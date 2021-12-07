package com.example.cloudgazer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {

    public static final String DEFAULT = "not available";

    EditText usernameEntered, passwordEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        usernameEntered = findViewById(R.id.usernameEntry);
        passwordEntered = findViewById(R.id.passwordEntry);
    }

    //saving information as a sharedpref
    public void logIn(View v){
        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("enteredUsername", usernameEntered.getText().toString());
        editor.putString("enteredPassword", passwordEntered.getText().toString());
        editor.commit();

        String username = sharedPrefs.getString("username", DEFAULT);
        String password = sharedPrefs.getString("password", DEFAULT);
        String enteredUsername = sharedPrefs.getString("enteredUsername", DEFAULT);
        String enteredPassword = sharedPrefs.getString("enteredPassword", DEFAULT);

        //see values for username and password
        Log.i("TESTING", "SET USERNAME: "+username+" || INPUTTED: "+enteredUsername+" || SET PASSWORD: "+password+" || INPUTTED: "+enteredPassword);

        //if there is nothing saved in sharedprefs
        if (username.equals(DEFAULT)||password.equals(DEFAULT))
        {
            Toast.makeText(this, "No data found. Please create an account.", Toast.LENGTH_LONG).show();
        }

        //if there is success and typed values match sharedprefs
        else if (enteredUsername.equals(username) && enteredPassword.equals(password))
        {
            Toast.makeText(this, "Entry success!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }

        //if typed values are incorrect
        else if (enteredUsername != username && enteredPassword != password){
            Toast.makeText(this, "Incorrect username and/or password. Try again or create another account.", Toast.LENGTH_LONG).show();
        }
    }

    public void accountCreate (View v){
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }

}

