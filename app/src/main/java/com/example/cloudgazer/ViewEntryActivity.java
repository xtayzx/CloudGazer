package com.example.cloudgazer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import utils.CameraUtils;

public class ViewEntryActivity extends Activity implements OnMapReadyCallback {

    Button deleteButton;
    Boolean delete;
    GoogleMap myMap;
    Double lat, lng;
    MyDatabase db;
    MyHelper helper;
    TextView titleField, dateField, timeField, locationField, rateDayField, weatherField, dayDesField, communityField;
    ImageView image;
    String photoPath, latValue, lngValue;

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
        image = (ImageView)findViewById(R.id.entryImageView);

        deleteButton = (Button)findViewById(R.id.deleteButton);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.showmap);
        mapFragment.getMapAsync(this);

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
        int index10 = cursor.getColumnIndex(Constants.PHOTO);
        int index11 = cursor.getColumnIndex(Constants.LAT);
        int index12 = cursor.getColumnIndex(Constants.LNG);

        int title = cursor.getColumnIndex(Constants.TITLE);

        Intent intent = getIntent();
        String intentQuery = intent.getStringExtra("title");
        Log.i("TEST", "This is the value of what's passed: "+intentQuery);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            String intentQueryMatch = cursor.getString(title);

            //is the title that was pressed the same as in the database
            if (intentQuery.equals(intentQueryMatch)) {
                String titleEntry = cursor.getString(index1);
                String dateEntry = cursor.getString(index2);
                String timeEntry = cursor.getString(index3);
                String locationEntry = cursor.getString(index4);
                String rateDayEntry = cursor.getString(index5);
                String weatherEntry = cursor.getString(index6);
                String dayDesEntry = cursor.getString(index7);
                String communityEntry = cursor.getString(index8);
                String savedPhotoPath = cursor.getString(index10);
                String latEntry = cursor.getString(index11);
                String lngEntry = cursor.getString(index12);

                //transfer all fields in the database
                titleField.setText(titleEntry);
                dateField.setText(dateEntry);
                timeField.setText(timeEntry);
                locationField.setText(locationEntry);
                rateDayField.setText(rateDayEntry);
                weatherField.setText(weatherEntry);
                dayDesField.setText(dayDesEntry);
                communityField.setText(communityEntry);
                photoPath = savedPhotoPath;
                latValue = latEntry;
                lngValue = lngEntry;

                cursor.moveToNext();
            }

            else if (intentQuery != intentQueryMatch) {
                cursor.moveToNext();
            }

        }

        lat = Double.parseDouble(latValue);
        lng = Double.parseDouble(lngValue);

//        getSupportActionBar().setTitle("Cloud Gazer");

        //display photo
        try {
            image.setVisibility(View.VISIBLE);
//            Log.d("new", path);
            final Bitmap bitmap = CameraUtils.scaleDownAndRotatePic(photoPath);
            image.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        //if the user would like to delete the entry
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewEntryActivity.this);

                //title for the alert dialog
                builder.setTitle("Delete Entry");

                //ask the final question
                builder.setMessage("Are you sure to delete the entry: " + titleField.getText() +"?");

                //set a yes button click listener
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),
                                "This entry has been deleted",Toast.LENGTH_SHORT).show();
                        Log.i("VALUE", String.valueOf(titleField.getText()));
                        db.deleteRow(String.valueOf(titleField.getText()));
                        deleteButton.setVisibility(View.INVISIBLE);

                    }
                });

                //set the no button click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),
                                "No Button Clicked",Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                //display the alert dialog on interface
                dialog.show();
            }
        });
    }

    public void homeButton(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    //set up map in the saved postiion of that entry
    @Override
    public void onMapReady(GoogleMap map) {
        myMap = map;

        checkLocationPermission();

        gotoLocation(lat, lng, 15);

        myMap.setMyLocationEnabled(true);
        myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    // go to location and set up a marker in that location
    private void gotoLocation(double lat, double lng, float zoom) {
        LatLng latlng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlng, zoom);
        myMap.moveCamera(update);
        MarkerOptions mark = new MarkerOptions().position(new LatLng(lat, lng));
        myMap.addMarker(mark);
    }

    // permission for location
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(ViewEntryActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                    }

                } else {

                }
                return;
            }

        }
    }
}
