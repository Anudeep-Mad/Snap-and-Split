package com.admin.snapandsplit.start.presenter;


public interface OnTokenLoginFinishedListener {

    void onLoginSuccessful(String provider, String uid, String accessToken);

    void onLoginUnsuccessful();

}
