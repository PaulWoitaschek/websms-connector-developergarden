package de.ph1b.dgard;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.telekom.api.common.ServiceEnvironment;
import com.telekom.api.common.auth.TelekomOAuth2Auth;
import com.telekom.api.common.model.SmsResponse;
import com.telekom.api.quota.QuotaClient;
import com.telekom.api.quota.model.GetAccountBalanceRequest;
import com.telekom.api.quota.model.GetAccountBalanceResponse;
import com.telekom.api.quota.model.GetQuotaInformationResponse;
import com.telekom.api.quota.model.SubAccountBalance;
import com.telekom.api.sendsms.OutboundSMSType;
import com.telekom.api.sendsms.SendSmsClient;
import com.telekom.api.sendsms.model.SendSmsRequest;

import java.io.IOException;
import java.text.NumberFormat;

import de.ub0r.android.websms.connector.common.BasicSMSLengthCalculator;
import de.ub0r.android.websms.connector.common.Connector;
import de.ub0r.android.websms.connector.common.ConnectorCommand;
import de.ub0r.android.websms.connector.common.ConnectorSpec;
import de.ub0r.android.websms.connector.common.ConnectorSpec.SubConnectorSpec;
import de.ub0r.android.websms.connector.common.Utils;
import de.ub0r.android.websms.connector.common.WebSMSException;


public class DevCon extends Connector {

    String TAG = "DeveloperGarden";

    private static void outputErrorAndAbort(SmsResponse response) {
        if (response.getRequestError().getServiceException() != null) {
            throw new WebSMSException(String.format("error %s: %s - %s",
                    response.getRequestError().getServiceException().getMessageId(),
                    response.getRequestError().getServiceException().getText().substring(0, response.getRequestError().getServiceException().getText().length() - 2),
                    response.getRequestError().getServiceException().getVariables()[0]));
        } else if (response.getRequestError().getPolicyException() != null) {
            throw new WebSMSException(String.format("error %s: %s - %s",
                    response.getRequestError().getPolicyException().getMessageId(),
                    response.getRequestError().getPolicyException().getText().substring(0, response.getRequestError().getPolicyException().getText().length() - 2),
                    response.getRequestError().getPolicyException().getVariables()[0]));
        }
    }

    @Override
    public final ConnectorSpec initSpec(final Context context) {
        ConnectorSpec c = new ConnectorSpec(context.getString(R.string.connector_dgarden_name));
        c.setAuthor(context.getString(R.string.connector_dgarden_author));
        c.setBalance(null);
        c.setAdUnitId("ca-app-pub-3446180126620439/5879359143");
        c.setCapabilities(ConnectorSpec.CAPABILITIES_UPDATE
                | ConnectorSpec.CAPABILITIES_SEND
                | ConnectorSpec.CAPABILITIES_PREFS);
        c.addSubConnector("0", "", SubConnectorSpec.FEATURE_NONE);
        return c;
    }


    @Override
    public final ConnectorSpec updateSpec(final Context context,
                                          final ConnectorSpec connectorSpec) {
        final SharedPreferences p = PreferenceManager
                .getDefaultSharedPreferences(context);
        if (p.getBoolean(Preferences.PREFS_ENABLED, false)) {
            if (p.getString(Preferences.PREFS_PASSWORD, "").length() > 0) {
                connectorSpec.setReady();
            } else {
                connectorSpec.setStatus(ConnectorSpec.STATUS_ENABLED);
            }
        } else {
            connectorSpec.setStatus(ConnectorSpec.STATUS_INACTIVE);
        }
        if (isPremium(context)) {
            connectorSpec.setLimitLength(765);
            connectorSpec.setSMSLengthCalculator(new BasicSMSLengthCalculator(new int[]{765}));
        } else {
            connectorSpec.setLimitLength(129);
            connectorSpec.setSMSLengthCalculator(new BasicSMSLengthCalculator(new int[]{129}));
        }
        initSpec(context);
        return connectorSpec;
    }


    private String getText(final Intent intent) {
        ConnectorCommand c = new ConnectorCommand(intent);
        return c.getText();
    }


    private boolean isPremium(final Context context) {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        return p.getBoolean(Preferences.PREFS_PREMIUM_ENABLED, false);
    }

    //initiates login and returns authentification
    private TelekomOAuth2Auth login(final Context context)
            throws IOException {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        String token = p.getString(Preferences.PREFS_PASSWORD, "");
        String clientId = p.getString(Preferences.PREFS_CLIENTID, "");

        TelekomOAuth2Auth auth = new TelekomOAuth2Auth(clientId, token, "DC0QX4UK");
        auth.requestAccessToken();
        if (!auth.hasValidToken())
            throw new WebSMSException("No valid token!");
        return auth;
    }


    private String[] getNumbers(final Intent intent) {
        ConnectorCommand c = new ConnectorCommand(intent);
        String[] recipients = new String[c.getRecipients().length];
        for (int i = 0; i < recipients.length; i++) {
            recipients[i] = "tel:" + Utils.national2international(c.getDefPrefix(), Utils.getRecipientsNumber(c.getRecipients()[i]));
        }
        return recipients;
    }


    private String getSenderNumber(final Context context) {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        return (p.getString("custom_sender", ""));
    }

    // sending actual message using the the auth provied by login()
    private void sendMessage(final Context context, final Intent intent, TelekomOAuth2Auth auth) {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        SendSmsClient client;
        if (isPremium(context)) {
            client = new SendSmsClient(auth, ServiceEnvironment.PREMIUM);
        } else {
            client = new SendSmsClient(auth, ServiceEnvironment.BUDGET);
        }
        SendSmsRequest request = new SendSmsRequest();
        request.setAddress(getNumbers(intent));
        request.setMessage(getText(intent));
        request.setType(OutboundSMSType.TEXT);

        if (p.getBoolean(Preferences.PREFS_CUSTOM_ENABLED, false)) {
            if (getSenderNumber(context).length() == 0) {
                throw new WebSMSException(context.getString(R.string.error_custom_sender));
            }
            request.setSenderAddress("tel:" + getSenderNumber(context));
        } else {
            request.setSenderAddress("0191011");
        }
        try {
            SmsResponse response = client.sendSms(request);
            if (!response.getSuccess())
                outputErrorAndAbort(response);
        } catch (IOException e) {
            Log.e(TAG, "Error while sending: " + e.getMessage());
        }
    }

    //gets free sms available and sets them to connector spec
    @Override
    protected final void doUpdate(final Context context, final Intent intent)
            throws IOException {
        TelekomOAuth2Auth auth = login(context);
        ConnectorSpec c = getSpec(context);
        int balance = getIntBalance(auth, context);
        if (isPremium(context)) {
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            c.setBalance(nf.format(balance / 1000.0));
        } else {
            c.setBalance((String.valueOf(balance)) + (context.getString(R.string.sms_free_left)));
        }
    }


    protected int getIntBalance(final TelekomOAuth2Auth auth, final Context context) throws IOException {
        int balance = 0;
        GetQuotaInformationResponse quotaResponse;
        QuotaClient client = new QuotaClient(auth,
                ServiceEnvironment.PRODUCTION);
        if (isPremium(context)) {
            GetAccountBalanceRequest balanceRequest = new GetAccountBalanceRequest();
            balanceRequest.setAccountId(null);
            GetAccountBalanceResponse balanceResponse = client
                    .getAccountBalance(balanceRequest);
            for (SubAccountBalance subAccountBalance : balanceResponse
                    .getAccounts())
                balance = subAccountBalance.getCredits();
        } else {
            quotaResponse = client.getQuotaInformation("GlobalSmsSandbox");
            balance = (quotaResponse.getMaxQuota() - quotaResponse.getQuotaLevel());
        }
        return balance;
    }

    //initiates login, then checks for free then initiates sending
    @Override
    protected final void doSend(final Context context, final Intent intent)
            throws IOException {
        TelekomOAuth2Auth auth = this.login(context);
        if (!isPremium(context)) {
            if (getIntBalance(auth, context) == 0) {
                throw new WebSMSException(context.getString(R.string.sms_free_no));
            }
        } else {
            if (getIntBalance(auth, context) < 1010) {
                throw new WebSMSException(context.getString(R.string.insuff));
            }
        }
        this.sendMessage(context, intent, auth);
        doUpdate(context, intent);
    }
}
