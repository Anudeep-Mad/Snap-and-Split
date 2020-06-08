package com.admin.snapandsplit.login.register.view;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.admin.snapandsplit.HomeActivity;
import com.admin.snapandsplit.R;
import com.admin.snapandsplit.login.register.presenter.RegisterPresenter;
import com.admin.snapandsplit.login.register.presenter.RegisterPresenterImpl;
import com.admin.snapandsplit.main.MainActivity;
import com.admin.snapandsplit.utils.Constants;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity implements RegisterActivityView {

    Fragment details;

    RegisterPresenter presenter;

    EditText email;
    EditText password;
    EditText username;
    EditText mobnum;
    EditText acc_num;
    ProgressDialog progressDialog;

    Button register;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        presenter = new RegisterPresenterImpl (this);

        details = getFragmentManager ().findFragmentById (R.id.registerDetailsFragment);

        email = (EditText) details.getView ().findViewById (R.id.emailText);
        password = (EditText) details.getView ().findViewById (R.id.passwordText);
        username = (EditText) details.getView ().findViewById (R.id.user_name);
        mobnum = (EditText) details.getView ().findViewById (R.id.mob_num);
        acc_num = (EditText) details.getView ().findViewById (R.id.acc_num);

        progressDialog = new ProgressDialog (this);
        progressDialog.setMessage("Registering User");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);

        register = (Button) findViewById (R.id.registerButton);
        register.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                presenter.createUser (email.getText ().toString (), password.getText ().toString (),username.getText().toString(),mobnum.getText().toString(),acc_num.getText().toString());
            }
        });



        //////////

        Firebase ref = new Firebase("https://snapnsplit.firebaseio.com");
        ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    // user is logged in
                } else {
                    // user is not logged in
                }
            }
        });


        AuthData authData = ref.getAuth();
        if (authData != null) {
            // user authenticated
        } else {
            // no user authenticated
        }



        //  Firebase ref = new Firebase("https://snapnsplit.firebaseio.com");

// Create a handler to handle the result of the authentication
        Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                // Authenticated successfully with payload authData
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // Authenticated failed with error firebaseError
            }
        };

// Authenticate users with a custom Firebase token
        ref.authWithCustomToken("<token>", authResultHandler);

// Alternatively, authenticate users anonymously
        ref.authAnonymously(authResultHandler);

// Or with an email/password combination
        ref.authWithPassword("abcd@abcd.com", "abcdefgh", authResultHandler);

// Or via popular OAuth providers ("facebook", "github", "google", or "twitter")
        ref.authWithOAuthToken("<provider>", "<oauth-token>", authResultHandler);




        //Firebase ref = new Firebase("https://snapnsplit.firebaseio.com");
        ref.authWithPassword("abcd@abcd.com", "abcdefgh",
                new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) { /* ... */ }

                    @Override
                    public void onAuthenticationError(FirebaseError error) {
                        // Something went wrong :(
                        switch (error.getCode()) {
                            case FirebaseError.USER_DOES_NOT_EXIST:
                                // handle a non existing user
                                break;
                            case FirebaseError.INVALID_PASSWORD:
                                // handle an invalid password
                                break;
                            default:
                                // handle other errors
                                break;
                        }
                    }
                });







        final Firebase mref = new Firebase("https://snapnsplit.firebaseio.com");

        mref.authWithPassword("abcd@abcd.com", "abcdefgh",
                new Firebase.AuthResultHandler() {

                    @Override
                    public void onAuthenticated(AuthData authData) {
                        // Authentication just completed successfully :)
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("provider", authData.getProvider());
                        if (authData.getProviderData().containsKey("displayName")) {
                            map.put("displayName", authData.getProviderData().get("displayName").toString());
                        }

                        mref.child("users").child(authData.getUid()).setValue(map);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError error) {
                        // Something went wrong :(
                    }
                });





    }

    @Override
    public void hideProgressBar () {
        progressDialog.hide ();
    }

    @Override
    public void showProgressBar () {
        progressDialog.show ();
    }

    @Override
    public void openHomePage () {
        Toast.makeText (RegisterActivity.this, "Welcome to YourVoiceHeard", Toast.LENGTH_SHORT).show ();
        startActivity (new Intent (RegisterActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void registrationError (String message) {
        Toast.makeText (RegisterActivity.this, message, Toast.LENGTH_SHORT).show ();
    }

    @Override
    public void writeToSharedPreferences (String uid, String token) {
        SharedPreferences sharedPreferences = getSharedPreferences (Constants.MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit ();
        editor.putString ("provider", "password");
        Log.i ("EMAIL VIEW", uid + " " + token);
        editor.putString ("uid", uid);
        editor.putString ("accessToken", token);
        editor.commit ();
        Log.i("Login UID", "The uid is - " + sharedPreferences.getString ("uid", ""));
    }


}
