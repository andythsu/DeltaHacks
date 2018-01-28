package com.deltahacks4.deltamind.deltamind;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        scheduleNotification(2);
    }

    public void scheduleNotification(int times){
        int multiplier = 10000; // delay 10 seconds
        for(int i=0; i<times; i++){
            Intent notificationIntent = new Intent(this, MyNotificationPublisher.class);
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, i);
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, getNotification());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            //Debug. Schedule at 5 seconds later.
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+multiplier, pendingIntent);
            multiplier += 10000;
        }
    }

    private Notification getNotification(){
        Notification.Builder builder = new Notification.Builder(this);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class), 0);
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle("Title");
        builder.setContentText("Context");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        return builder.build();
    }
}
