package com.deltahacks4.deltamind.deltamind;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreateReminder extends AppCompatActivity {

    Button saveButton;
    Button takePictureButton;
    private DBActivity db;
    private String fileName;
    private String rmd_title;
    private String pic_absolute_path;

    static final int REQUEST_IMAGE_CAPTURE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        db = new DBActivity(this);

        saveButton = (Button)findViewById(R.id.saveButton);
        takePictureButton = findViewById(R.id.createReminderButton);

        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                EditText title = (EditText)findViewById(R.id.title);
                System.out.println("title: " + title.getText());

                EditText description = (EditText)findViewById(R.id.description);
                System.out.println("description: " + description.getText());

                DatePicker entryDate = (DatePicker)findViewById(R.id.entryDate);
                String entryMonth = Integer.toString(entryDate.getMonth() + 1).length() > 1 ? Integer.toString(entryDate.getMonth() + 1) : "0"+Integer.toString(entryDate.getMonth() + 1);
                String start_day_time = entryDate.getYear() + "-" + entryMonth + "-" + entryDate.getDayOfMonth() + " ";

                TimePicker entryTime = (TimePicker)findViewById(R.id.entryTime);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String hour = Integer.toString(entryTime.getHour()).length() > 1 ? Integer.toString(entryTime.getHour()) : "0" + entryTime.getHour();
                    String start_time = hour + ":" + entryTime.getMinute();
                    start_day_time = start_day_time + start_time;
                    System.out.println("start_date_time: " + start_day_time);

                }else{
                    String hour = Integer.toString(entryTime.getCurrentHour()).length() > 1 ? Integer.toString(entryTime.getCurrentHour()) : "0" + entryTime.getCurrentHour();
                    String start_time = hour + ":" + entryTime.getCurrentMinute();
                    start_day_time = start_day_time + start_time;
                    System.out.println("start_date_time: " + start_day_time);
                }

                DatePicker exitDate = (DatePicker)findViewById(R.id.exitDate);
                String exitMonth = Integer.toString(exitDate.getMonth() + 1).length() > 1 ? Integer.toString(exitDate.getMonth() + 1) : "0"+Integer.toString(exitDate.getMonth() + 1);
                String end_day_time = exitDate.getYear() + "-" + exitMonth + "-" + exitDate.getDayOfMonth() + " ";
                System.out.println("exitDate: " + end_day_time);

                TimePicker exitTime = (TimePicker)findViewById(R.id.exitTime);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String hour = Integer.toString(exitTime.getHour()).length() > 1 ? Integer.toString(exitTime.getHour()) : "0" + exitTime.getHour();
                    String end_time = hour + ":" + exitTime.getMinute();
                    end_day_time = end_day_time + end_time;
                    System.out.println("exit_date_time: " + end_day_time);
                }else{
                    String hour = Integer.toString(exitTime.getCurrentHour()).length() > 1 ? Integer.toString(exitTime.getCurrentHour()) : "0" + exitTime.getCurrentHour();
                    String end_time = hour + ":" + exitTime.getCurrentMinute();
                    end_day_time = end_day_time + end_time;
                    System.out.println("exit_date_time: " + end_day_time);
                }
                Spinner frequency = (Spinner)findViewById(R.id.spinner);
                System.out.println("frequency: " + frequency.getSelectedItem());


                Reminder rmd = new Reminder(title.getText().toString(), description.getText().toString(), start_day_time, end_day_time, frequency.getSelectedItem().toString(), "1");
                ArrayList<SubReminder> sub_rmds = rmd.getAllSubreminders();


                boolean savedToPicDB = false;
                long rmd_id;

                for(int i=0; i<sub_rmds.size(); i++){
                    // save reminder into reminder table
                    rmd_id = db.insertRmd(rmd, sub_rmds.get(i));
                    if(rmd_id == -1) System.out.println("error when saving to reminder table");
                    // save associated picture
                    savedToPicDB = db.insertPicture(rmd_id, fileName);
                    if(savedToPicDB) System.out.println("error when saving to picture table");
                }
                rmd_title = rmd.getTitle();
                scheduleNotification(sub_rmds.size());

                Intent newIntent = new Intent(view.getContext(), HomeActivity.class);
                startActivity(newIntent);

            }
        });


        this.takePictureButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(CreateReminder.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(CreateReminder.this, new String[] {Manifest.permission.CAMERA}, 100);
                } else {
                    takePicture();
                }
            }
        });


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String permission[], int[] grantResults) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        takePicture();
                    } else {
                        this.takePictureButton.setClickable(false);
                    }
            }
        }


    public void scheduleNotification(int times) {
//        int multiplier = 60000;
        int multiplier = 10000; // delay 10 seconds to debug
        for (int i = 0; i < times; i++) {
            Intent notificationIntent = new Intent(this, MyNotificationPublisher.class);
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, i);
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, getNotification());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            //Debug. Schedule at 5 seconds later.
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + multiplier, pendingIntent);
            multiplier += 60000;
        }
    }

    private Notification getNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        Intent detailActivity = new Intent(this, DetailActivity.class);
        System.out.println("filename: " + fileName);
        detailActivity.putExtra("fileName", fileName);
        detailActivity.putExtra("title", rmd_title);
        detailActivity.putExtra("pic_absolute_path", pic_absolute_path);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, detailActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle("Title");
        builder.setContentText("Remember to take your medicine");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        builder.setAutoCancel(true);
        return builder.build();
    }



    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File Object to store the picture taken
            File pictureFile = null;

            try {
                pictureFile = configImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (pictureFile != null) {
                this.fileName = pictureFile.getName();
                this.pic_absolute_path = pictureFile.getAbsolutePath();
                Uri pictureURI = FileProvider.getUriForFile(this, "com.deltahacks4.deltamind.deltamind.fileprovider", pictureFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File configImageFile() throws IOException {
        String photoPath = getPackageName();

        try {
            photoPath = getPackageManager().getPackageInfo(photoPath, 0).applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // TODO: Change file name when integrating with View
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        return image;
    }


    }
