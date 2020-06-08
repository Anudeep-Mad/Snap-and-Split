package com.admin.snapandsplit.login.login.email.presenter;

import com.admin.snapandsplit.user.User;


public interface OnEmailLoginFinishedListener {

    void onSuccess(User user);
     String sendNoti(String user);

    void onEmailError();

    void onPasswordError();

}
