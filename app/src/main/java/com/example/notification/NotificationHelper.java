package com.example.notification;


import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class NotificationHelper {
    private static final String CHANNEL_ID = "default_channel";
    private static final String CHANNEL_NAME = "Kanał Powiadomień";
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID_LOW = "Channel_low_priority";
    private static final String CHANNEL_ID_DEFAULT = "Channel_default_priority";
    private static final String CHANNEL_ID_HIGH = "Channel_high_priority";
    // kanal ma importance wieksza niz same powiadomienia

    public static void createNotificationChannels(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channelow = new NotificationChannel(CHANNEL_ID_LOW, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            NotificationChannel channeldefault = new NotificationChannel(CHANNEL_ID_DEFAULT, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel channelhigh = new NotificationChannel(CHANNEL_ID_HIGH, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);


            if(notificationManager!=null){
                notificationManager.createNotificationChannel(channelow);
                notificationManager.createNotificationChannel(channeldefault);
                notificationManager.createNotificationChannel(channelhigh);
            }
        }

    }

    public static void sendNotification(int NOTIFICATION_ID, int Priority, AppCompatActivity activity, String title, String message, int styleType, Integer largeIconResID, Integer sound){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(activity, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
               ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
                return;
            }
        }

        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title) //  \/ make whole text (long, more the two rows) display in message window
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                //.setContentIntent(pendingIntent) // po kliknieciu prowadzi do aplikacji
                .setAutoCancel(true);

        if(largeIconResID!=null){
            Bitmap largeIcon = BitmapFactory.decodeResource(activity.getResources(), largeIconResID);
            builder.setLargeIcon(largeIcon);
        }

        switch(styleType){
            case 1:
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                break;
            case 2:
                Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), largeIconResID);
                builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).setBigContentTitle(title));
                break;
            case 3:
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                inboxStyle.addLine(message);
                inboxStyle.addLine("Dodatkowa linia tekstu");
                builder.setStyle(inboxStyle);
                break;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}
