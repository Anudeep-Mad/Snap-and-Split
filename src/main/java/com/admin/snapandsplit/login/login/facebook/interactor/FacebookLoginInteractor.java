package com.admin.snapandsplit.login.login.facebook.interactor;

import com.admin.snapandsplit.login.login.facebook.presenter.OnFacebookLoginFinishedListener;


public interface FacebookLoginInteractor {

    void requestData(OnFacebookLoginFinishedListener listener);

}
