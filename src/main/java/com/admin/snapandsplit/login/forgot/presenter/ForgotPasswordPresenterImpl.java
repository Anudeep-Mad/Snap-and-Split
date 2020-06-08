package com.admin.snapandsplit.login.forgot.presenter;

import android.util.Log;

import com.admin.snapandsplit.login.forgot.interactor.ForgotPasswordInteractor;
import com.admin.snapandsplit.login.forgot.interactor.ForgotPasswordInteractorImpl;
import com.admin.snapandsplit.login.forgot.view.ForgotPasswordView;
import com.admin.snapandsplit.utils.Constants;



public class ForgotPasswordPresenterImpl implements
        ForgotPasswordPresenter, OnPasswordResetFinishedListener {

    String TAG = ForgotPasswordPresenterImpl.class.getSimpleName();

    ForgotPasswordView view;
    ForgotPasswordInteractor interactor;

    public ForgotPasswordPresenterImpl(ForgotPasswordView view){
        this.view = view;
        interactor = new ForgotPasswordInteractorImpl ();
    }

    @Override
    public void resetPassword (String email) {
        view.showProgressDialog ("Sending Reset Email...");
        interactor.sendResetEmail (email, this);
    }

    @Override
    public void changePassword (String email, String oldPassword, String newPassword) {
        view.hideChangePasswordDialog ();
        Log.i (TAG, email + " " + oldPassword + " " + newPassword);
        interactor.changePassword (email, oldPassword, newPassword);
        view.showProgressDialog ("Updating your new password...");
    }

    @Override
    public void onFailure (String message) {
        view.hideProgressDialog ();
        view.showMessage (message);
    }

    @Override
    public void onSuccess (int flag) {
        Log.i (TAG + flag, "OnSuccess");
        view.hideProgressDialog ();
        switch (flag) {
            case Constants.RESET:
                view.showMessage ("New Password has been mailed to your email");
                view.showChangePasswordDialog ();
                break;
            case Constants.CHANGE:
                view.showMessage ("Password Changed Successfully!");
                view.openLoginPage ();
                break;
        }

    }
}
