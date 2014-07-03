package de.ph1b.dgard;


import android.os.Bundle;
import android.preference.PreferenceActivity;


public final class Preferences extends PreferenceActivity {

    static final String PREFS_ENABLED = "enable_dgarden";

    static final String PREFS_PASSWORD = "token";

    static final String PREFS_CLIENTID = "client_id";

    static final String PREFS_CUSTOM_ENABLED = "use_custom_sender";

    static final String PREFS_PREMIUM_ENABLED = "premium";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.prefs);
    }

}