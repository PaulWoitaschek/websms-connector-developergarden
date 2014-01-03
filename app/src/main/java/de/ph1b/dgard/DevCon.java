package de.ph1b.dgard;


import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import de.ub0r.android.websms.connector.common.Connector;
import de.ub0r.android.websms.connector.common.ConnectorCommand;
import de.ub0r.android.websms.connector.common.ConnectorSpec;
import de.ub0r.android.websms.connector.common.ConnectorSpec.SubConnectorSpec;
import de.ub0r.android.websms.connector.common.Log;

import com.telekom.api.common.ServiceEnvironment;
import com.telekom.api.common.auth.TelekomOAuth2Auth;
import com.telekom.api.common.model.SmsResponse;
import com.telekom.api.sendsms.OutboundSMSType;
import com.telekom.api.sendsms.SendSmsClient;
import com.telekom.api.sendsms.model.SendSmsRequest;


public class DevCon extends Connector {

    private static final String TAG = "Developergarden";
    static String subAccountId = null;
    static String scope = "DC0QX4UK";
    TelekomOAuth2Auth auth;

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

    private boolean login(final Context context)
            throws IOException {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        String token = p.getString(Preferences.PREFS_PASSWORD, "");
        String clientId = p.getString(Preferences.PREFS_CLIENTID, "");

        auth = new TelekomOAuth2Auth(clientId, token, scope);
        auth.requestAccessToken();
        if (!auth.hasValidToken())
            Log.e(TAG, "Authentication error");

        return true;
    }

    private void sendMessage(final Context context, final Intent intent) {
        ConnectorCommand localConnectorCommand = new ConnectorCommand(intent);
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        String senderNumber = p.getString(Preferences.PREFS_CUSTOMNUMBER, "");
        SendSmsClient client = new SendSmsClient(auth, ServiceEnvironment.SANDBOX);
        SendSmsRequest request = new SendSmsRequest();
        String text = localConnectorCommand.getText();

        String[] reci = localConnectorCommand.getRecipients();
        String[] reciFormat = new String[reci.length];
        for (int i = 0; i < reci.length; i++) {
            reciFormat[i] = "tel:" + reci[i].substring(reci[i].indexOf("<") + 1, reci[i].indexOf(">"));
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

    @Override
    protected final void doSend(final Context context, final Intent intent)
            throws IOException {
        this.login(context);
        this.sendMessage(context, intent);
    }


}