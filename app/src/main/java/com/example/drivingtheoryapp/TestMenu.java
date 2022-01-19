package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TestMenu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_menu);



        //Declaring buttons
        CardView mockbtn = (CardView) findViewById(R.id.mockbtn);
        CardView practicebtn = (CardView) findViewById(R.id.practicebtn);
        CardView hwcodebtn = (CardView) findViewById(R.id.hwcodebtn);
        CardView tipsbtn = (CardView) findViewById(R.id.tipsbtn);

        //Button Listeners
        mockbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTest();
            }
        });

        practicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPractice();
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
    public void startTest(){
        Intent intent = new Intent(TestMenu.this, MockTestActivity.class);
        startActivity(intent);
    }

    public void openPractice(){

    }

    public void openHWCODE(){
    }

    public void openTips(){
    }

}