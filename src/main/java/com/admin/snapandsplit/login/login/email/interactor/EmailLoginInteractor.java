package com.admin.snapandsplit.login.login.email.interactor;


import com.admin.snapandsplit.login.login.email.presenter.OnEmailLoginFinishedListener;


public interface EmailLoginInteractor {

    void authenticateWithEmail(String email, String password, OnEmailLoginFinishedListener listener);

}
