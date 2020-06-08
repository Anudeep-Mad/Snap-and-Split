package com.admin.snapandsplit.login.register.presenter;

import com.firebase.client.FirebaseError;
import com.admin.snapandsplit.login.register.interactor.RegisterInteractor;
import com.admin.snapandsplit.login.register.interactor.RegisterInteractorImpl;
import com.admin.snapandsplit.login.register.view.RegisterActivity;
import com.admin.snapandsplit.login.register.view.RegisterActivityView;


public class RegisterPresenterImpl implements RegisterPresenter, OnRegisterFinishedListener {

    RegisterInteractor interactor;
    RegisterActivityView view;

    public RegisterPresenterImpl(RegisterActivity view){

        this.view = view;
        interactor = new RegisterInteractorImpl ();

    }

    @Override
    public void createUser (String email, String password,String username,String mobnum,String acc_num) {
        view.showProgressBar ();
        if(validate (email, password))
            interactor.registerUser (email, password,username,mobnum,acc_num, this);
    }

    public boolean validate(String email, String password) {

        boolean valid = true;

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.hideProgressBar ();
            view.registrationError ("Enter a valid email address");
            valid = false;
        }

        if (password.isEmpty() || password.length() < 5 || password.length() > 20) {
            view.hideProgressBar ();
            view.registrationError ("Between 4 and 20 alphanumeric characters");
            valid = false;
        }

        return valid;
    }

    @Override
    public void onFailure (int errorCode) {
        view.hideProgressBar ();
        switch (errorCode){
            case FirebaseError.EMAIL_TAKEN:
                view.hideProgressBar ();
                view.registrationError ("Email already taken\nTry logging in");
                break;

            case FirebaseError.INVALID_EMAIL:
                view.hideProgressBar ();
                view.registrationError ("Invalid email. Please try again");
                break;

            default :
                view.hideProgressBar ();
                view.registrationError ("Error in creating user - " + errorCode);
        }
    }

    @Override
    public void onSuccess (String uid, String token) {
        view.writeToSharedPreferences (uid, token);
        view.hideProgressBar ();
        view.openHomePage ();
    }
}
