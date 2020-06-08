package com.admin.snapandsplit.start.interactor;


import com.admin.snapandsplit.start.presenter.OnTokenLoginFinishedListener;


public interface StartPageInteractor {

    void loginWithToken (String provider, String accessToken, OnTokenLoginFinishedListener listener);

}
