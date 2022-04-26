package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


public class ActivityFullExamPreScreen extends AppCompatActivity {

    Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pre_screen);


        startButton = (Button) findViewById(R.id.ID_startButton);



        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        String username = intent.getStringExtra("username_key");



        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startTest(username);
                finish();
            }
        });

    }


    //Button Actions
    public void startTest(String passUsername){
        Intent intent = new Intent(ActivityFullExamPreScreen.this, ActivityFullExam.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);
    }




}