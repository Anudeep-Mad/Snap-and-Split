package com.admin.snapandsplit.application;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;
import com.admin.snapandsplit.utils.Constants;
import com.admin.snapandsplit.utils.GoogleApiHelper;
import com.admin.snapandsplit.utils.Utilities;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class SnapAndSplitApplication extends Application {

    private GoogleApiHelper mGoogleApiHelper;

    private static SnapAndSplitApplication mInstance;

    private final String TAG = Utilities.getTag (this);

    @Override
    public void onCreate () {
        super.onCreate ();

        mInstance = this;
        mGoogleApiHelper = new GoogleApiHelper(getApplicationContext ());

        FacebookSdk.sdkInitialize(getApplicationContext());

        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        Firebase myFirebaseRef = new Firebase("https://snapandsplit.firebaseio.com/");

    }

    private static synchronized SnapAndSplitApplication getInstance() {
        return mInstance;
    }

    private GoogleApiHelper getGoogleApiHelperInstance() {
        return this.mGoogleApiHelper;
    }

    public static GoogleApiHelper getGoogleApiHelper() {
        return getInstance().getGoogleApiHelperInstance();
    }

}
