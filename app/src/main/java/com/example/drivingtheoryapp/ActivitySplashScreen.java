package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.os.*;

public class ActivitySplashScreen extends AppCompatActivity {
    Handler h = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
                startActivity(i);
                finish();

            }
        },3000);


    }
}