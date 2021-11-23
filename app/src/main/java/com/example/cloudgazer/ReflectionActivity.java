package com.example.cloudgazer;

import static com.example.cloudgazer.Welcome.DEFAULT;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

public class ReflectionActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener{
    private SeekBar thisSeekBar;
    private RadioGroup communitySelect, weatherSelect;
    private int rateDayValue;
    EditText inputTitle, inputDate, inputTime, inputRateDay, inputDayDes;
    TextView inputLocation;
    Button locationButton;
    MyDatabase db;
    String inputCommunity, inputWeather;
    private FusedLocationProviderClient fusedLocationClient;
    GoogleMap myMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection_layout);

        thisSeekBar = (SeekBar) findViewById(R.id.seekBar);
        thisSeekBar.setOnSeekBarChangeListener(examSeekBarListener);

        communitySelect = (RadioGroup) findViewById(R.id.communitySelect);
        communitySelect.setOnCheckedChangeListener(this);

        weatherSelect = (RadioGroup) findViewById(R.id.weatherSelect);
        weatherSelect.setOnCheckedChangeListener(this);

        inputTitle = (EditText) findViewById(R.id.inputTitle);
        inputDate = (EditText) findViewById(R.id.inputDate);
        inputTime = (EditText) findViewById(R.id.inputTime);
        inputRateDay = (EditText) findViewById(R.id.rateDay);
        inputDayDes = (EditText) findViewById(R.id.inputDayDes);

        inputLocation = (TextView) findViewById(R.id.inputLocation);

        locationButton = (Button) findViewById(R.id.getLocation);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        db = new MyDatabase(this);

        checkLocationPermission();

    }


    public void submit(View view) {

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
        if (title == null || title.matches("") || date == null || date.matches("") || time == null || date.matches("") || location == null || location.matches("") || weather == null || dayDes == null || dayDes.matches("") || communitySelect == null) {
            Log.i("ENTRIES", allData);
            Toast.makeText(this, "Error: not all fields have been inputted. Please try again.", Toast.LENGTH_LONG).show();
        } else {
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

    public void home(View view) {
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
        if (seekBar.getId() == thisSeekBar.getId()) {
            rateDayValue = seekBar.getProgress();
            inputRateDay.setText(rateDayValue);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        //set weather according to selected RadioButton
        switch (checkedId) {
            case R.id.cumulus:
                inputWeather = "Cumulus";
                break;
            case R.id.stratus:
                inputWeather = "Stratus";
                break;
            case R.id.cumulonimbus:
                inputWeather = "Cumulonimbus";
                break;
            case R.id.cirrus:
                inputWeather = "Cirrus";
                break;
            case R.id.altocumulus:
                inputWeather = "Altocumulus";
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

    public void cloudLaunch(View v) {
        Uri webpage = Uri.parse("https://www.weather.gov/jetstream/basicten");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        try {
            startActivity(webIntent);
        } catch (ActivityNotFoundException e) {
            
        }
    }

    public void showCurrentLocation(View v) {
        Log.i("TEST", "button is pressed");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {

                        @Override
                        public void onSuccess(Location location){
                            Log.i("TEST", "onSuccess is called");
                            Log.i("TEST", "location is not null");
                            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
                            String pos = String.valueOf(latlng);
                            Log.i("tag", "currentlocation:" + pos);
                            inputLocation.setText(pos);
                            if (location != null) {

                            }
                        }
                });
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

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                //try again to request the permission
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(ReflectionActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                //we can request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                //if request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                    }

                } else {
                    // permission was denied

                }
                return;
            }

        }
    }
}

