package com.example.mypfe;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.util.Objects;


public class ApplicationDem extends Application  {
    public   static final String CHANNEL_ID="Channel11";
    private  static NotificationManager notificationManager;
    public static NotificationManager getNotificationManager() {
        return notificationManager;
    }


    private void createNotificationChannel1(String name, String description, int importance) {
        //for API 28+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //API28+
            NotificationChannel channel= new NotificationChannel(CHANNEL_ID,name,importance);
            channel.setDescription(description);
            //cannot be changed after
            notificationManager=getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);

        }


    }


    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel1("channel de fred","Channel pou l application de démonstration des notifications", NotificationManager.IMPORTANCE_DEFAULT);
    }


}




