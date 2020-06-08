package com.admin.snapandsplit.start.presenter;

import android.util.Log;

import com.facebook.AccessToken;
import com.admin.snapandsplit.start.interactor.StartPageInteractor;
import com.admin.snapandsplit.start.interactor.StartPageInteractorImpl;
import com.admin.snapandsplit.start.view.StartPageView;
import com.admin.snapandsplit.utils.Constants;
import com.admin.snapandsplit.utils.Utilities;

public class StartPagePreviousLoginChecker implements
        StartPagePresenter, OnTokenLoginFinishedListener {

    String TAG = Utilities.getTag (this);

    StartPageView view;
    StartPageInteractor interactor;

    public StartPagePreviousLoginChecker (StartPageView view){
        this.view = view;
        interactor = new StartPageInteractorImpl ();
        Log.i (TAG, "PreviousLoginChecker created");
    }

    @Override
    public void loginWithPassword (String accessToken) {
        Log.i(TAG, "Logging in with Password");
        interactor.loginWithToken (Constants.PROVIDER_PASSWORD, accessToken, this);
    }

    @Override
    public void onInitialized () {

        if(AccessToken.getCurrentAccessToken () != null){
            Log.i(TAG, "Logging in with Facebook");
            interactor.loginWithToken (Constants.PROVIDER_FACEBOOK, AccessToken.getCurrentAccessToken ().getToken (), this);
        }

    }

    @Override
    public void onLoginSuccessful (String provider, String uid, String accessToken) {
        view.disableLoginPage ();
        view.writeToSharedPreferences (provider, uid, accessToken);
        view.openMainPage ();
    }

    @Override
    public void onLoginUnsuccessful () {
        view.showMessage ("Your access token has expired\nPlease re-com.example.benjaminlize.loginapp.GlobalLogin.login");
        view.openLoginPage ();
    }
}
