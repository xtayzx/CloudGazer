package com.example.cloudgazer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;

public class Meditation extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "TAG";

    TextView displaymsg;

    Timer timer;

    private SensorManager mSensor;
    Sensor mLight;
    Sensor mAccel;

    int counter = 0;

    double x,y,z,lastPos,currentPos;

    private MediaPlayer beep;
    private boolean still = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        displaymsg = (TextView) findViewById(R.id.display);

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
            if (change == 0 ){
                still = true;
            }

        }
        Log.i(TAG, "still = " + still);

        if (event.sensor.getType() == Sensor.TYPE_LIGHT && still==true) {
            Log.i(TAG, "light found");
            float lux = event.values[0];
            if (lux <= 5) {
                Log.i(TAG, "light value is 0");
                displaymsg.setText("meditating mode ");
                counter++;
                Log.i(TAG, "counter = " + counter);

                //beep = MediaPlayer.create(getApplicationContext(),R.raw.beep_00);
                // beep.start();


                if (counter == 60) {
                    beep = MediaPlayer.create(getApplicationContext(),R.raw.beep_01);
                    displaymsg.setText("all done");
                    counter =  0;
                }
            }
            else {
                displaymsg.setText("look at the sky - not your phone");
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
