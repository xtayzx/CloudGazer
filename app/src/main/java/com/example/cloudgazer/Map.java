package com.example.cloudgazer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

public class Map extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    GoogleMap myMap;
    private EditText locationEntry;
    private String locationString;

    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //create map display
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationEntry = (EditText) findViewById(R.id.locationEditText);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        //set up map to open at specified location and allow for any changes/inputs
        myMap = map;
        checkLocationPermission();
        myMap.setMyLocationEnabled(true);
        myMap.setOnMyLocationButtonClickListener(this);
        myMap.setOnMyLocationClickListener(this);
        myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void saveLoc(View v){
        //Save location boolean to ensure that there is a inputted value
        if(locationEntry.getText().toString() == null || locationEntry.getText().toString() == " ") {
            Toast.makeText(this, "No location entered. Please try again.", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    @Override
    public void finish() {
        //once the map activity i s done, save the info and go back to the reflection activity
        Intent data = getIntent();
        data.putExtra("location", locationString);
        setResult(RESULT_OK, data);
        super.finish();
    }


    public void geolocate(View v) {
        Geocoder myGeocoder = new Geocoder(this);
        hideSoftKeyboard(v);

        // finding location using a search input
        if (v.getId() == R.id.locationButton) {
            locationString = locationEntry.getText().toString();
            Toast.makeText(this, "Searching for " + locationString, Toast.LENGTH_SHORT).show();


            List<Address> list = null;
            try {
                list = myGeocoder.getFromLocationName(locationString, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (list.size() > 0) {
                Address add = list.get(0);
                String locality = add.getLocality();
                Toast.makeText(this, "Found " + locality, Toast.LENGTH_SHORT).show();

                double lat = add.getLatitude();
                double lng = add.getLongitude();
                gotoLocation(lat, lng, 15);

                // save the sharedprefs of lat anf lng values
                SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("locentry", locationString);
                editor.putString("lat", String.valueOf(lat));
                editor.putString("lng", String.valueOf(lng));
                Log.d("lat val", String.valueOf(lat));
                Log.d("lng val", String.valueOf(lng));
                editor.commit();

                //set up the marker to be in the selected location
                    MarkerOptions mark = new MarkerOptions().title(locality).position(new LatLng(lat, lng));
                    myMap.addMarker(mark);
                }
            }
        }


    private void hideSoftKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    //go to the location specified
    private void gotoLocation(double lat, double lng, float zoom) {
        LatLng latlng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlng, zoom);
        myMap.moveCamera(update);
    }


    @Override
    public void onMyLocationClick(@NonNull Location location) {
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }


// Permission request
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(Map.this,
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
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
