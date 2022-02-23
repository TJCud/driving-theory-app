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

        //PUTS APP INTO FULL SCREEN
        hideSystemUI();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        startButton = (Button) findViewById(R.id.ID_startButton);
        ImageView trafficLights = findViewById(R.id.ID_trafficlights);
        ImageView backButtonIcon = (ImageView) findViewById(R.id.ID_returnButton);


        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        String username = intent.getStringExtra("username_key");



        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            //    testInfo.setText("Starting test in... 3");
                startButton.setVisibility(View.GONE);
                trafficLights.setVisibility(View.VISIBLE);



                new Handler().postDelayed(new Runnable() {
                    public void run() {
                      //  testInfo.setText("Starting test in... 2");
                        trafficLights.setImageResource(R.drawable.trafficlight_amber);

                    }
                }, 1 * 1000);

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                      //  testInfo.setText("Starting test in... 1");
                        trafficLights.setImageResource(R.drawable.trafficlight_green);

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


        backButtonIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnMenu = new Intent(getApplicationContext(), ActivityLearnToDriveMenu.class);
                returnMenu.putExtra("username_key",username);
                finish();
                startActivity(returnMenu);
            }
        });


    }


    //Button Actions
    public void startTest(String passUsername){
        Intent intent = new Intent(ActivityFullExamPreScreen.this, ActivityFullExam.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);
    }

    public void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }



}