package com.admin.snapandsplit.notifications.interactor;

import android.util.Log;
import android.view.View;


import com.admin.snapandsplit.notifications.NotificationActivity;
import com.admin.snapandsplit.notifications.User;
import com.admin.snapandsplit.notifications.presenter.OnNewPetitionListener;

import com.admin.snapandsplit.utils.Constants;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import com.admin.snapandsplit.notifications.NotificationActivity;
/**
 * Created by Vinay Nikhil Pabba on 07-02-2016.
 */
public class NotificationServiceInteractorImpl implements NotificationServiceInteractor, ValueEventListener, ChildEventListener {

    long petitionsCount = 0;
    Map<String, String> userPreferences = new HashMap<> ();
    OnNewPetitionListener listener;
    public NotificationActivity noti = new NotificationActivity();
    String s = noti.NotificationPicker();

    Firebase firebase = new Firebase (Constants.FIREBASE_REF);
    @Override
    public void startNotifications (User user, OnNewPetitionListener listener) {
       // this.userPreferences = user.getPreferences ();
        this.listener = listener;
        firebase.child ("users" + s).addListenerForSingleValueEvent (this);
    }

    @Override
    public void stopNotifications () {
        firebase.child ("users" + s).removeEventListener ((ChildEventListener) this);
    }

    @Override
    public void onDataChange (DataSnapshot dataSnapshot) {
        petitionsCount = dataSnapshot.getChildrenCount ();
        firebase.child ("users" + s).addChildEventListener (this);
    }

    @Override
    public void onCancelled (FirebaseError firebaseError) {

    }

    int currentCount = 0;

   /* @Override
    public void onChildAdded (DataSnapshot dataSnapshot, String s) {
        Petition newPetition = getPetition (dataSnapshot);

        if (newPetition == null)
            return;

        Log.i ("NotifInteractor", "New PetitionID " + newPetition.getmUniqueId ());
        currentCount++;
        if (currentCount > petitionsCount) {

            Log.i ("NotifInteractor", "CheckPoint 123");
            petitionsCount++;
            //listener.onNewPetitionAdded (newPetition);
            if (isUserPreferredPetition (newPetition)) {
                //petitionList.add (newPetition);
                listener.onNewPetition (newPetition);
            }
        }
        Log.i ("NotifInteractor", "New currentCount =  " + currentCount + " " + petitionsCount);
    }
*/
   // firebase.child("users/" + s).addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            System.out.println(dataSnapshot.getKey() + " was " + dataSnapshot.getValue());
            Log.d("obj", dataSnapshot.getKey() + " , " + dataSnapshot.getValue());
            final String str = dataSnapshot.getValue().toString();
        /*    btnSendSMS.setOnClickListener(new View.OnClickListener()

            {
                public void onClick(View v) {
                    sendNotification(str);
                }
            });*/

            listener.onNewPetition(str);

        }

    @Override
    public void onChildChanged (DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildMoved (DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved (DataSnapshot dataSnapshot) {

       /* Petition removedPetition = getPetition (dataSnapshot); //dataSnapshot.getValue (Petition.class);
        Log.i ("NotifInteractor", "Removed PetitionID " + removedPetition.getmUniqueId ());
        if (currentCount == petitionsCount)
            petitionsCount--;
        currentCount--;
        if(isUserPreferredPetition (removedPetition)) {
            Log.i ("NotifInteractor", "Removed PetitionID " + removedPetition.getmUniqueId ());
            //petitionList.remove (removedPetition);
            Log.i ("NotifInteractor", "Removed Petition List Size = " + petitionsCount);
            //listener.onPetitionsListGenerated (petitionList);
        }
*/
    }
/*
    private Petition getPetition (DataSnapshot dataSnapshot) {
        Petition p = null;
        try {
            p = dataSnapshot.getValue (Petition.class);
        } catch (FirebaseException e) {
            Log.e ("NotifInteractor", e.getMessage ());
        }
        return p;
    }

    private boolean isUserPreferredPetition (Petition petition) {
        if (userPreferences.containsKey (petition.getmCategory ()))
            return true;
        else
            return false;
    }
*/
}
