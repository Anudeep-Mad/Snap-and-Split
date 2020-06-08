package com.admin.snapandsplit.notifications;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.admin.snapandsplit.R;

/**
 * Created by Admin on 4/20/2016.
 */
public class SuccessActivity extends Activity {

    TextView success;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_main);

       success = (TextView) findViewById (R.id.success);

    }
}
