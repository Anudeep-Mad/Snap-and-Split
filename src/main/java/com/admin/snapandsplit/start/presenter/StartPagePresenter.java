package com.admin.snapandsplit.start.presenter;

import com.facebook.FacebookSdk;


public interface StartPagePresenter extends FacebookSdk.InitializeCallback {

    void loginWithPassword(String accessToken);
}