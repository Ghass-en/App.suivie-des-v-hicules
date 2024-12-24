package com.example.mypfe;

import static com.example.mypfe.ApplicationDem.CHANNEL_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NotificationActivity2 extends AppCompatActivity {
    private  int notificationId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification2);
        findViewById(R.id.buttonNotify1).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=((EditText)findViewById(R.id.edit_text_title)).getText().toString();
                String message=((EditText)findViewById(R.id.edit_text_message)).getText().toString();
                sendNotificationOnChannel(title,message,CHANNEL_ID, NotificationCompat.PRIORITY_DEFAULT);
            }


        });
    }
    private void sendNotificationOnChannel(String title, String message, String channelId, int priority) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(),channelId)
                .setSmallIcon(R.drawable.baseline_star_24)
                .setContentTitle(title)
                .setContentText("id-"+notificationId+"  -   "+message)
                .setPriority(priority);

        ApplicationDem.getNotificationManager().notify(++notificationId,notification.build());

    }
}