package com.admin.snapandsplit.start.interactor;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.admin.snapandsplit.start.presenter.OnTokenLoginFinishedListener;
import com.admin.snapandsplit.utils.Constants;
import com.admin.snapandsplit.utils.UpdateFirebaseLogin;
import com.admin.snapandsplit.utils.Utilities;

public class StartPageInteractorImpl implements StartPageInteractor, Firebase.AuthResultHandler{

    Firebase firebase = new Firebase(Constants.FIREBASE_REF);

    OnTokenLoginFinishedListener listener;

    String provider;

    String TAG = Utilities.getTag (this);

    @Override
    public void loginWithToken(String provider, String accessToken, OnTokenLoginFinishedListener listener){
        this.listener = listener;
        this.provider = provider;
        if(provider.equals (Constants.PROVIDER_FACEBOOK))
            firebase.authWithOAuthToken (provider, accessToken, this);
        else
            firebase.authWithCustomToken (accessToken, this);
    }

    @Override
    public void onAuthenticated (AuthData authData) {
        UpdateFirebaseLogin.updateFirebase (authData);
        listener.onLoginSuccessful (provider, authData.getUid (), authData.getToken ());
    }

    @Override
    public void onAuthenticationError (FirebaseError firebaseError) {
        Log.e(TAG, firebaseError.getMessage ());
        listener.onLoginUnsuccessful ();
    }
}
