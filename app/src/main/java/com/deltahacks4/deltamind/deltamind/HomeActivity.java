package com.deltahacks4.deltamind.deltamind;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    List<String> reminders = new ArrayList<String>();

    private ListView mListView;
    private DBActivity db;
    private TextView no_reminder_label;
    private ArrayList<String> items;
    Button createButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Configure UI Binding
        setContentView(R.layout.activity_home);
        mListView = (ListView) findViewById(R.id.string_arraylist);
        items = new ArrayList<>();

        db = new DBActivity(this);

        no_reminder_label = (TextView) findViewById(R.id.no_rmd_label);

        Cursor res = db.fetchRmds(); // fetch reminders everytime it is redirected to homepage
        Cursor pic = db.fetchPictures();
        Toast.makeText(this, Integer.toString(pic.getCount()), Toast.LENGTH_LONG).show();
        if(res.getCount() == 0){
            no_reminder_label.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        }else{
            while(res.moveToNext()){
                items.add("Title: " + res.getString(1) + "\n" + "Happening time: " + res.getString(3));
            }
            // Adaptor for list view from array data
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView view = (TextView) super.getView(position, convertView, parent);
                    view.setText(getItem(position));
                    return view;
                }
            };
            mListView.setAdapter(adapter);
            mListView.setVisibility(View.VISIBLE);
            no_reminder_label.setVisibility(View.INVISIBLE);
        }

        createButton = (Button)findViewById(R.id.createReminderButton);
        // createButton listener
        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateReminder.class);
                startActivity(intent);
            }
        });

        scheduleNotification(2);




//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(HomeActivity.this, CreateReminder.class);
//                intent.putExtra("crypto", items.get(position));
//                startActivity(intent);
//            }
//        });


    }

    public void scheduleNotification(int times) {
        int multiplier = 10000; // delay 10 seconds
        for (int i = 0; i < times; i++) {
            Intent notificationIntent = new Intent(this, MyNotificationPublisher.class);
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, i);
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, getNotification());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            //Debug. Schedule at 5 seconds later.
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + multiplier, pendingIntent);
            multiplier += 10000;
        }
    }

    private Notification getNotification() {
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