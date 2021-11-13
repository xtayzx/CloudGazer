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

    public long insertData (String date, String time, String location, String rateDay, String cloudDes, String dayDes, String community)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.DATE, date);
        contentValues.put(Constants.TIME, time);
        contentValues.put(Constants.LOCATION, location);
        contentValues.put(Constants.RATE_DAY, rateDay);
        contentValues.put(Constants.CLOUD_DES, cloudDes);
        contentValues.put(Constants.DAY_DES, dayDes);
        contentValues.put(Constants.COMMUNITY, community);
        long id = db.insert(Constants.TABLE_NAME, null, contentValues);
        return id;
    }

    public Cursor getData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.UID, Constants.DATE, Constants.TIME, Constants.LOCATION, Constants.RATE_DAY, Constants.CLOUD_DES, Constants.DAY_DES, Constants.COMMUNITY};
        Cursor cursor = db.query(Constants.TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }

//    public String getSensorData(String name)
//    {
//        SQLiteDatabase db = helper.getWritableDatabase();
//        String[] columns = {Constants.UID, Constants.DATE, Constants.TIME, Constants.LOCATION, Constants.RATE_DAY, Constants.CLOUD_DES, Constants.DAY_DES};
//
//        //checking if the name selected match the name of the sensor in the database
//        String selection = Constants.NAME + "='" +name+ "'";
//        Cursor cursor = db.query(Constants.TABLE_NAME, columns, selection, null, null, null, null);
//
//        StringBuffer buffer = new StringBuffer();
//        while (cursor.moveToNext()) {
//            int index1 = cursor.getColumnIndex(Constants.DATE);
//            int index2 = cursor.getColumnIndex(Constants.TIME);
//            int index3 = cursor.getColumnIndex(Constants.LOCATION);
//            int index4 = cursor.getColumnIndex(Constants.RATE_DAY);
//            int index5 = cursor.getColumnIndex(Constants.CLOUD_DES);
//            int index6 = cursor.getColumnIndex(Constants.DAY_DES);
//            String date = cursor.getString(index1);
//            String time = cursor.getString(index2);
//            String location = cursor.getString(index3);
//            String rateDay = cursor.getString(index4);
//            String cloudDes = cursor.getString(index5);
//            String dayDes = cursor.getString(index6);
//            buffer.append(date + "\n" + time + "\n" + location + "\n" + rateDay + "\n" + cloudDes + "\n" + dayDes);
//        }
//        return buffer.toString();
//    }
}
