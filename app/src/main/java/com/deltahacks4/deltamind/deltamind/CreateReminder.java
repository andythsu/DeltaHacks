package com.deltahacks4.deltamind.deltamind;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Date;

public class CreateReminder extends AppCompatActivity {

    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        saveButton = (Button)findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                EditText title = (EditText)findViewById(R.id.title);
                System.out.println("title: " + title.getText());

                EditText description = (EditText)findViewById(R.id.description);
                System.out.println("description: " + description.getText());

                DatePicker entryDate = (DatePicker)findViewById(R.id.entryDate);
                System.out.println("entryDate: " + entryDate.getYear() + ":" + entryDate.getMonth() + ":" + entryDate.getDayOfMonth());

                TimePicker entryTime = (TimePicker)findViewById(R.id.entryTime);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    System.out.println("entryTime: " + entryTime.getHour() + ":" + entryTime.getMinute());
                }else{
                    System.out.println("entryTime: " + entryTime.getCurrentHour() + ":" + entryTime.getCurrentMinute());

                }

                DatePicker exitDate = (DatePicker)findViewById(R.id.exitDate);
                System.out.println("exitDate: " + exitDate.getYear() + "/" + exitDate.getMonth() + "/" + exitDate.getDayOfMonth());

                TimePicker exitTime = (TimePicker)findViewById(R.id.exitTime);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    System.out.println("exitTime: " + exitTime.getHour() + "/" + exitTime.getMinute());
                }else{
                    System.out.println("exitTime: " + exitTime.getCurrentHour() + "/" + exitTime.getCurrentMinute());
                }
                Spinner frequency = (Spinner)findViewById(R.id.spinner);
                System.out.println("frequency: " + frequency.getSelectedItem());
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
        };

    }
