package com.example.cloudgazer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);



    }

    public void signUp (){
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }

}

