package com.example.cloudgazer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void signedUp(View v){
        Intent i  = new Intent(this, Welcome.class);
        startActivity(i);
    }

    public void reflectionActivity(View v){
        Intent i  = new Intent(this, ReflectionActivity.class);
        startActivity(i);
    }

}