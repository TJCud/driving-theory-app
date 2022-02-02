package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class TestMenu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_menu);

        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        String username = intent.getStringExtra("username_key");


        //Declaring buttons
        CardView mockbtn = (CardView) findViewById(R.id.mockbtn);
        CardView practicebtn = (CardView) findViewById(R.id.practicebtn);
        CardView hwcodebtn = (CardView) findViewById(R.id.hwcodebtn);
        CardView tipsbtn = (CardView) findViewById(R.id.tipsbtn);

        //Button Listeners
        mockbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTest(username);
            }
        });

        practicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPractice(username);
            }
        });

        hwcodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHWCODE();
            }
        });

        tipsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTips();
            }
        });




    }

    //Button Actions
    public void startTest(String passUsername){
        Intent intent = new Intent(TestMenu.this, ActivityTestPreScreen.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);
    }

    public void openPractice(String passUsername){
        Intent intent = new Intent(TestMenu.this, ActivityPracticeMenu.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);

    }

    public void openHWCODE(){
    }

    public void openTips(){
    }







}