package com.admin.snapandsplit.login.forgot.view;


public interface ForgotPasswordView {

    void showProgressDialog(String message);

    void hideProgressDialog();

    void showMessage(String message);

    void showChangePasswordDialog();

    void hideChangePasswordDialog();

    void openLoginPage();

}
