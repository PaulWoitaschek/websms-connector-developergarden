package de.ph1b.dgard;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.telekom.api.common.ServiceEnvironment;
import com.telekom.api.common.auth.TelekomOAuth2Auth;
import com.telekom.api.common.model.SmsResponse;
import com.telekom.api.quota.QuotaClient;
import com.telekom.api.quota.model.GetQuotaInformationResponse;
import com.telekom.api.sendsms.OutboundSMSType;
import com.telekom.api.sendsms.SendSmsClient;
import com.telekom.api.sendsms.model.SendSmsRequest;

import java.io.IOException;

import de.ub0r.android.websms.connector.common.Connector;
import de.ub0r.android.websms.connector.common.ConnectorCommand;
import de.ub0r.android.websms.connector.common.ConnectorSpec;
import de.ub0r.android.websms.connector.common.ConnectorSpec.SubConnectorSpec;
import de.ub0r.android.websms.connector.common.Log;
import de.ub0r.android.websms.connector.common.Utils;
import de.ub0r.android.websms.connector.common.WebSMSException;


public class DevCon extends Connector {

    private static final String TAG = "Developergarden";
    static String subAccountId = null;
    static String scope = "DC0QX4UK";

    private static void outputErrorAndAbort(SmsResponse response) {
        if (response.getRequestError().getServiceException() != null) {
            throw new RuntimeException(String.format("error %s: %s - %s",
                    response.getRequestError().getServiceException().getMessageId(),
                    response.getRequestError().getServiceException().getText().substring(0, response.getRequestError().getServiceException().getText().length() - 2),
                    response.getRequestError().getServiceException().getVariables()[0]));
        } else if (response.getRequestError().getPolicyException() != null) {
            throw new RuntimeException(String.format("error %s: %s - %s",
                    response.getRequestError().getPolicyException().getMessageId(),
                    response.getRequestError().getPolicyException().getText().substring(0, response.getRequestError().getPolicyException().getText().length() - 2),
                    response.getRequestError().getPolicyException().getVariables()[0]));
        }
    }

    @Override
    public final ConnectorSpec initSpec(final Context context) {
        final String name = context.getString(R.string.connector_dgarden_name);
        ConnectorSpec c = new ConnectorSpec(name);
        c.setAuthor(context.getString(R.string.connector_dgarden_author));
        c.setBalance(null);
        c.setLimitLength(129);
        c.setCapabilities(ConnectorSpec.CAPABILITIES_UPDATE
                | ConnectorSpec.CAPABILITIES_SEND
                | ConnectorSpec.CAPABILITIES_PREFS);
        c.addSubConnector(TAG, c.getName(),
                SubConnectorSpec.FEATURE_CUSTOMSENDER
                        | SubConnectorSpec.FEATURE_SENDLATER
                        | SubConnectorSpec.FEATURE_SENDLATER_QUARTERS
                        | SubConnectorSpec.FEATURE_FLASHSMS);
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

        return connectorSpec;
    }

    //initiates login and returns authentification
    private TelekomOAuth2Auth login(final Context context)
            throws IOException {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        String token = p.getString(Preferences.PREFS_PASSWORD, "");
        String clientId = p.getString(Preferences.PREFS_CLIENTID, "");

        TelekomOAuth2Auth auth = new TelekomOAuth2Auth(clientId, token, scope);
        auth.requestAccessToken();
        if (!auth.hasValidToken())
            throw new WebSMSException("No valid token!");
        return auth;
    }

    // sending actual message using the the auth provied by login()
    private void sendMessage(final Context context, final Intent intent, TelekomOAuth2Auth auth) {
        ConnectorCommand localConnectorCommand = new ConnectorCommand(intent);
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        String senderNumber = p.getString(Preferences.PREFS_CUSTOMNUMBER, "");
        SendSmsClient client = new SendSmsClient(auth, ServiceEnvironment.SANDBOX);
        SendSmsRequest request = new SendSmsRequest();
        String text = localConnectorCommand.getText();

        //reformats
        String[] reci = localConnectorCommand.getRecipients();
        String[] reciFormat = new String[reci.length];
        for (int i = 0; i < reci.length; i++) {
            reciFormat[i] = "tel:" + Utils.national2international(localConnectorCommand.getDefPrefix(), Utils.getRecipientsNumber(reci[i]));
            Log.i(TAG, "Sending to: " + reciFormat[i]);
        }

        request.setAddress(reciFormat);
        request.setMessage(text);
        request.setType(OutboundSMSType.TEXT);
        request.setSenderAddress("tel:" + senderNumber);
        request.setAccount(subAccountId);

        try {
            Log.i(TAG, "Sending SMS...");
            SmsResponse response = client.sendSms(request);
            if (!response.getSuccess())
                outputErrorAndAbort(response);
        } catch (IOException e) {
            Log.e(TAG, "error during service call: " + e.getMessage());
        }

    }

    //gets free sms available and sets them to connector spec
    @Override
    protected final void doUpdate(final Context context, final Intent intent)
            throws IOException {
        TelekomOAuth2Auth auth = login(context);
        ConnectorSpec c = getSpec(context);
        QuotaClient client = new QuotaClient(auth, ServiceEnvironment.SANDBOX);
        GetQuotaInformationResponse quotaResponse = client.getQuotaInformation("GlobalSmsSandbox");
        Log.i(TAG, "Free SMS: " + String.valueOf(quotaResponse.getMaxQuota() - quotaResponse.getQuotaLevel()));
        c.setBalance(String.valueOf(quotaResponse.getMaxQuota() - quotaResponse.getQuotaLevel()));
    }

    //initiates login, then checks for free then initiates sending
    @Override
    protected final void doSend(final Context context, final Intent intent)
            throws IOException {
        TelekomOAuth2Auth auth = this.login(context);
        doUpdate(context, intent);

        /* temporary disabled because of buggy update
        if (getSpec(context).getBalance().equals("0")) {
            throw new WebSMSException(context, R.string.no_free_sms);
        }*/

        this.sendMessage(context, intent, auth);
    }


}