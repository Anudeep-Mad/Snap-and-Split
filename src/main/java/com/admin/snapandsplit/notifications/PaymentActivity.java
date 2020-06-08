package com.admin.snapandsplit.notifications;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.admin.snapandsplit.R;
import com.admin.snapandsplit.login.login.email.presenter.EmailLoginPresenterImpl;


/**
 * Created by Admin on 4/15/2016.
 */
public class PaymentActivity extends Activity {

    public static String us;
    public static String dispname;
    public static String mobnum;
    public static String email;
    public static String acc_num;
    TextView dname;
    TextView uid;
    TextView phone;
    TextView em;
    TextView acc;
    Button pay;
    ProgressDialog progressDialog;
    private static final String TAG = "PaymentActivity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_main);
        dname = (TextView) findViewById (R.id.name);
        uid = (TextView) findViewById (R.id.uid);
        phone = (TextView) findViewById (R.id.phone);
        em = (TextView) findViewById (R.id.email);
        acc = (TextView) findViewById (R.id.acc_num);
       pay = (Button) findViewById (R.id.payButton);

               us = EmailLoginPresenterImpl.us;
        dispname = EmailLoginPresenterImpl.dn;
        mobnum = EmailLoginPresenterImpl.mb;
        acc_num = EmailLoginPresenterImpl.acc_num;
        email = EmailLoginPresenterImpl.em;

        dname.setText("Display Name : "+dispname);
        phone.setText("Mobile Number : "+mobnum);
        em.setText("Email ID : "+email);
        acc.setText("Email ID : "+acc_num);
        uid.setText("uid : "+us);

        progressDialog = new ProgressDialog (this);
        progressDialog.setMessage("Transferring Amount");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showProgressBar();
             /*   try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                hideProgressBar();
                openSuccessPage();
            }
        });

    }


    public void hideProgressBar () {
        progressDialog.hide ();
    }


    public void showProgressBar () {
        progressDialog.show();
    }

    public void openSuccessPage() {
        Log.i(TAG, "Opening Success Page");
        startActivity(new Intent(PaymentActivity.this, SuccessActivity.class));
        finish();

    }

}