package com.admin.snapandsplit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.admin.snapandsplit.contact.ContactsPicker;
import com.admin.snapandsplit.notifications.NotificationActivity;

/**
 * Created by Admin on 4/12/2016.
 */
public class SelectActivity extends Activity{

    Button btnSelectContacts;
    Button btnSelectNoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_main);

        btnSelectContacts = (Button) findViewById(R.id.btnSelectContacts);
        btnSelectNoti = (Button) findViewById(R.id.btnSelectNoti);

        btnSelectContacts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openContactsPage ();

            }

        });

        btnSelectNoti.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openNotificationsPage ();
            }
        });
    }

    public void openContactsPage () {
       // Log.i(TAG, "Opening Contacts Page");
        startActivity (new Intent(SelectActivity.this, ContactsPicker.class));
        finish ();

    }

    public void openNotificationsPage () {
       // Log.i(TAG, "Opening Notifications Page");
        startActivity(new Intent(SelectActivity.this, NotificationActivity.class));
        finish();

    }
}
