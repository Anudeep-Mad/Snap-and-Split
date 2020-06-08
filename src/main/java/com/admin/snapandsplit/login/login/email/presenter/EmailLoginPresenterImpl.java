package com.admin.snapandsplit.login.login.email.presenter;

import android.util.Log;

import com.admin.snapandsplit.HomeActivity;
import com.admin.snapandsplit.login.login.email.interactor.EmailLoginInteractor;
import com.admin.snapandsplit.login.login.email.interactor.EmailLoginInteractorImpl;
import com.admin.snapandsplit.login.login.email.view.EmailLoginFragmentView;
import com.admin.snapandsplit.login.login.email.view.LoginActivityFragment;
import com.admin.snapandsplit.user.User;



public class EmailLoginPresenterImpl implements EmailLoginPresenter, OnEmailLoginFinishedListener {

	private EmailLoginFragmentView view;
	private EmailLoginInteractor interactor;
private HomeActivity home;

	public static String us;
	public static String dn;
	public static String mb;
	public static String em;
	public static String acc_num;
	

	public EmailLoginPresenterImpl(LoginActivityFragment loginActivityFragment) {
		this.view = loginActivityFragment;
		interactor = new EmailLoginInteractorImpl();
	}

	@Override
	public void authenticateCredentials(String email, String password) {

		view.showProgressDialog();
		interactor.authenticateWithEmail(email, password, this);
		//AuthenticateUser.authWithEmailPassword (email, password, loginActivityFragment.getFragment ().getContext ());
	}

	@Override
	public void onEmailError() {
		view.hideProgressDialog();
		view.emailError();
	}

	@Override
	public void onPasswordError() {
		view.hideProgressDialog();
		view.passwordError();
	}

	@Override
	public void onSuccess(User user) {
		view.writeToSharedPreferences(user);
		Log.i("EMAIL PRESENTER", "UID = " + user.getUid());
		view.hideProgressDialog();
		view.openMainPage();
		//home.sendUserId(user);
		us=user.getUid();
		dn=user.getDisplayName();
		mb=user.getMobileNumber();
		em=user.getEmail();
		acc_num=user.getAccountNumber();
	}


	public String sendNoti(String user) {
		//view.writeToSharedPreferences(user);
		//Log.i("EMAIL PRESENTER", "UID = " + user.getUid());
		//view.hideProgressDialog();
		//view.openMainPage();
		return user;
	}
}