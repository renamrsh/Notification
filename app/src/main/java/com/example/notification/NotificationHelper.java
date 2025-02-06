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
    // kanal ma importance wieksza niz same powiadomienia

    public static void sendNotification(int NOTIFICATION_ID, int Priority, AppCompatActivity activity, String title, String message, int styleType, Integer largeIconResID, Integer sound){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(activity, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
               ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
                return;
            }
        }
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //priorytet kanalu powiadomien, poniewaz powiadomiana nie dzialaja
            //trzeba stworzyc kilka kanalow, i wybierac w zaleznosci od potrzebnej importance
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
            if(sound!=null){
                Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://"+activity.getApplicationContext()
                        .getPackageName()+ "/"+sound);
                channel.setSound(soundUri, null);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title) //  \/ make whole text (long, more the two rows) display in message window
                .setContentText(message)
                .setPriority(Priority)
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
