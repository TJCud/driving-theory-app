package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ActivityFullExamResult extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_exam_result);


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
        if(passCheck > 85){
            verdictLabel.setText("Exam Passed.\n Congratulations!");
        }
        else {
            verdictLabel.setText("Exam Failed.\n Please try again.");
        }
        scoreLabel.setText(score + " out of " + totalQuestions + "\n" + "Accuracy " + passCheck + "%" + "\n" + "(86% or higher required)");



        //If user is a guest, hide results button
        if (username.equals("guest")){
            resultsBtn.setVisibility(View.GONE);
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
        Intent intent = new Intent(this, ActivityFullExam.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);
        finish();
    }





    public void mainMenu(String passUsername){
        Intent intent;

        //If user is a guest, return to Learn To Drive Menu
        if (passUsername=="guest"){
            intent = new Intent(this, ActivityLearnToDriveMenu.class);
        }
        //Return to main menu if user is registered
        else{
            intent = new Intent(this, ActivityMainMenu.class);
        }

        intent.putExtra("username_key",passUsername);
        startActivity(intent);
        finish();
    }



    public void viewResults(String passUsername){
        Intent intent = new Intent(this, ActivityResultsAll.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);
        finish();
    }



}