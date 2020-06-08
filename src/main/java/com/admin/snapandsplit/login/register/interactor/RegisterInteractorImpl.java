package com.admin.snapandsplit.login.register.interactor;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.admin.snapandsplit.login.register.presenter.OnRegisterFinishedListener;
import com.admin.snapandsplit.utils.Constants;
import com.admin.snapandsplit.utils.UpdateFirebaseLogin;

import java.util.HashMap;
import java.util.Map;


public class RegisterInteractorImpl implements RegisterInteractor,
        Firebase.ValueResultHandler<Map<String, Object>>,
        Firebase.AuthResultHandler{

    String email;
    String password;
    String username;
    String mobnum;
    String acc_num;
    OnRegisterFinishedListener listener;

    Firebase firebase = new Firebase(Constants.FIREBASE_REF);

    @Override
    public void registerUser (String email, String password,String username,String mobnum,String acc_num, OnRegisterFinishedListener listener) {
        firebase.createUser(email, password, this);
        this.email = email;
        this.password = password;
        this.username = username;
        this.mobnum = mobnum;
        this.acc_num = acc_num;
        this.listener = listener;
    }

    @Override
    public void onError (FirebaseError firebaseError) {
        switch (firebaseError.getCode ()) {
            case FirebaseError.EMAIL_TAKEN:
                //Toast.makeText (context, "Email Already exists", Toast.LENGTH_LONG).show ();
                listener.onFailure (FirebaseError.EMAIL_TAKEN);
                break;
        }
    }

    @Override
    public void onSuccess (Map<String, Object> stringObjectMap) {
        Log.i ("Authenticate Create", stringObjectMap.toString ());
        //Toast.makeText (context, "User created", Toast.LENGTH_SHORT).show ();
        Map<String, Object> map = new HashMap<String, Object> ();
        map.put ("displayName", username);
        map.put ("mobileNumber", mobnum);
        map.put ("email", email);
        map.put ("provider", password);
        map.put ("BankAccountNumber", acc_num);
        map.put ("transactions", "transactions");

        firebase.child ("users").child ((String) stringObjectMap.get ("uid")).setValue(map);
        firebase.authWithPassword (email, password, this);
    }

    @Override
    public void onAuthenticated (AuthData authData) {
        listener.onSuccess (authData.getUid (), authData.getToken ());
        UpdateFirebaseLogin.updateFirebase (authData);

    }

    @Override
    public void onAuthenticationError (FirebaseError firebaseError) {
        listener.onFailure(firebaseError.getCode ());
    }
}
