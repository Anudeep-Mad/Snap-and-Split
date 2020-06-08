package com.admin.snapandsplit.login.login.email.view;

import com.admin.snapandsplit.user.User;


public interface EmailLoginFragmentView {

    void emailError();

    void passwordError();

    void openMainPage();

    void writeToSharedPreferences(User user);

    String sendUserId(User user);

    void showProgressDialog();

    void hideProgressDialog();

}