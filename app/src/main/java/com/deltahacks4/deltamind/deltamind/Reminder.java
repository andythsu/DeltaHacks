package com.deltahacks4.deltamind.deltamind;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Andy Su on 2018/1/27.
 */

public class Reminder {
    private String title;
    private String description;
    private String start_day_time;
    private String end_day_time;
    private String occurrence;
    private String happening_day_time;

    public Reminder(String title, String description, String start_day_time, String end_day_time, String occurrence){
        this.title = title;
        this.description = description;
        this.start_day_time = start_day_time;
        this.end_day_time = end_day_time;
        this.occurrence = occurrence;
    }

    public Reminder(String title, String description, String start_day_time, String end_day_time, String occurrence, String happening_day_time){
        this.title = title;
        this.description = description;
        this.start_day_time = start_day_time;
        this.end_day_time = end_day_time;
        this.occurrence = occurrence;
        // happening_day_time is to distinguish between "big reminder" and "small reminder"
        this.happening_day_time = happening_day_time;
    }

    // getters
    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public String getStart_day_time(){
        return this.start_day_time;
    }

    public String getEnd_day_time(){
        return this.end_day_time;
    }

    public String getHappening_day_time(){
        return this.happening_day_time;
    }

    public ArrayList<Reminder> getAllRmds(){
        // this is the stuff that will go to DB
        return getAllRmds(this.occurrence);
    }

    private ArrayList<Reminder> getAllRmds(String occurrence){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar end_c = Calendar.getInstance();
        Calendar start_c = Calendar.getInstance();
        try{
            if(occurrence.equalsIgnoreCase("every 10 seconds")){
                end_c.setTime(dateFormatter.parse(this.end_day_time));
                start_c.setTime(dateFormatter.parse(this.start_day_time));
                int frequency = 10;
                return calculateReminders(start_c, end_c, Calendar.SECOND, frequency);
            }else if(occurrence.equalsIgnoreCase("every 1 day")){
                end_c.setTime(dateFormatter.parse(this.end_day_time));
                start_c.setTime(dateFormatter.parse(this.start_day_time));
                int frequency = 1;
                return calculateReminders(start_c, end_c, Calendar.DATE, frequency);
            }else if(occurrence.equalsIgnoreCase("every 3 days")){
                end_c.setTime(dateFormatter.parse(this.end_day_time));
                start_c.setTime(dateFormatter.parse(this.start_day_time));
                int frequency = 3;
                return calculateReminders(start_c, end_c, Calendar.DATE, frequency);
            }else{
                System.out.println("no matching occurrence");
                return null;
            }
        }catch(ParseException e){
            System.out.println("Unable to parse date: " + e.getMessage().toString());
            return null;
        }
    }

    private ArrayList<Reminder> calculateReminders(Calendar start_c, Calendar end_c, int type, int frequency){
        ArrayList<Reminder> rmds = new ArrayList<>();
        while(end_c.compareTo(start_c) <= 0){ // if it's less or equal to start day
            String month = Integer.toString(end_c.get(Calendar.MONTH));
            month = month.length() > 1 ? month : "0"+month;
            String date = Integer.toString(end_c.get(Calendar.DATE));
            date = date.length() > 1 ? date : "0"+date;
            String hour = Integer.toString(end_c.get(Calendar.HOUR_OF_DAY));
            hour = hour.length() > 1 ? hour : "0"+hour;
            String minute = Integer.toString(end_c.get(Calendar.MINUTE));
            minute = minute.length() > 1 ? minute : "0"+minute;
            String second = Integer.toString(end_c.get(Calendar.SECOND));
            second = second.length() > 1 ? second : "0"+second;
            String happening_day_time = end_c.get(Calendar.YEAR) + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
            Reminder rmd = new Reminder(this.title, this.description, this.start_day_time, this.end_day_time, this.occurrence, happening_day_time);
            rmds.add(rmd);
            end_c.add(type, frequency);
        }
        return rmds;
    }


}
