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

    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pre_screen);


        Button startButton = (Button) findViewById(R.id.ID_startButton);

        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        username = intent.getStringExtra("username_key");



        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityFullExamPreScreen.this, ActivityFullExam.class);
                intent.putExtra("username_key",username);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ActivityFullExamPreScreen.this, ActivityLearnToDriveMenu.class);
        intent.putExtra("username_key",username);
        startActivity(intent);
        finish();
    }




}