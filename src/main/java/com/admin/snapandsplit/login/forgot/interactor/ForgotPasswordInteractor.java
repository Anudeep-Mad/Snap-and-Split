package com.admin.snapandsplit.login.forgot.interactor;

import com.admin.snapandsplit.login.forgot.presenter.OnPasswordResetFinishedListener;


public interface ForgotPasswordInteractor {

    void sendResetEmail(String email, OnPasswordResetFinishedListener listener);

    void changePassword(String email, String oldPassword, String newPassword);

}
