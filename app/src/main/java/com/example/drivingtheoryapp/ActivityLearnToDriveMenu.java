package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class ActivityLearnToDriveMenu extends AppCompatActivity implements DialogPositiveNegative.ExampleDialogListener {

    private String username;
    private boolean connected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_menu);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        connected = sharedPreferences.getBoolean("connected_key",false);

        if (!connected){
            openDialog("username", "Unable to connect to server. Please select an option below.",
                    "Connection Error", "Try Again", "Continue Offline");
        }



        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        username = intent.getStringExtra("username_key");


        //Declaring buttons
        CardView fullTestCV = (CardView) findViewById(R.id.mockbtn);
        CardView practiceModeCV = (CardView) findViewById(R.id.practicebtn);
        CardView hwCodeCV = (CardView) findViewById(R.id.hwcodebtn);
        CardView drivingTipsCV = (CardView) findViewById(R.id.tipsbtn);

        //Button Listeners
        fullTestCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLearnToDriveMenu.this, ActivityFullExamPreScreen.class);
                intent.putExtra("username_key",username);
                finish();
                startActivity(intent);
            }
        });

        practiceModeCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLearnToDriveMenu.this, ActivityPracticeMenu.class);
                intent.putExtra("username_key",username);
                startActivity(intent);
                finish();
            }
        });

        hwCodeCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLearnToDriveMenu.this, ActivityWebView.class);
                intent.putExtra("username_key",username);
                intent.putExtra("document_key",getResources().getString(R.string.highwayCode));
                startActivity(intent);

            }
        });

        drivingTipsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLearnToDriveMenu.this, ActivityWebView.class);
                intent.putExtra("username_key",username);
                intent.putExtra("document_key",getResources().getString(R.string.drivingTips));
                startActivity(intent);

            }
        });
    }


    @Override
    public void onBackPressed()
    {
        Intent intent;

        if (connected) {

            if (username.equals("guest")) {
                intent = new Intent(ActivityLearnToDriveMenu.this, ActivityLogin.class);
            } else {
                intent = new Intent(ActivityLearnToDriveMenu.this, ActivityMainMenu.class);
            }
            intent.putExtra("username_key", username);
            startActivity(intent);
        }
        finish();
    }



    @Override
    public void applyChoice(String username) {
        Intent intent = new Intent(getApplicationContext(), ActivitySplashScreen.class);
        //intent.putExtra("username_key", "guest");
        startActivity(intent);
        finish();
    }

    //OPENING DIALOG
    public void openDialog(String username, String input, String title, String positiveButton, String negativeButton) {
        DialogPositiveNegative dialogPositiveNegative = new DialogPositiveNegative(username,input,title,positiveButton,negativeButton);
        dialogPositiveNegative.show(getSupportFragmentManager(), "example dialog");
    }



}