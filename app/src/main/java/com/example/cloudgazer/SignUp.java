package com.example.cloudgazer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    EditText usernameEditText, emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameEditText = findViewById(R.id.usernameEntry);
        emailEditText = findViewById(R.id.emailEntry);
        passwordEditText = findViewById(R.id.passwordEntry);
    }

    public void signedUp(View v){
        submit(v);
        Intent i  = new Intent(this, Welcome.class);
        startActivity(i);
    }

    public void reflectionActivity(View v){
        Intent i  = new Intent(this, ReflectionActivity.class);
        startActivity(i);
    }

    public void submit(View view){
        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("username", usernameEditText.getText().toString());
        editor.putString("email", emailEditText.getText().toString());
        editor.putString("password", passwordEditText.getText().toString());
        Toast.makeText(this, "Name, email and password have been saved to Preferences", Toast.LENGTH_LONG).show();
        editor.commit();
    }

}