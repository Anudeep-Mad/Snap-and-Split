package com.admin.snapandsplit.start.view;


public interface StartPageView {

    void writeToSharedPreferences(String provider, String uid, String accessToken);

    void showMessage(String message);

    void openMainPage();

    void openLoginPage();

    void disableLoginPage();

}
