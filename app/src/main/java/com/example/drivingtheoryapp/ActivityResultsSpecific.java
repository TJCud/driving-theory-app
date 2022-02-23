package com.example.drivingtheoryapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


public class ActivityResultsSpecific extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_specific);

        Bundle recdData = getIntent().getExtras();
        String passedValue = recdData.getString("result");

        TextView textView = (TextView)findViewById(R.id.tvReviewResults);

        textView.setText(passedValue); }



/*
        //Button Listeners
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainMenu();
            }
        }); }


    //Button Actions
    public void mainMenu(){
        Intent intent = new Intent(this, ActivityMainMenu.class);
        startActivity(intent);
    }
*/



}


