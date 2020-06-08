package com.admin.snapandsplit.login.login.facebook.view;

import com.admin.snapandsplit.user.User;


public interface FacebookLoginFragmentView {

    void openMainPage();

    void onError();

    void writeToSharedPrefernces(User user);

    void showProgressDialog();

    void hideProgressDialog();

}
