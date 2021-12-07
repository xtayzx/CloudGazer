package com.example.cloudgazer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDatabase {
    SQLiteDatabase db;
    Context context;

    private final MyHelper helper;

    public MyDatabase (Context c){
        context = c;
        helper = new MyHelper(context);
    }

    public long insertData (String title, String date, String time, String location, String rateDay, String weather, String dayDes, String community, String user, String photo, String lat, String lng)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TITLE, title);
        contentValues.put(Constants.DATE, date);
        contentValues.put(Constants.TIME, time);
        contentValues.put(Constants.LOCATION, location);
        contentValues.put(Constants.RATE_DAY, rateDay);
        contentValues.put(Constants.WEATHER, weather);
        contentValues.put(Constants.DAY_DES, dayDes);
        contentValues.put(Constants.COMMUNITY, community);
        contentValues.put(Constants.USER, user);
        contentValues.put(Constants.PHOTO, photo);
        contentValues.put(Constants.LAT, lat);
        contentValues.put(Constants.LNG, lng);
        long id = db.insert(Constants.TABLE_NAME, null, contentValues);
        return id;
    }

    public Cursor getData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.UID, Constants.TITLE, Constants.DATE, Constants.TIME, Constants.LOCATION, Constants.RATE_DAY, Constants.WEATHER, Constants.DAY_DES, Constants.COMMUNITY, Constants.USER, Constants.PHOTO, Constants.LAT, Constants.LNG};
        Cursor cursor = db.query(Constants.TABLE_NAME, columns, null, null, null, null, Constants.DATE + " DESC");
        return cursor;
    }

    public void deleteRow(String title)
    {
        db = helper.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.TITLE+"=?", new String[]{title});
    }

}
