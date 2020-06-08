package com.admin.snapandsplit.notifications;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.snapandsplit.R;
import com.admin.snapandsplit.notifications.NotiActivity;
import com.admin.snapandsplit.contact.ContactsAdapter;
import com.admin.snapandsplit.contact.ContactsListClass;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Admin on 4/11/2016.
 */
 public class NotificationActivity extends Activity {
    ListView listView;
    ArrayList<String> arr = new ArrayList<>(100);
    private static final String TAG = "NotificationsPick.java";
    Context context = null;
    Button btnOK = null;
    private List<String> list;
    NotificationsAdapter objAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.contacts_main);
        Firebase.setAndroidContext(this);

        btnOK = (Button) findViewById(R.id.ok_button);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                getSelectedContacts();
            }
        });

        final Firebase myFirebaseRef = new Firebase("https://snapnsplit.firebaseio.com");

        myFirebaseRef.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                System.out.println(dataSnapshot.getKey() + " was " + dataSnapshot.getValue());
                Log.d("obj", dataSnapshot.getKey() + " , " + dataSnapshot.getValue());
                String uid = dataSnapshot.getKey();

                User cp = new User();


                cp.setUid(dataSnapshot.getKey());

                NotificationsListClass.notiList.add(cp);

                arr.add(dataSnapshot.getKey());


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        listView = (ListView) findViewById(R.id.listView);
       // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,arr);
       // arrayAdapter.notifyDataSetChanged();
       // listView.setAdapter(arrayAdapter);


        Collections.sort(NotificationsListClass.notiList,
                new Comparator<User>() {
                    @Override
                    public int compare(User lhs,
                                       User rhs) {
                        return lhs.getUid().compareTo(
                                rhs.getUid());
                    }
                });


        objAdapter = new NotificationsAdapter(NotificationActivity.this,
                NotificationsListClass.notiList);
        listView.setAdapter(objAdapter);
        objAdapter.notifyDataSetChanged();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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




    }

    private void getSelectedContacts() {
        // TODO Auto-generated method stub

        StringBuffer sb = new StringBuffer();

        for (User bean : NotificationsListClass.notiList) {

            if (bean.isSelected()) {
                sb.append(bean.getUid());
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
        openNotiPage();

    }

    public String NotificationPicker() {
        list = new ArrayList<String>();
        StringBuilder ph = new StringBuilder();

        for (User bean : NotificationsListClass.notiList) {

            if (bean.isSelected()) {
                ph.append(bean.getUid());
               // ph.append(",");
            }
        }
        String s = ph.toString().trim();
        //  s = s.substring(0, s.length() - 1);

        return s;
    }


    public void openNotiPage () {
        Log.i(TAG, "Opening Noti Page");
        startActivity (new Intent(NotificationActivity.this, NotiActivity.class));
        finish();

    }
}
