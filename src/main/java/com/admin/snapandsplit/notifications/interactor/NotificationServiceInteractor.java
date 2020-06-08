package com.admin.snapandsplit.notifications.interactor;


import com.admin.snapandsplit.notifications.User;
import com.admin.snapandsplit.notifications.presenter.OnNewPetitionListener;

/**
 * Created by Vinay Nikhil Pabba on 07-02-2016.
 */
public interface NotificationServiceInteractor {

    void startNotifications(User user, OnNewPetitionListener listener);

    void stopNotifications();

}
