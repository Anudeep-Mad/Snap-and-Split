package com.admin.snapandsplit.notifications.presenter;


import com.admin.snapandsplit.notifications.User;

/**
 * Created by Vinay Nikhil Pabba on 07-02-2016.
 */
public interface NotificationServicePresenter {

    void startNotifications(User user);

    void stopNotifications();

}
