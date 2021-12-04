package com.example.cloudgazer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import utils.CameraUtils;

public class CommunityViewEntryActivity extends Activity {

    MyDatabase db;
    MyHelper helper;
    TextView titleField, dateField, timeField, locationField, rateDayField, weatherField, dayDesField, communityField, userField;
    ImageView image;
    String photoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry_community);

        titleField = (TextView)findViewById(R.id.titleTextView);
        dateField = (TextView)findViewById(R.id.dateEntry);
        timeField = (TextView)findViewById(R.id.timeEntry);
        locationField = (TextView)findViewById(R.id.locationEntry);
        rateDayField = (TextView)findViewById(R.id.rateDayEntry);
        weatherField = (TextView)findViewById(R.id.weatherEntry);
        dayDesField = (TextView)findViewById(R.id.dayDesEntry);
        communityField = (TextView)findViewById(R.id.communityEntry);
        userField = (TextView)findViewById(R.id.userEntry);
        image = (ImageView)findViewById(R.id.entryImageViewC);


        db = new MyDatabase(this);
        helper = new MyHelper(this);

        Cursor cursor = db.getData();

        //get the column index for both the columns
        int index1 = cursor.getColumnIndex(Constants.TITLE);
        int index2 = cursor.getColumnIndex(Constants.DATE);
        int index3 = cursor.getColumnIndex(Constants.TIME);
        int index4 = cursor.getColumnIndex(Constants.LOCATION);
        int index5 = cursor.getColumnIndex(Constants.RATE_DAY);
        int index6 = cursor.getColumnIndex(Constants.WEATHER);
        int index7 = cursor.getColumnIndex(Constants.DAY_DES);
        int index8 = cursor.getColumnIndex(Constants.COMMUNITY);
        int index9 = cursor.getColumnIndex(Constants.USER);
        int index10 = cursor.getColumnIndex(Constants.PHOTO);

        int title = cursor.getColumnIndex(Constants.TITLE);

        Intent intent = getIntent();
        String intentQuery = intent.getStringExtra("title");
        Log.i("TEST", "This is the value of what's passed: "+intentQuery);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            //if the name of the title pressed matches the title listed in the database
            String intentQueryMatch = cursor.getString(title);

            if (intentQuery.equals(intentQueryMatch)) {
                String titleEntry = cursor.getString(index1);
                String dateEntry = cursor.getString(index2);
                String timeEntry = cursor.getString(index3);
                String locationEntry = cursor.getString(index4);
                String rateDayEntry = cursor.getString(index5);
                String weatherEntry = cursor.getString(index6);
                String dayDesEntry = cursor.getString(index7);
                String communityEntry = cursor.getString(index8);
                String userEntry = cursor.getString(index9);
                String entryPhotoPath = cursor.getString(index10);

                titleField.setText(titleEntry);
                dateField.setText(dateEntry);
                timeField.setText(timeEntry);
                locationField.setText(locationEntry);
                rateDayField.setText(rateDayEntry);
                weatherField.setText(weatherEntry);
                dayDesField.setText(dayDesEntry);
                communityField.setText(communityEntry);
                userField.setText(userEntry);
                photoPath = entryPhotoPath;
                cursor.moveToNext();
            }

            //if not then move to the next row
            else if (intentQuery != intentQueryMatch) {
                cursor.moveToNext();
            }
        }

        //display photo
        try {
            image.setVisibility(View.VISIBLE);
//            Log.d("new", path);
            final Bitmap bitmap = CameraUtils.scaleDownAndRotatePic(photoPath);
            image.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }
}
