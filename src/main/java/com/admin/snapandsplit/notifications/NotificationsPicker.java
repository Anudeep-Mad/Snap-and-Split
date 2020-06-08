package com.admin.snapandsplit.notifications;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.admin.snapandsplit.notifications.NotiActivity;
import com.admin.snapandsplit.R;
import com.admin.snapandsplit.SMS;
import com.admin.snapandsplit.utils.Constants;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

abstract public class NotificationsPicker extends Activity implements ChildEventListener{
    private static final String TAG = "NotificationsPick.java";
    private final static Firebase firebase = new Firebase("https://snapnsplit.firebaseio.com");

    Context context = null;

    NotificationsAdapter objAdapter;

    ListView lv = null;

    EditText edtSearch = null;
    LinearLayout llContainer = null;
    Button btnOK = null;

    RelativeLayout rlPBContainer = null;

    private List<String> list;
    public String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(R.layout.contacts_main);

        rlPBContainer = (RelativeLayout) findViewById(R.id.pbcontainer);

        edtSearch = (EditText) findViewById(R.id.input_search);
        llContainer = (LinearLayout) findViewById(R.id.data_container);
        btnOK = (Button) findViewById(R.id.ok_button);

        btnOK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                getSelectedContacts();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                // When user changed the Text
                String text = edtSearch.getText().toString()
                        .toLowerCase(Locale.getDefault());
               // objAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        addContactsInList();

    }

    private void getSelectedContacts() {
        // TODO Auto-generated method stub

        StringBuffer sb = new StringBuffer();

        for (User bean : NotificationsListClass.notiList) {

            if (bean.isSelected()) {
                sb.append(bean.getDisplayName());
                sb.append(",");
            }
        }

        String s = sb.toString().trim();

        if (TextUtils.isEmpty(s)) {
            Toast.makeText(context, "Select atleast one Contact",
                    Toast.LENGTH_SHORT).show();
        } else {

            s = s.substring(0, s.length() - 1);
            Toast.makeText(context, "Selected Contacts : " + s,
                    Toast.LENGTH_SHORT).show();

        }
        objAdapter.notifyDataSetChanged();
        openSMSPage();

    }

    public String ContactsPicker() {
        list = new ArrayList<String>();
        StringBuilder ph = new StringBuilder();

        for (User bean : NotificationsListClass.notiList) {

            if (bean.isSelected()) {
                ph.append(bean.getAccessToken());
                ph.append(",");
            }
        }
        String s = ph.toString().trim();
      //  s = s.substring(0, s.length() - 1);

    return s;
    }


    private void addContactsInList() {
        // TODO Auto-generated method stub

        Thread thread = new Thread() {
            @Override
            public void run() {

                showPB();

                try {

                    Cursor phones = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, null, null, null);

                    try {
                        NotificationsListClass.notiList.clear();
                    } catch (Exception e) {

                    }

                    while (phones.moveToNext()) {
                       /* String phoneName = phones
                                .getString(phones
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));*/
                       /* String phoneNumber = phones
                                .getString(phones
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));*/
                        String phoneImage = phones
                                .getString(phones
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));



                        firebase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();

                              //  List<Object> values = td.values();

                                //notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });


                        User cp = new User();

                        String phoneName = cp.getDisplayName();
                        String phoneNumber = cp.getAccessToken();

                        cp.setDisplayName(phoneName);
                        cp.setAccessToken(phoneNumber);
                        //cp.setImage(phoneImage);

                        NotificationsListClass.notiList.add(cp);

                    }
                    phones.close();

                    lv = new ListView(context);

                    lv.setLayoutParams(new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            llContainer.addView(lv);
                        }
                    });

                    Collections.sort(NotificationsListClass.notiList,
                            new Comparator<User>() {
                                @Override
                                public int compare(User lhs,
                                                   User rhs) {
                                    return lhs.getDisplayName().compareTo(
                                            rhs.getDisplayName());
                                }
                            });

                    objAdapter = new NotificationsAdapter(NotificationsPicker.this,
                            NotificationsListClass.notiList);
                    lv.setAdapter(objAdapter);
                   objAdapter.notifyDataSetChanged();
                    lv.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {

                            CheckBox chk = (CheckBox) view
                                    .findViewById(R.id.contactcheck);
                            User bean = NotificationsListClass.notiList
                                    .get(position);
                            if (bean.isSelected()) {
                                bean.setSelected(false);
                                chk.setChecked(false);
                            } else {
                                bean.setSelected(true);
                                chk.setChecked(true);
                            }

                        }
                    });

                } catch (Exception e) {

                    e.printStackTrace();

                }

                hidePB();

            }
        };
        thread.start();

    }

    void showPB() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                rlPBContainer.setVisibility(View.VISIBLE);
                edtSearch.setVisibility(View.GONE);
                btnOK.setVisibility(View.GONE);
            }
        });
    }

    void hidePB() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                rlPBContainer.setVisibility(View.GONE);
                edtSearch.setVisibility(View.VISIBLE);
                btnOK.setVisibility(View.VISIBLE);
            }
        });
    }

    public void openSMSPage () {
        Log.i(TAG, "Opening SMS Page");
        startActivity (new Intent(NotificationsPicker.this, NotiActivity.class));
        finish();

    }


}
