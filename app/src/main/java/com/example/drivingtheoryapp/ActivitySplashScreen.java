package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivitySplashScreen extends AppCompatActivity {
    private ProgressBar pbProgressBar;
    private String fetchedResult;
    private List<QuestionModel> questionListFromRemote;
    Handler splashDelayHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbProgressBar = findViewById(R.id.pbProgressBar);
        questionListFromRemote = new ArrayList<>();

        getAllQuestions();

        Log.i("FetchData", fetchedResult);


        //INITIALISE AND HIDE PROGRESS BAR
        pbProgressBar.setVisibility(View.VISIBLE);

        splashDelayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent passQuestionJSONToExamActivity = new Intent(ActivitySplashScreen.this, ActivityFullExam.class);
                passQuestionJSONToExamActivity.putExtra("questionJSON", fetchedResult);

                Intent goToLoginActivity = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
                startActivity(goToLoginActivity);


                finish();

            }
        },3000);




    }


    public void getAllQuestions() {

        FetchData fetchData = new FetchData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/get_all_questions.php");
        if (fetchData.startFetch()) {
            if (fetchData.onComplete()) {
                fetchedResult = fetchData.getData();
            }
        }
    }



}