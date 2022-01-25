package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MockTestResults extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test_results);

        //Initialise database helper
        TestDbHelper dbHelper = new TestDbHelper(this);


        //Code for passing username from last activity and assigning to string variable
        Intent intent = getIntent();
        String username = intent.getStringExtra("username_key");

        //Variables
        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        TextView verdictLabel = (TextView) findViewById(R.id.verdictLabel);
        Button retryBtn = (Button) findViewById(R.id.retrybtn);
        Button menuBtn = (Button) findViewById(R.id.menubtn);
        Button resultsBtn = (Button) findViewById(R.id.resultsbtn);


        //CALCULATES PERCENTAGE OF CORRECT QUESTIONS
        int score = getIntent().getIntExtra("SCORE", 0);
        int totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 0);
        double passCheck = score * 100 / totalQuestions;


        //CHECKS IF PASS PERCENTAGE IS ACHIEVED AND DISPLAYS OUTCOME
        if(passCheck > 86){
            verdictLabel.setText("Test Passed. Congratulations!");
        }
        else {
            verdictLabel.setText("Test Failed. Please try again.");
        }
        scoreLabel.setText(score + " out of " + totalQuestions + "\n" + "Accuracy " + passCheck + "%" + "\n" + "(86% or higher required)");



        //Assign current date and time to string
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("'Test Date: 'MMMM dd yyyy ' Test Time: ' hh:mm a");
        String dateToStr = format.format(today);

        //Saves results and date/time to database
        if (username.equals("Guest")){
            resultsBtn.setVisibility(View.GONE);
        }
        else{
            dbHelper.saveResults(username, score, totalQuestions, dateToStr);
        }




        //Button Listeners
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retryTest(username);
            }
        });

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainMenu(username);
            }
        });

        resultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewResults(username);
            }
        });
    }

    //Button Actions
    public void retryTest(String passUsername){
        Intent intent = new Intent(this, MockTestActivity.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);
        finish();
    }

    public void mainMenu(String passUsername){
        Intent intent = new Intent(this, Menu.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);
        finish();
    }

    public void viewResults(String passUsername){
        Intent intent = new Intent(this, AllResults.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);
        finish();
    }



}