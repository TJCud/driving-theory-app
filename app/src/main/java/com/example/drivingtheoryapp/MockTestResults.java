package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MockTestResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test_results);

        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        TextView verdictLabel = (TextView) findViewById(R.id.verdictLabel);


        int score = getIntent().getIntExtra("SCORE", 0);
        int totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 0);

        if(score > 2){
            verdictLabel.setText("Passed");
        }
        else {
            verdictLabel.setText("Failed");
        }

        scoreLabel.setText(score + " / " + totalQuestions);

    }
}