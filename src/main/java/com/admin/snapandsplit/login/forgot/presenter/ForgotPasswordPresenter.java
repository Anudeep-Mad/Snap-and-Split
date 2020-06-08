package com.admin.snapandsplit.login.forgot.presenter;


public interface ForgotPasswordPresenter {

    void resetPassword(String email);

    void changePassword(String email, String oldPassword, String newPassword);

}
