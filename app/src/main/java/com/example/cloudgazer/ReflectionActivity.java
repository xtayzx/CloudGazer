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
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import utils.CameraUtils;

public class ReflectionActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener{
    private SeekBar thisSeekBar;
    private RadioGroup communitySelect, weatherSelect;
    private int rateDayValue;
    EditText inputTitle, inputDate, inputTime, inputRateDay, inputDayDes;
    TextView inputLocation;
    Button locationButton;
    ImageView image;
    MyDatabase db;
    String inputCommunity, inputWeather, inputPhoto;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int CAMERA_CAPTURE_IMAGE = 1;
    private static final int RETURN_MAP = 2;


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
        image = (ImageView) findViewById(R.id.previewImage);
        inputWeather = "'example'";

        inputLocation = (TextView) findViewById(R.id.inputLocation);

        locationButton = (Button) findViewById(R.id.getLocation);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        db = new MyDatabase(this);

        checkLocationPermission();

        getSupportActionBar().setTitle("Cloud Gazer - Reflection");
    }


    public void submit(View view) {
        String title = inputTitle.getText().toString();
        String date = inputDate.getText().toString();
        String time = inputTime.getText().toString();
        String location = inputLocation.getText().toString();
        String rateDay = inputRateDay.getText().toString();
        String weather = inputWeather;
        String dayDes = inputDayDes.getText().toString();
        String photo = inputPhoto;
        String communitySelect = inputCommunity;

        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String user = sharedPrefs.getString("username", DEFAULT);
        String lat = sharedPrefs.getString("lat", DEFAULT);
        String lng = sharedPrefs.getString("lng", DEFAULT);

        Log.i("TEST", "Values lat: "+lat+" and values lng: "+lng);

        String allData = title + " " + date + " " + time + " " + location + " " + rateDay + " " + weather + " " + dayDes + " " + communitySelect + " " + user + " " + photo + " " + lat + " " + lng;

        //check if any fields do not have any data inputted
        if (title == null || title.matches("") || date == null || date.matches("") || time == null || date.matches("") || location == null || location.matches("") || weather == null || dayDes == null || dayDes.matches("") || communitySelect == null || photo == null || photo.matches("") || lat == null || lng == null) {
            Log.i("ENTRIES", allData);
            Toast.makeText(this, "Error: not all fields have been inputted. Please try again.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, allData, Toast.LENGTH_SHORT).show();
            long id = db.insertData(title, date, time, location, rateDay, weather, dayDes, communitySelect, user, photo, lat, lng);
            if (id < 0) {
                Toast.makeText(this, "Could not create entry.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Entry success!", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
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
            case R.id.cirrocumulus:
                inputWeather = "Cirrocumulus";
                break;
            case R.id.cirrostratus:
                inputWeather = "Cirrostratus";
                break;
            case R.id.altostratus:
                inputWeather = "Altostratus";
                break;
            case R.id.nimbostratus:
                inputWeather = "Nimbostratus";
                break;
            case R.id.stratocumulus:
                inputWeather = "Stratocumulus";
                break;
            case R.id.other:
                inputWeather = "Other";
                break;
        }

        //set the community value according to the radio button
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

    public void launchLocation(View v) {
        Intent i = new Intent(this, Map.class);
        i.putExtra("location", "Location Retrieved");
        startActivityForResult(i, RETURN_MAP);
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
        //make sure location permissions are on, otherwise the app will crash once the map activity is launched
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_CAPTURE_IMAGE:
                    //once returning back to the activity, set the image in the layout
                    Toast.makeText(this, "The image has been saved!", Toast.LENGTH_SHORT).show();
                    previewCapturedImage();
                    break;
                case RETURN_MAP:
                    //once returning back to the activity, set the location in the layout
                    Toast.makeText(this, "Back to Reflection", Toast.LENGTH_SHORT).show();
                    if(data.hasExtra("location")) {
                        inputLocation.setText(data.getExtras().getString("location"));
                    }
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            // user cancelled Image capture
            Toast.makeText(this, "User has cancelled the capture", Toast.LENGTH_SHORT).show();
        } else {
            // failed to capture image
            Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
        }
    }

    public void launchCamera(View view) {
        dispatchTakePictureIntent(CAMERA_CAPTURE_IMAGE);
    }

    //display image from a path to imageView
    private void previewCapturedImage() {
        try {
            image.setVisibility(View.VISIBLE);
            final Bitmap bitmap = CameraUtils.scaleDownAndRotatePic(inputPhoto);
            image.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void dispatchTakePictureIntent(int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                Log.i("TEST", "created the file");
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d("ex", "cannot create file");

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, requestCode);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        inputPhoto = image.getAbsolutePath();
        return image;
    }
}

