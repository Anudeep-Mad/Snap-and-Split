package com.admin.snapandsplit.login.register.presenter;


public interface OnRegisterFinishedListener {

    void onSuccess(String uid, String token);

    void onFailure(int errorCode);

}
