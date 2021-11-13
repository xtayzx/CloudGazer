package com.example.cloudgazer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewEntryActivity extends Activity implements View.OnClickListener{
    TextView inputAll;
    Button backButton;

    MyDatabase db;
    MyHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);

//        inputAll = (TextView)findViewById(R.id.input_all);

        backButton = (Button)findViewById(R.id.back_button);
        backButton.setOnClickListener(this);

        db = new MyDatabase(this);
        helper = new MyHelper(this);

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
    }

    @Override
    public void onClick(View view) {
        Intent i = getIntent();
        setResult(RESULT_OK, i);
        super.finish();
    }
}
