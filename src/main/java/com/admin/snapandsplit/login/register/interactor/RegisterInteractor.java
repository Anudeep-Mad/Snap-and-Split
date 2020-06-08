package com.admin.snapandsplit.login.register.interactor;


import com.admin.snapandsplit.login.register.presenter.OnRegisterFinishedListener;


public interface RegisterInteractor {

    void registerUser(String email, String password,String username,String mobnum,String acc_num,OnRegisterFinishedListener listener);

}
