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

        //PUTS APP INTO FULL SCREEN
        hideSystemUI();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        splashDelayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
                startActivity(i);
                finish();

            }
        },5000);


        //INITIALISE AND HIDE PROGRESS BAR
        pbProgressBar = findViewById(R.id.pbProgressBar);
        pbProgressBar.setVisibility(View.GONE);

    }


/*    public void getQuestions(){

        pbProgressBar.setVisibility(View.VISIBLE);
        FetchData fetchData = new FetchData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/getquestions.php");
        if (fetchData.startFetch()) {
            if (fetchData.onComplete()) {
                fetchedResult = fetchData.getData();
                Log.i("FetchData", fetchedResult);
                //End ProgressBar (Set visibility to GONE)
                pbProgressBar.setVisibility(View.GONE);

            }
        }

        try {
            JSONObject obj = new JSONObject(fetchedResult);
            JSONArray questionData = obj.getJSONArray("questiondata");
            int n = questionData.length();
            for (int i = 0; i < n; ++i) {
                JSONObject questionObj = questionData.getJSONObject(i);
                QuestionModel q = new QuestionModel();
                int ID = questionObj.getInt("id");
                q.setID(ID);
                String category = questionObj.getString("category");
                q.setCategory(category);
                String question = questionObj.getString("question");
                q.setQuestion(question);
                String option1 = questionObj.getString("option1");
                q.setOption1(option1);
                String option2 = questionObj.getString("option2");
                q.setOption2(option2);
                String option3 = questionObj.getString("option3");
                q.setOption3(option3);
                String option4 = questionObj.getString("option4");
                q.setOption4(option4);
                int answer = questionObj.getInt("answer");
                q.setAnswerNr(answer);
                String imageID = questionObj.getString("image");
                q.setImageID(imageID);
                String explanation = questionObj.getString("explanation");
                q.setExplanation(explanation);
                questionListFromRemote.add(q);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }*/

    //CODE FOR SHOWING FULL SCREEN
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