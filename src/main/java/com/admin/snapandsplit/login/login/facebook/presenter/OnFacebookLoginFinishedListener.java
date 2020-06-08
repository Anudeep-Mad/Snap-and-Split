package com.admin.snapandsplit.login.login.facebook.presenter;

import com.admin.snapandsplit.user.User;


public interface OnFacebookLoginFinishedListener {

    void onFirebaseLoginSuccess(User user);

    void onFirebaseLoginFailure();

}
