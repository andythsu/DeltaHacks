package com.deltahacks4.deltamind.deltamind;

/**
 * Created by Andy Su on 2018/1/27.
 */

public class Reminder {
    private String title;
    private String description;
    private String day_time;

    public Reminder(String title, String description, String day_time){
        this.title = title;
        this.description = description;
        this.day_time = day_time;
    }

    // getters
    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public String getDay_time(){
        return this.day_time;
    }
}
