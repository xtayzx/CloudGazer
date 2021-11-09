package com.example.cloudgazer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {

    public static final String DEFAULT = "not available";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //check to see if there is anything saved under sharedPreferences
        checkData();
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

    public void checkData() {
        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        String savedUsername = sharedPrefs.getString("username", DEFAULT);
        String savedEmail = sharedPrefs.getString("email", DEFAULT);
        String savedPassword = sharedPrefs.getString("password", DEFAULT);

        Log.i("TESTING", "Saved Username: "+savedUsername+" || Saved Password: "+savedPassword+" || Saved Email: "+savedEmail);

        //add email later
        if((savedUsername.isEmpty() && savedPassword.isEmpty()) && savedEmail.isEmpty() || savedUsername.equals(DEFAULT) && savedPassword.equals(DEFAULT) && savedEmail.equals(DEFAULT)) {
            Toast.makeText(this, "No saved username and password. Create a new username and password.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }
    }

}

