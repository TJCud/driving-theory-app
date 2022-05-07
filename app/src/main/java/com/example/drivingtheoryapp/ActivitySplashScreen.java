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


public class ActivitySplashScreen extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {
    private String fetchedResult;
    public static final String SHARED_PREFS = "sharedPrefs";
    Handler splashDelayHandler = new Handler();
    Boolean proceed=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


                //Run method to load questions from database
                getAllQuestions();


                if (!getAllQuestions().equals("getAllQuestions")) {
                    openDialog("username", "Unable to connect to server.", "Connection Error", "Continue Offline", "Try Again");
                }

                Log.i("Status", "Attempting to connect");


                if(proceed) {

                    //INITIALISING SHARED PREFERENCES TO SHARE WITH EXAM ACTIVITIES
                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("questionJSON_key", fetchedResult);
                    editor.apply();

                    //INTENT FOR NEXT ACTIVITY (LOGIN SCREEN)
                    Intent goToLoginActivity = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
                    startActivity(goToLoginActivity);

                    finish(); //Close splash screen activity
                }

            }
        }, 1000); //1 second delay


    }


    public String getAllQuestions() {

        //FETCH DATA METHOD ON GET_ALL_QUESTIONS PHP FUNCTION
        FetchData fetchData = new FetchData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/get_all_questions.php");

        if (fetchData.startFetch()) {
            Log.i("Status", "Attempting to connect");
            if (fetchData.onComplete()) {
                Log.i("Status", "Connected, returning data");
                fetchedResult = fetchData.getData();

                //For testing purposes
                Log.i("Status", String.valueOf(fetchedResult.startsWith("getAllQuestions", 2)));

                if(fetchedResult.startsWith("getAllQuestions", 2)){
                    proceed=true;
                }
            }
        }
        return fetchedResult.substring(2, 17);
    }



    @Override
    public void applyChoice(String username) {
        Intent intent = new Intent(getApplicationContext(), ActivityLearnToDriveMenu.class);
        intent.putExtra("username_key", "guest");
        startActivity(intent);
        finish();
    }

    //OPENING DIALOG
    public void openDialog(String username, String input, String title, String positiveButton, String negativeButton) {
        ExampleDialog exampleDialog = new ExampleDialog(username,input,title,positiveButton,negativeButton);
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }




}