package com.example.notification;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "my_channel_id";
    private static int ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotificationHelper.createNotificationChannels(this);

        Button btn = findViewById(R.id.notification);
        btn.setOnClickListener(v->{
            NotificationHelper.sendNotification(ID, NotificationHelper.CHANNEL_ID_DEFAULT, this, "Custom",
                    "Custom posiwadomienie",1, R.drawable.mountain, R.raw.ding);
            ID++;
        });

        Button btnLomg = findViewById(R.id.notificationLong);
        btnLomg.setOnClickListener(v->{
            NotificationHelper.sendNotification(ID, NotificationHelper.CHANNEL_ID_DEFAULT, this, "Custom",
                    "Custom posiwadomienie",1, R.drawable.mountain, R.raw.ding);
            ID++;
        });

        Button btnCustom = findViewById(R.id.notificationCustom);
        btnCustom.setOnClickListener(v->{
            NotificationHelper.sendNotification(ID, NotificationHelper.CHANNEL_ID_HIGH, this, "Custom",
                    "Custom posiwadomienie",2, R.drawable.mountain, R.raw.ding);
            ID++;
        });
    }
/*
    public void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Kanal Powiadomien";
            String description = "Opis kanalu powiadomiec";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name , importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendNotification(){
        // zezwolenie na wyslanie powiadomienia
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if(checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS},1);
                return;
            }
        }
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Nowe powiadomienie")
                .setContentText("to jest example msg")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent) // po kliknieciu prowadzi do aplikacji
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    private void sendNotificationLong(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if(checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS},1);
                return;
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Nowe powiadomienie LONG") //  \/ make whole text (long, more the two rows) display in message window
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. In tempus vitae ex sit amet tincidunt. Ut lectus dui, dapibus placerat suscipit eget, aliquam vel massa. Sed viverra interdum lacus eget."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent) // po kliknieciu prowadzi do aplikacji
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }
*/
}