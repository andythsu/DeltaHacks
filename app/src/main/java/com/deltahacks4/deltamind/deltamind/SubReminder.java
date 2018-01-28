package com.deltahacks4.deltamind.deltamind;

/**
 * Created by Andy Su on 2018/1/28.
 */

public class SubReminder {
    private String happening_day_time;
    private int unique_id;

    public SubReminder(String happening_day_time){
        this.happening_day_time = happening_day_time;
    }

    public String getHappening_day_time(){
        return this.happening_day_time;
    }


    // sets the unique id returned from DB
    public void setUnique_id(int id){
        this.unique_id = id;
    }
}
