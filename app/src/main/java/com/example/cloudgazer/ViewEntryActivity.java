package com.example.cloudgazer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewEntryActivity extends Activity {
//    TextView inputAll;
    Button deleteButton;
    Boolean delete;

    MyDatabase db;
    MyHelper helper;
    TextView titleField, dateField, timeField, locationField, rateDayField, weatherField, dayDesField, communityField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);

        titleField = (TextView)findViewById(R.id.titleTextView);
        dateField = (TextView)findViewById(R.id.dateEntry);
        timeField = (TextView)findViewById(R.id.timeEntry);
        locationField = (TextView)findViewById(R.id.locationEntry);
        rateDayField = (TextView)findViewById(R.id.rateDayEntry);
        weatherField = (TextView)findViewById(R.id.weatherEntry);
        dayDesField = (TextView)findViewById(R.id.dayDesEntry);
        communityField = (TextView)findViewById(R.id.communityEntry);

        deleteButton = (Button)findViewById(R.id.deleteButton);

//        inputAll = (TextView)findViewById(R.id.input_all);

//        backButton = (Button)findViewById(R.id.back_button);
//        backButton.setOnClickListener(this);

        db = new MyDatabase(this);
        helper = new MyHelper(this);

//        delete = false;

        //populate all the data and put it inside the arraylist
        Cursor cursor = db.getData();
//        Cursor checkCursor = db.getData();

        //get the column index for both the columns
        int index1 = cursor.getColumnIndex(Constants.TITLE);
        int index2 = cursor.getColumnIndex(Constants.DATE);
        int index3 = cursor.getColumnIndex(Constants.TIME);
        int index4 = cursor.getColumnIndex(Constants.LOCATION);
        int index5 = cursor.getColumnIndex(Constants.RATE_DAY);
        int index6 = cursor.getColumnIndex(Constants.WEATHER);
        int index7 = cursor.getColumnIndex(Constants.DAY_DES);
        int index8 = cursor.getColumnIndex(Constants.COMMUNITY);

        int title = cursor.getColumnIndex(Constants.TITLE);

        //retrieve the arraylist from the database
        //populate all the data from the database and run the while loop

        Intent intent = getIntent();
        String intentQuery = intent.getStringExtra("title");
        Log.i("TEST", "This is the value of what's passed: "+intentQuery);

//        if(intentQuery != null) {
        //ArrayList<String> mArrayList = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            String intentQueryMatch = cursor.getString(title);
            //Log.i("TEST", "This is the value of cursor: "+plantQueryMatch);
            if (intentQuery.equals(intentQueryMatch)) {
                //Log.i("TEST", "It's a MATCH");
                String titleEntry = cursor.getString(index1);
                String dateEntry = cursor.getString(index2);
                String timeEntry = cursor.getString(index3);
                String locationEntry = cursor.getString(index4);
                String rateDayEntry = cursor.getString(index5);
                String weatherEntry = cursor.getString(index6);
                String dayDesEntry = cursor.getString(index7);
                String communityEntry = cursor.getString(index8);

                //String s = title + "," + date + "," + dayDes;
                //mArrayList.add(s);


                titleField.setText(titleEntry);
                dateField.setText(dateEntry);
                timeField.setText(timeEntry);
                locationField.setText(locationEntry);
                rateDayField.setText(rateDayEntry);
                weatherField.setText(weatherEntry);
                dayDesField.setText(dayDesEntry);
                communityField.setText(communityEntry);
                cursor.moveToNext();
            }

            else if (intentQuery != intentQueryMatch) {
                cursor.moveToNext();
            }
        }

//        Log.i("TEST", "Size of ArrayList: "+mArrayList.size());
//        myAdapter = new MyAdapter(mArrayList);
//        myRecycler.setAdapter(myAdapter);
//        }

//        else if (intentQuery == null){
//            ArrayList<String> mArrayList = new ArrayList<>();
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                String date = cursor.getString(index1);
//                String cloudDes = cursor.getString(index2);
//                String row = date + "," + cloudDes;
//                mArrayList.add(row);
//                cursor.moveToNext();
//            }
//            myAdapter = new MyAdapter(mArrayList);
//            myRecycler.setAdapter(myAdapter);
//        }

//        //get the intent data of the sensor name clicked on trigger
//        Intent intent = getIntent();
//        String sensorName = intent.getStringExtra("SensorName");
//
//        //if the sensor name clicked matches a sensor name in the database, retrieve information in the row of the database and save to SharedPrefs
//        String sensorInfo = db.getSensorData(sensorName);
//        Log.i("SENSOR DATA SAVED", sensorInfo);
//
//        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        editor.putString("allData", sensorInfo);
//        editor.commit();
//
//        //collect data from SharedPrefs and display in the view
//        String savedAllData = sharedPrefs.getString("allData", DEFAULT);
//        inputAll.setText(savedAllData);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewEntryActivity.this);

                // Set a title for alert dialog
                builder.setTitle("Delete Entry");

                // Ask the final question
                builder.setMessage("Are you sure to delete the entry: " + titleField.getText() +"?");

                // Set the alert dialog yes button click listener
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when user clicked the Yes button
                        // Set the TextView visibility GONE
//                    tv.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),
                                "This entry has been deleted",Toast.LENGTH_SHORT).show();
                        Log.i("VALUE", String.valueOf(titleField.getText()));
                        db.deleteRow(String.valueOf(titleField.getText()));
                        deleteButton.setText("Entry Deleted");
                        deleteButton.setBackgroundColor(Color.BLACK);
//                        delete = true;
                    }
                });

                // Set the alert dialog no button click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when No button clicked
                        Toast.makeText(getApplicationContext(),
                                "No Button Clicked",Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();


            }
        });


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.i("TEST", "Value of delete: "+delete);
//        if(delete == true) {
//            Intent intent = new Intent(this, HomeActivity.class);
//            startActivity(intent);
//        }
//    }

    public void homeButton(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
