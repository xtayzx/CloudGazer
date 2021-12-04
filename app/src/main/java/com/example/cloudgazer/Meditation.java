package com.example.cloudgazer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;

public class Meditation extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "TAG";

    TextView displaymsg;
    Button reflect;

    private SensorManager mSensor;
    Sensor mLight;
    Sensor mAccel;

    int counter = 0;

    double x,y,z,lastPos,currentPos;

    private MediaPlayer beep;
    private boolean still = false;
    private boolean facedown = false;
    private boolean meditate = false;
    private boolean played = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        displaymsg = (TextView) findViewById(R.id.display);
        reflect = (Button) findViewById(R.id.reflect);

        beep = new MediaPlayer();

        mSensor = (SensorManager)  getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensor.getSensorList((Sensor.TYPE_ALL));

        mLight = mSensor.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(mLight != null){
            Toast.makeText(this,"Light Available",Toast.LENGTH_SHORT).show();
            mSensor.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);

        } else {
            Toast.makeText(this,"Lights Out",Toast.LENGTH_SHORT).show();
        }

        mAccel = mSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(mAccel != null){
            Toast.makeText(this,"Accel Available",Toast.LENGTH_SHORT).show();
            mSensor.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            Toast.makeText(this,"No Accel",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            lastPos = currentPos;
            currentPos = Math.sqrt(x * x + y * y + z * z);

            double change = currentPos - lastPos;
            Log.i(TAG, "Change = " + change);

// This would mean the phone is not moving and if the light sensor is low than it is flipped over.
            if (change <= 2 ){ // to intense
                still = true;
            }

        }
        Log.i(TAG, "still = " + still);

        if (event.sensor.getType() == Sensor.TYPE_LIGHT && still==true) {
            Log.i(TAG, "light found");

            float lux = event.values[0];
            Log.i(TAG, "meditate: " + meditate);

            if (lux <= 5 && meditate == false) {
                Log.i(TAG, "meditate: " + meditate);
                facedown = true;

                if (facedown == true) {
                    Log.i(TAG, "light value is 0");
                    counter++;
                    Log.i(TAG, "counter = " + counter);
                    String time = String.valueOf(counter);
                    displaymsg.setText("counter : " + time);
                    if(played == false){
                        beep = MediaPlayer.create(getApplicationContext(), R.raw.beep_00);
                        beep.start();
                        played = true;
                    }

                }

                if (counter == 60) {
                    played = false;
                    if (played == false){
                        beep = MediaPlayer.create(getApplicationContext(), R.raw.beep_00);
                        beep.start();
                        played = true;
                    }

                    displaymsg.setText("all done");
                    facedown = false;
                    meditate = true;
                    counter = 0;
                }
            }
            if (meditate == true){
                reflect.setVisibility(View.VISIBLE);
            }
            else {
                //displaymsg.setText("look at the sky - not your phone");
            }
        }

    }

    public void goToReflect (View v) {
        Intent i = new Intent(this, ReflectionActivity.class);
        startActivity(i);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
