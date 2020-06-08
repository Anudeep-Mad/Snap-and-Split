package com.admin.snapandsplit.notifications;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.admin.snapandsplit.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NotiActivity extends Activity {

    // A integer, that identifies each notification uniquely
    public static final int NOTIFICATION_ID = 1;
    Button btnSendSMS;
   public NotificationActivity noti = new NotificationActivity();
    private static final String TAG = "noti";

            String s;
    String str;
    final Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    final Firebase myFirebaseRef = new Firebase("https://snapnsplit.firebaseio.com");

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_send);

        s = noti.NotificationPicker();



        btnSendSMS = (Button)

                findViewById(R.id.btnSendSMS);

     /*   btnSendSMS.setOnClickListener(new View.OnClickListener()

                                      {
                                          public void onClick(View v) {
                                              NotificationCompat.Builder builder = new NotificationCompat.Builder(NotiActivity.this);

                                              //icon appears in device notification bar and right hand corner of notification
                                              builder.setSmallIcon(R.drawable.ic_stat_ic_notification);

                                              // This intent is fired when notification is clicked
                                              Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://javatechig.com/"));
                                              PendingIntent pendingIntent = PendingIntent.getActivity(NotiActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                                              builder.setSound(defaultSoundUri);
                                              // Set the intent that will fire when the user taps the notification.
                                              builder.setContentIntent(pendingIntent);

                                              builder.setAutoCancel(true);
                                              builder.setWhen(System.currentTimeMillis());
                                              // Large icon appears on the left of the notification
                                              builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));

                                              // Content title, which appears in large type at the top of the notification
                                              builder.setContentTitle("Snap 'N' Split");

                                              // Content text, which appears in smaller text below the title
                                              builder.setContentText("Hello!! The divided amount is 500");

                                              // The subtext, which appears under the text on newer devices.
                                              // This will show-up in the devices with Android 4.2 and above only
                                              builder.setSubText("Tap to pay the amount");

                                              NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                                              // Will display the notification in the notification bar
                                              notificationManager.notify(NOTIFICATION_ID, builder.build());

                                          }
                                      }

        );
*/




     /*   myFirebaseRef.child("users/"+s+"/inbound-messages").on("child_added", function(newMessageSnapshot)
        {
          sendNotification(newMessageSnapshot.val());
           // newMessageSnapshot.ref().remove();
        });*/



     //   Firebase postRef = myFirebaseRef.child("users/" + s);
      //  Map<String, String> post1 = new HashMap<String, String>();
      //  post1.put("author", "gracehop");
       // post1.put("title", "Announcing COBOL, a New Programming Language");

        btnSendSMS.setOnClickListener(new View.OnClickListener()

        {
            public void onClick(View v) {
                Firebase postRef = myFirebaseRef.child("users").child(s).child("transactions");
                Map<String, Object> post1 = new HashMap<String, Object>();
                post1.put("author", "gracehop");
               // postRef.push().getKey().setValue(post1);
                String g= postRef.push().getKey();
                myFirebaseRef.child ("users").child(s).child("transactions").child(g).setValue(post1);

            }
            });

            //myFirebaseRef.child("users/" + s).push();
        }

//Send a message to another user


                // Your application layout file


                /**
                 * Send simple notification using the NotificationCompat API.
                 */

    public void sendNotification(String str) {

        // Use NotificationCompat.Builder to set up our notification.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        //icon appears in device notification bar and right hand corner of notification
        builder.setSmallIcon(R.drawable.ic_stat_ic_notification);

        // This intent is fired when notification is clicked
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://javatechig.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setSound(defaultSoundUri);
        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        // Large icon appears on the left of the notification
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));

        // Content title, which appears in large type at the top of the notification
        builder.setContentTitle("Snap 'N' Split");
        // Content text, which appears in smaller text below the title
        builder.setContentText(str);

        // The subtext, which appears under the text on newer devices.
        // This will show-up in the devices with Android 4.2 and above only
        builder.setSubText("Tap to view documentation about notifications.");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Will display the notification in the notification bar
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }




}
