package com.example.amplifieradmin.notification;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.example.amplifieradmin.AdsListStatusActivity;
import com.example.amplifieradmin.HomeActivity;
import com.example.amplifieradmin.R;
import com.example.amplifieradmin.data.model.PushNotificationResp;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    Random rand = new Random();
    private PendingIntent pendingIntent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "RemoteMessage : " +"Yes");
        if (remoteMessage != null) {
            Log.e(TAG, "RemoteMessageData : " + new Gson().toJson(remoteMessage.getData()));
            if (remoteMessage.getData().size() > 0) {
                Type type = new TypeToken<PushNotificationResp>() {
                }.getType();
                String dataStr = new Gson().toJson(remoteMessage.getData());
                PushNotificationResp data = new Gson().fromJson(dataStr, type);
                Intent intent = null;
                int n = rand.nextInt(5000) + 1;
                intent = new Intent(this, AdsListStatusActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                generateNotification(intent, data, n);

            }
        }


    }

    private void generateNotification(Intent intent, PushNotificationResp data, int n) {
        try {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(n);
            String channel_id = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                createNotificationChannels(notificationManager);
                channel_id = notificationManager.getNotificationChannel("com.spryntsllc.amplifieradmin").getId();
                 Log.e("!!!!", "CH ID : " + channel_id);

                intent.putExtra("importance", notificationManager.getNotificationChannel(channel_id).getImportance());
            }

            pendingIntent = PendingIntent.getActivity(this, n, intent, PendingIntent.FLAG_IMMUTABLE);
            int uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;


            RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification_small_layout);
            mRemoteViews.setImageViewResource(R.id.icon, R.drawable.ic_launcher_new);
            Log.e("flflf",data.getId());
            mRemoteViews.setTextViewText(R.id.tv_title, "" + data.getId());
            mRemoteViews.setTextViewText(R.id.message, "" + data.getMessage());


            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channel_id);
            notificationBuilder.setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setCustomContentView(mRemoteViews)
                    .setCustomBigContentView(mRemoteViews)
                    .setSmallIcon(R.drawable.ic_launcher_new)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setOnlyAlertOnce(true)
                    .setContentIntent(pendingIntent);
            notificationManager.notify(n, notificationBuilder.build());
            Log.d(TAG, "sendNotification");

        } catch (Exception e) {
            Log.e("GenNotification", "" + e.getLocalizedMessage());
        }

    }

    private void createNotificationChannels(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // NotificationChannel notificationChannel = new NotificationChannel("com.assettl.eazyship", "com.assettl.eazyship", NotificationManager.IMPORTANCE_HIGH);
            NotificationChannel notificationChannel = new NotificationChannel("com.spryntsllc.amplifieradmin", "com.spryntsllc.amplifieradmin", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);

            }
        }
    }


/*    private void generateNotification(Intent intent, Data data, int n) {
        try {
            long[] pattern = {500, 500, 500, 500, 500};
            Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                    R.drawable.fleet_onwer_jio_logo);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(n);
            String channel_id = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    createNotificationChannels(notificationManager);

                    // channel_id = notificationManager.getNotificationChannel("com.assettl.eazyship").getId();
                    channel_id = notificationManager.getNotificationChannel("com.spryntsllc.amplifieradmin").getId();
                // Log.d("!!!!", "CH ID : " + channel_id);

                    intent.putExtra("importance", notificationManager.getNotificationChannel(channel_id).getImportance());
            }

            pendingIntent = PendingIntent.getActivity(this, n, intent,PendingIntent.FLAG_ONE_SHOT);

            int uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;


            RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification_small_layout);
            mRemoteViews.setImageViewResource(R.id.icon, R.drawable.fleet_onwer_jio_logo);
            mRemoteViews.setTextViewText(R.id.tv_title, ""+data.getTitle());
            mRemoteViews.setTextViewText(R.id.tv_regno, ""+data.getVehicle());
            mRemoteViews.setTextViewText(R.id.tv_time, ""+data.getBody());
            mRemoteViews.setTextViewText(R.id.tv_origin, ""+data.getSource());
            mRemoteViews.setTextViewText(R.id.tv_destination, ""+data.getDestination());
            if(uiMode==Configuration.UI_MODE_NIGHT_YES){

                mRemoteViews.setTextColor(R.id.tv_title, ContextCompat.getColor(this, R.color.white));
                mRemoteViews.setTextColor(R.id.tv_regno, ContextCompat.getColor(this, R.color.white));
                mRemoteViews.setTextColor(R.id.tv_time, ContextCompat.getColor(this, R.color.white));
                mRemoteViews.setTextColor(R.id.tv_origin, ContextCompat.getColor(this, R.color.white));
                mRemoteViews.setTextColor(R.id.tv_destination, ContextCompat.getColor(this, R.color.white));
            }else {

                mRemoteViews.setTextColor(R.id.tv_title, ContextCompat.getColor(this, R.color.black));
                mRemoteViews.setTextColor(R.id.tv_regno, ContextCompat.getColor(this, R.color.black));
                mRemoteViews.setTextColor(R.id.tv_time, ContextCompat.getColor(this, R.color.black));
                mRemoteViews.setTextColor(R.id.tv_origin, ContextCompat.getColor(this, R.color.black));
                mRemoteViews.setTextColor(R.id.tv_destination, ContextCompat.getColor(this, R.color.black));
            }



            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channel_id);
            notificationBuilder.setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setCustomContentView(mRemoteViews)
                    .setCustomBigContentView(mRemoteViews)
                    .setSmallIcon(R.drawable.ic_notification_logo)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setOnlyAlertOnce(true)
                    .setContentIntent(pendingIntent);
            notificationManager.notify(n, notificationBuilder.build());
            Log.d(TAG, "sendNotification");

        } catch (Exception e) {
            Log.e("GenNotification", "" + e.getLocalizedMessage());
        }

    }

    private void generateGeofenceNotification(Intent intent, Data data, int n) {
        try {
            long[] pattern = {500, 500, 500, 500, 500};
            Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                    R.drawable.fleet_onwer_jio_logo);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(n);
            String channel_id = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                createNotificationChannels(notificationManager);

                // channel_id = notificationManager.getNotificationChannel("com.assettl.eazyship").getId();
                channel_id = notificationManager.getNotificationChannel("com.spryntsllc.amplifieradmin").getId();
                // Log.d("!!!!", "CH ID : " + channel_id);
                intent.putExtra("importance", notificationManager.getNotificationChannel(channel_id).getImportance());
            }

            pendingIntent = PendingIntent.getActivity(this, n, intent,PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channel_id);

            notificationBuilder.setSmallIcon(R.drawable.fleet_onwer_jio_logo);
            notificationBuilder.setLargeIcon(icon)
                    .setContentTitle("" + data.getTitle())
                    .setColor(getResources().getColor(R.color.colorPrimary))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setVibrate(pattern)
                   // .setSound(sound)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(data.getBody()))
                    .setContentIntent(pendingIntent)
                    .setSound(defaultSoundUri)
                    .build();
            notificationManager.notify(n, notificationBuilder.build());
            Log.d(TAG, "sendNotification");

        } catch (Exception e) {
            Log.e("GenNotification", "" + e.getLocalizedMessage());
        }

    }*/

}
