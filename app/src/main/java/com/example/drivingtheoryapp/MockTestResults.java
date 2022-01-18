package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MockTestResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test_results);
        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        TextView verdictLabel = (TextView) findViewById(R.id.verdictLabel);
        Button retrybtn = (Button) findViewById(R.id.retrybtn);
        Button menubtn = (Button) findViewById(R.id.menubtn);


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
        scoreLabel.setText(score + " / " + totalQuestions + "\n" + "Accuracy " + passCheck + "%" + "\n" + "(86% or higher required)");


        //Button Listeners
        retrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retryTest();
            }
        });

        menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainMenu();
            }
        });



    }

    //Button Actions
    public void retryTest(){
        Intent intent = new Intent(this, MockTest.class);
        startActivity(intent);
    }

    public void mainMenu(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }



}