package com.admin.snapandsplit.login.register.view;


public interface RegisterActivityView {

    void openHomePage();

    void registrationError(String message);

    void showProgressBar();

    void hideProgressBar();

    void writeToSharedPreferences(String uid, String token);

}
