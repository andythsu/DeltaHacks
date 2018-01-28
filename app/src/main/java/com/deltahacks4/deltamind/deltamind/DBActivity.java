package com.deltahacks4.deltamind.deltamind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andy Su on 2018/1/27.
 */

public class DBActivity extends SQLiteOpenHelper{

    private static final String DB_NAME = "DeltaMind_DB";
    // REMINDER TABLE SETTING
    private static final String RMD_TABLE = "Reminder_Table";
    private static final String RMD_PRIMARY_ID = "ID";
    private static final String RMD_TITLE = "reminder_title";
    private static final String RMD_DESCRIPTION = "reminder_description";
    private static final String RMD_DAY_TIME = "reminder_day_time";
    // PICTURE TABLE SETTING
    private static final String PIC_TABLE = "Picture_Table";
    private static final String PIC_PRIMARY_ID = "ID";
    private static final String PIC_RMD_ID = "picture_reminder_id";
    private static final String PIC_NAME = "picture_name";


    public DBActivity(Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // creating reminder table
        try {
            String sql = "CREATE TABLE " + RMD_TABLE + "(" + RMD_PRIMARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                          RMD_TITLE + " TEXT, " + RMD_DESCRIPTION + " TEXT, " + RMD_DAY_TIME + " TEXT)";
           sqLiteDatabase.execSQL(sql);
           System.out.println("successfully created table " + RMD_TABLE);
        }catch(SQLException e){
            System.out.println("error in creating table: " + RMD_TABLE + e.getMessage().toString());
        }
        // creating picture table
        try {
            String sql = "CREATE TABLE " + PIC_TABLE + "(" + PIC_PRIMARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PIC_RMD_ID +
                         " INTEGER, " + PIC_NAME + " TEXT)";
            sqLiteDatabase.execSQL(sql);
            System.out.println("successfully created table: " + PIC_TABLE);
        }catch(SQLException e){
            System.out.println("error in creating table: " + PIC_TABLE + e.getMessage().toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try{
            String rmd_table_sql = "DROP TABLE IF EXISTS " + RMD_TABLE;
            String pic_table_sql = "DROP TABLE IF EXISTS " + PIC_TABLE;
            sqLiteDatabase.execSQL(rmd_table_sql);
            sqLiteDatabase.execSQL(pic_table_sql);
            System.out.println("Table dropped successfully");
            onCreate(sqLiteDatabase);
        }catch(SQLException e){
            System.out.print("error while dropping table " + e.getMessage().toString());
        }
    }

    // helper method
    public boolean insertRmd(Reminder rmd, SubReminder sub_rmd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(RMD_TITLE, rmd.getTitle());
        val.put(RMD_DESCRIPTION, rmd.getDescription());
        val.put(RMD_DAY_TIME, sub_rmd.getHappening_day_time());
        long result = db.insert(RMD_TABLE, null, val);

        return result == -1 ? false : true;

    }

    public boolean insertPicture(String rmd_id, String picture_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(PIC_RMD_ID, rmd_id);
        val.put(PIC_NAME, picture_name);
        long result = db.insert(PIC_TABLE, null, val);

        return result == -1 ? false : true;

    }

    public Cursor fetchRmds(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + RMD_TABLE + " ORDER BY " + RMD_DAY_TIME + " DESC";
        Cursor res = db.rawQuery(sql, null);
        return  res;
    }

}
