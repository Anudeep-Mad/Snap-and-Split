package com.admin.snapandsplit.contact;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.admin.snapandsplit.R;
import com.admin.snapandsplit.SMS;
import com.admin.snapandsplit.utils.Constants;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("ALL")
public class ContactPickerMulti extends ListActivity  {

    private static final String TAG = "SimpleAndroidOCR.java";

    // List variables
    public String[] Contacts = {};
    public int[] to = {};
    public ListView myListView;
    Button save_button;
    private TextView textView1;
   public String[] phoneNumber;
    String phone;
    private Cursor cursor;
    String [] temp = {};
    private List<String> list;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        // Initializing the buttons according to their ID
        save_button = (Button) findViewById(R.id.button1);

        // Defines listeners for the buttons


        Cursor mCursor = getContacts();
        startManagingCursor(mCursor);

        ListAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                mCursor,
                Contacts = new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                to = new int[]{android.R.id.text1});

        setListAdapter(adapter);
        myListView = getListView();
        myListView.setItemsCanFocus(false);
        myListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        save_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                long[] id = getListView().getCheckedItemIds();//  i get the checked contact_id instead of position
                phoneNumber = new String[id.length];
                for (int i = 0; i < id.length; i++) {

                    phoneNumber[i] = getPhoneNumber(id[i]); // get phonenumber from selected id

                }

                Intent pickContactIntent = new Intent();
                pickContactIntent.putExtra("PICK_CONTACT", phoneNumber);// Add checked phonenumber in intent and finish current activity.
                startActivityForResult(pickContactIntent, Constants.REQUEST_CODE_PICK_CONTACT);
                openSMSPage();

            }


        });


    }

    private Cursor getContacts() {
        // Run query
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[]{ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME};
        String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER + " = '"
                + ("1") + "'";
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
                + " COLLATE LOCALIZED ASC";

        return managedQuery(uri, projection, selection, selectionArgs,
                sortOrder);
    }



    private String getPhoneNumber(long id) {
        String phone = null;
        Cursor phonesCursor = null;
        phonesCursor = queryPhoneNumbers(id);
        if (phonesCursor == null || phonesCursor.getCount() == 0) {
            // No valid number
            //signalError();
            return null;
        } else if (phonesCursor.getCount() == 1) {
            // only one number, call it.
            phone = phonesCursor.getString(phonesCursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        } else {
            phonesCursor.moveToPosition(-1);
            while (phonesCursor.moveToNext()) {

                // Found super primary, call it.
                phone = phonesCursor.getString(phonesCursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                break;

            }
        }

        return phone;
    }


    private Cursor queryPhoneNumbers(long contactId) {
        ContentResolver cr = getContentResolver();
        Uri baseUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,
                contactId);
        Uri dataUri = Uri.withAppendedPath(baseUri,
                ContactsContract.Contacts.Data.CONTENT_DIRECTORY);

        Cursor c = cr.query(dataUri, new String[]{ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.IS_SUPER_PRIMARY, ContactsContract.RawContacts.ACCOUNT_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.LABEL}, ContactsContract.Data.MIMETYPE + "=?",
                new String[]{ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE}, null);
        if (c != null && c.moveToFirst()) {
            return c;
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case (Constants.REQUEST_CODE_PICK_CONTACT):

                if (resultCode == RESULT_OK) {

                    if (data != null) {

                        String[] temp = data.getStringArrayExtra("PICK_CONTACT");
                        textView1.setText(temp.toString());
                    }

                }
                break;
        }

    }

    public ContactPickerMulti() {
        list = new ArrayList<String>();
        list.add(temp.toString());

    }

    public List<String> getList() {
        return list;
    }



    public void openSMSPage () {
        Log.i(TAG, "Opening SMS Page");
        startActivity (new Intent (ContactPickerMulti.this, SMS.class));
        finish ();

    }

}