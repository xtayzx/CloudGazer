package com.example.cloudgazer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

    }

    public void logIn(View v){
        //**** THIS INTENT WILL CHANGE TO FOLLOW THE PROPER SITE MAP ****
        Intent i = new Intent(this, Meditation.class);
        startActivity(i);
    }

    public void accountCreate (View v){
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }

}

