package com.example.cloudgazer;

import static com.example.cloudgazer.Welcome.DEFAULT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

public class ReflectionActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener {
    private SeekBar thisSeekBar;
    private RadioGroup communitySelect, weatherSelect;
    private int rateDayValue;
    EditText inputTitle, inputDate, inputTime, inputLocation, inputRateDay, inputDayDes;
    MyDatabase db;
    String inputCommunity, inputWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection_layout);

        thisSeekBar = (SeekBar)findViewById(R.id.seekBar);
        thisSeekBar.setOnSeekBarChangeListener(examSeekBarListener);

        communitySelect = (RadioGroup)findViewById(R.id.communitySelect);
        communitySelect.setOnCheckedChangeListener(this);

        weatherSelect = (RadioGroup)findViewById(R.id.weatherSelect);
        weatherSelect.setOnCheckedChangeListener(this);

        inputTitle = (EditText) findViewById(R.id.inputTitle);
        inputDate = (EditText) findViewById(R.id.inputDate);
        inputTime = (EditText)findViewById(R.id.inputTime);
        inputLocation = (EditText)findViewById(R.id.inputLocation);
        inputRateDay = (EditText)findViewById(R.id.rateDay);
        inputDayDes = (EditText) findViewById(R.id.inputDayDes);

        db = new MyDatabase(this);

    }

    public void submit (View view)
    {

        String title = inputTitle.getText().toString();
        String date = inputDate.getText().toString();
        String time = inputTime.getText().toString();
        String location = inputLocation.getText().toString();
        String rateDay = inputRateDay.getText().toString();
        String weather = inputWeather;
        String dayDes = inputDayDes.getText().toString();
        String communitySelect = inputCommunity;

        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String user = sharedPrefs.getString("username", DEFAULT);

        String allData = title + " " + date + " " + time + " " + location + " " + rateDay + " " + weather + " " + dayDes + " " + communitySelect + " " + user;

        //check if any fields do not have any data inputted
        if (title == null || title.matches("") ||date == null || date.matches("") || time == null || date.matches("")|| location == null || location.matches("") || weather == null || dayDes == null || dayDes.matches("")|| communitySelect == null) {
            Log.i("ENTRIES", allData);
            Toast.makeText(this, "Error: not all fields have been inputted. Please try again.", Toast.LENGTH_LONG).show();
        }

        else {
            Toast.makeText(this, allData, Toast.LENGTH_SHORT).show();
            long id = db.insertData(title, date, time, location, rateDay, weather, dayDes, communitySelect, user);
            if (id < 0) {
                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
       }
    }

    public void home(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private SeekBar.OnSeekBarChangeListener examSeekBarListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            rateDayValue = thisSeekBar.getProgress();
            inputRateDay.setText(String.valueOf(rateDayValue));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar.getId() == thisSeekBar.getId()) {
            rateDayValue = seekBar.getProgress();
            inputRateDay.setText(rateDayValue);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        //set weather according to selected RadioButton
        switch (checkedId) {
            case R.id.sunnyRadioButton:
                inputWeather = "Sunny";
                break;
            case R.id.partlyCloudyRadioButton:
                inputWeather = "Partly Cloudy";
                break;
            case R.id.cloudyRadioButton:
                inputWeather = "Cloudy";
                break;
            case R.id.rainyRadioButton:
                inputWeather = "Rainy";
                break;
            case R.id.snowyRadioButton:
                inputWeather = "Snowy";
                break;
            case R.id.otherRadioButton:
                inputWeather = "Other";
                break;
        }

        switch (checkedId) {
            case R.id.yesRadioButton:
                inputCommunity = "Yes";
                break;
            case R.id.noRadioButton:
                inputCommunity = "No";
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View view) {

    }
}
