package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


public class ActivitySplashScreen extends AppCompatActivity {
    private String fetchedResult;
    public static final String SHARED_PREFS = "sharedPrefs";
    Handler splashDelayHandler = new Handler();
    Boolean connected=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INITIALISING SHARED PREFERENCES TO SHARE WITH EXAM ACTIVITIES
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Initialise and assign progress bar / text objects
        ProgressBar pbProgressBar = findViewById(R.id.pbProgressBar);
        TextView tvProgressBarText = findViewById(R.id.tvProgressBarText);

        //DISPLAYING STATUS ON UI
        pbProgressBar.setVisibility(View.VISIBLE);
        tvProgressBarText.setVisibility(View.VISIBLE);
        tvProgressBarText.setText("Connecting to database...");


        //HANDLER FOR ADDING DELAY TO SPLASH SCREEN
        splashDelayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {


                //Test connection to database
                establishConnection();


                //Close splash screen activity
                if (!establishConnection()) {
                    //If connection not established, pass in boolean to shared prefs
                    editor.putBoolean("connected_key", false);
                    editor.apply();
                    //INTENT FOR NEXT ACTIVITY (LOGIN SCREEN)
                    Intent goToMainMenuActivity = new Intent(ActivitySplashScreen.this, ActivityLearnToDriveMenu.class);
                    goToMainMenuActivity.putExtra("username_key", "guest");
                    startActivity(goToMainMenuActivity);
                }
                else {
                    //If connection established, pass in fetched results to shared prefs
                    editor.putString("questionJSON_key", fetchedResult);
                    editor.putBoolean("connected_key", true);
                    editor.apply();
                    //INTENT FOR NEXT ACTIVITY (LOGIN SCREEN)
                    Intent goToLoginActivity = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
                    startActivity(goToLoginActivity);
                }
                finish(); //Close splash screen activity


            }
        }, 1000); //1 second delay


    }


    public boolean establishConnection() {

        //FETCH DATA METHOD ON GET_ALL_QUESTIONS PHP FUNCTION
        FetchData fetchData = new FetchData(getResources().getString(R.string.getAllQuestions));

        if (fetchData.startFetch()) {
            if (fetchData.onComplete()) {
                Log.i("Status", "Connected, returning data");
                fetchedResult = fetchData.getData();

                //For testing purposes
                Log.i("Status", String.valueOf(fetchedResult.startsWith("getAllQuestions", 2)));

                if(!fetchedResult.startsWith("getAllQuestions", 2)){
                    return false;
                }
            }
        }
        return true;
    }





}