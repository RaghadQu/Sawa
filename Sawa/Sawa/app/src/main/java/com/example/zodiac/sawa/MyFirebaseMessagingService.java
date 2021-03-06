package com.example.zodiac.sawa;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Rabee on 5/13/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        //Log.d("Notification", "Received" + remoteMessage.getData().);
        //Log.d("FROOOm", "From: " + remoteMessage.getFrom());
        //Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        int count = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("notifications_counter",
                0);


        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("notifications_counter",
                ++count).commit();
        count = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("notifications_counter",
                0);
        Log.d("notifications_counter1222", " " + count);
        //check if the apploication is in the foreground
        int state = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("isRunning",
                0);
        if (state == 0)
            createNotification(count + "");
        else Log.d("Notficationcounter", "" + count);
        HomeTabbedActivity.showBadge(getApplicationContext());

    }

    private void createNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Ibrahim")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
    }
}
