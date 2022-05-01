package com.example.drivingtheoryapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


public class ActivityResultsSpecific extends AppCompatActivity {


    private String passedUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_specific);

        //Gets intent (exam data) from previous activity and assigns it to String variable
        Bundle receivedData = getIntent().getExtras();
        String passedExamData = receivedData.getString("result_key");
        passedUsername = receivedData.getString("username_key");


        //Assign string contents to text view on the UI
        TextView tvReviewResults = (TextView)findViewById(R.id.tvReviewResults);
        tvReviewResults.setText(passedExamData);

    }





    //Return to full results activity on back press
    @Override
    public void onBackPressed()
    {
            Intent returnToResults = new Intent(this, ActivityResultsAll.class);
            returnToResults.putExtra("username_key",passedUsername);
            startActivity(returnToResults);
            finish();
    }




}


