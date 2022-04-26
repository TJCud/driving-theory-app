package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivityLearnToDriveMenu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_menu);


        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        String username = intent.getStringExtra("username_key");


        //Declaring buttons
        CardView fullTestCV = (CardView) findViewById(R.id.mockbtn);
        CardView practiceModeCV = (CardView) findViewById(R.id.practicebtn);
        CardView hwCodeCV = (CardView) findViewById(R.id.hwcodebtn);
        CardView drivingTipsCV = (CardView) findViewById(R.id.tipsbtn);
        ImageView returnButtonIcon = (ImageView) findViewById(R.id.ID_returnButton);

        //Button Listeners
        fullTestCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLearnToDriveMenu.this, ActivityFullExamPreScreen.class);
                intent.putExtra("username_key",username);
                finish();
                startActivity(intent);
            }
        });

        practiceModeCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLearnToDriveMenu.this, ActivityPracticeMenu.class);
                intent.putExtra("username_key",username);
                startActivity(intent);
                finish();
            }
        });

        hwCodeCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLearnToDriveMenu.this, ActivityWebView.class);
                intent.putExtra("username_key",username);
                intent.putExtra("document_key","https://www.gov.uk/browse/driving/highway-code-road-safety");
                startActivity(intent);

            }
        });

        drivingTipsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLearnToDriveMenu.this, ActivityWebView.class);
                intent.putExtra("username_key",username);
                intent.putExtra("document_key","https://www.drivingtestsuccess.com/blog/last-minute-tips-pass-your-theory-test");
                startActivity(intent);

            }
        });



    }


}