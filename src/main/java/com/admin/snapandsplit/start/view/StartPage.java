package com.admin.snapandsplit.start.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.admin.snapandsplit.HomeActivity;
import com.facebook.FacebookSdk;
import com.google.gson.Gson;
import com.admin.snapandsplit.R;
import com.admin.snapandsplit.login.login.LoginActivity;
import com.admin.snapandsplit.main.MainActivity;
import com.admin.snapandsplit.start.presenter.StartPagePresenter;
import com.admin.snapandsplit.start.presenter.StartPagePreviousLoginChecker;
import com.admin.snapandsplit.user.User;
import com.admin.snapandsplit.utils.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class StartPage extends Activity implements StartPageView{

    boolean openLoginPageFlag = true;
    ProgressBar progressBar;

    StartPagePresenter presenter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final String TAG = StartPage.class.getSimpleName ();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        displayHashKey ();

        presenter = new StartPagePreviousLoginChecker (this);

        FacebookSdk.sdkInitialize (getApplicationContext (), presenter);

        sharedPreferences = getSharedPreferences(Constants.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit ();

        checkPreviousPasswordLogin();

        setContentView (R.layout.start_page);

        progressBar = (ProgressBar) findViewById (R.id.progressBar);

    }

    void checkPreviousPasswordLogin(){
        Log.i (TAG, "Checking previous password login");
        String userJson = sharedPreferences.getString ("user", "");
        if(!userJson.equals ("") && !userJson.isEmpty ()){
            Log.i (TAG, "Provider is password");
            Gson gson = new Gson ();
            //String userJson = sharedPreferences.getString ("user", "");
            User user = gson.fromJson (userJson, User.class);
            if(user == null)
                return;
            String token = user.getAccessToken (); //sharedPreferences.getString ("accessToken", "");
            Log.i(TAG + " Token ", token);
            if(user.getProvider ().equals (Constants.PROVIDER_PASSWORD) && !token.equals ("")) {
                presenter.loginWithPassword (token);
                disableLoginPage ();
            }

        }
    }

    @Override
    public void writeToSharedPreferences (String provider, String uid, String accessToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit ();

        Log.i(TAG, provider + " " + uid + " " + accessToken);
        editor.putString ("provider", provider);
        editor.putString ("uid", uid);
        editor.putString ("accessToken", accessToken);
        editor.apply();
    }

    @Override
    public void showMessage (String message) {
        Toast.makeText (StartPage.this, message, Toast.LENGTH_SHORT).show ();
    }

    private void displayHashKey () {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.benjaminlize.loginapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray ());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }


    @Override
    protected void onResume () {
        super.onResume ();

        new Handler ().postDelayed (new Runnable () {

            @Override
            public void run () {
                if(!isNetworkAvailable ()){
                    progressBar.setVisibility (View.GONE);
                    Toast.makeText (StartPage.this, "Internet not available..." +
                            "\nPlease check your internet and try again", Toast.LENGTH_LONG).show ();
                    finish ();
                }
                if(openLoginPageFlag) {
                    openLoginPage ();
                }
            }

        }, 5000);
    }

    @Override
    public void openLoginPage () {
        Log.i(TAG, "Opening Login Page");
        startActivity (new Intent (StartPage.this, LoginActivity.class));
        finish ();

    }

    @Override
    public void openMainPage () {
        Log.i(TAG, "Opening Main Page");
        startActivity (new Intent(StartPage.this, HomeActivity.class));
        finish();
    }

    @Override
    public void disableLoginPage () {
        openLoginPageFlag = false;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
