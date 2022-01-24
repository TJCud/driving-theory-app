package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;




public class ActivityTestPreScreen extends AppCompatActivity {

    Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pre_screen);

        startButton = (Button) findViewById(R.id.ID_startButton);
        ImageView trafficLights = findViewById(R.id.ID_trafficlights);
        TextView testInfo = findViewById(R.id.ID_testInfo);
        TextView timerText = findViewById(R.id.ID_timer);
        timerText.setVisibility(View.INVISIBLE);

        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        String username = intent.getStringExtra("username_key");



        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                testInfo.setText("Starting test in... 3");
                startButton.setVisibility(View.GONE);
                trafficLights.setVisibility(View.VISIBLE);
                timerText.setVisibility(View.VISIBLE);
                timerText.setTextColor(Color.RED);

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        testInfo.setText("Starting test in... 2");
                        trafficLights.setImageResource(R.drawable.trafficlight_amber);
                        timerText.setTextColor(Color.YELLOW);
                    }
                }, 1 * 1000);

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        testInfo.setText("Starting test in... 1");
                        trafficLights.setImageResource(R.drawable.trafficlight_green);
                        timerText.setTextColor(Color.GREEN);
                    }
                }, 2 * 1000);

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        finish();
                        startTest(username);
                    }
                }, 3 * 1000);

            }
        });
    }


    //Button Actions
    public void startTest(String passUsername){
        Intent intent = new Intent(ActivityTestPreScreen.this, MockTestActivity.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);
    }





}