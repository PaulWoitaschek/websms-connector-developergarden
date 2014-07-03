package de.ph1b.dgard;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class HelpScreen extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_layout);
        TextView helpscreen = (TextView) findViewById(R.id.helpscreen);
        helpscreen.setMovementMethod(LinkMovementMethod.getInstance());
        helpscreen.setText(Html.fromHtml(getString(R.string.help_text)));
    }

}
