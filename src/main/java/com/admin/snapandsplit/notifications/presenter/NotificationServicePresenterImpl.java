package com.admin.snapandsplit.notifications.presenter;


import com.admin.snapandsplit.notifications.User;
import com.admin.snapandsplit.notifications.interactor.NotificationServiceInteractor;
import com.admin.snapandsplit.notifications.interactor.NotificationServiceInteractorImpl;
import com.admin.snapandsplit.notifications.view.NotificationServiceView;


/**
 * Created by Vinay Nikhil Pabba on 07-02-2016.
 */
public class NotificationServicePresenterImpl implements NotificationServicePresenter, OnNewPetitionListener {

    NotificationServiceView view;
    NotificationServiceInteractor interactor;

    public NotificationServicePresenterImpl (NotificationServiceView view) {

        this.view = view;
        interactor = new NotificationServiceInteractorImpl();

    }

    @Override
    public void startNotifications (User user) {
        interactor.startNotifications (user, this);
    }

    @Override
    public void onNewPetition (String str) {
        view.createNotification (str);
    }

    @Override
    public void stopNotifications () {
        interactor.stopNotifications ();
    }
}
