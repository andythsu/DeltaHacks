package com.deltahacks4.deltamind.deltamind;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    private ArrayList<SubReminder> sub_rmds;
//    private String happening_day_time;
    private String picture_names;

    public Reminder(String title, String description, String start_day_time, String end_day_time, String occurrence, String picture_names){
        this.title = title;
        this.description = description;
        this.start_day_time = start_day_time;
        this.end_day_time = end_day_time;
        this.occurrence = occurrence;
        this.picture_names = picture_names;
    }

//    public Reminder(String title, String description, String start_day_time, String end_day_time, String occurrence, ArrayList<String> picture_names, String happening_day_time){
//        this.title = title;
//        this.description = description;
//        this.start_day_time = start_day_time;
//        this.end_day_time = end_day_time;
//        this.occurrence = occurrence;
//        this.picture_names = picture_names;
//        // happening_day_time is to distinguish between "big reminder" and "small reminder"
////        this.happening_day_time = happening_day_time;
//    }

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

//    public String getHappening_day_time(){
//        return this.happening_day_time;
//    }

    public ArrayList<SubReminder> getAllSubreminders(){
        System.out.println(this.end_day_time);
        System.out.println(this.start_day_time);
        System.out.println("objects: ");
        for(int i=0; i<getAllSubreminders(this.occurrence).size(); i++){
            System.out.println("obj: " + getAllSubreminders(this.occurrence).get(i).toString());
        }
        return getAllSubreminders(this.occurrence);
    }


    private ArrayList<SubReminder> getAllSubreminders(String occurrence){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar end_c = Calendar.getInstance();
        Calendar start_c = Calendar.getInstance();
        try{
            if(occurrence.equalsIgnoreCase("Every minute")){
                end_c.setTime(dateFormatter.parse(this.end_day_time));
                start_c.setTime(dateFormatter.parse(this.start_day_time));
                int frequency = 1;
                return calculateSubreminders(start_c, end_c, Calendar.MINUTE, frequency, this.title);
            }else if(occurrence.equalsIgnoreCase("Every day")){
                end_c.setTime(dateFormatter.parse(this.end_day_time));
                start_c.setTime(dateFormatter.parse(this.start_day_time));
                int frequency = 1;
                return calculateSubreminders(start_c, end_c, Calendar.DATE, frequency, this.title);
            }else if(occurrence.equalsIgnoreCase("Every 3 days")){
                end_c.setTime(dateFormatter.parse(this.end_day_time));
                start_c.setTime(dateFormatter.parse(this.start_day_time));
                int frequency = 3;
                return calculateSubreminders(start_c, end_c, Calendar.DATE, frequency, this.title);
            }else{
                System.out.println("no matching occurrence");
                return null;
            }
        }catch(ParseException e){
            System.out.println("Unable to parse date: " + e.getMessage().toString());
            return null;
        }
    }

    private ArrayList<SubReminder> calculateSubreminders(Calendar start_c, Calendar end_c, int type, int frequency, String title){
        ArrayList<SubReminder> rmds = new ArrayList<>();
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
            SubReminder rmd = new SubReminder(happening_day_time, title);
            rmds.add(rmd);
            end_c.add(type, frequency);
        }
        return rmds;
    }


}
