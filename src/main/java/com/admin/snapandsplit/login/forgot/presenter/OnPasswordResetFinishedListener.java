package com.admin.snapandsplit.login.forgot.presenter;


public interface OnPasswordResetFinishedListener {

    void onSuccess(int flag);

    void onFailure(String message);

}
