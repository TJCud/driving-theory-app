package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        Button settingbtn = (Button) findViewById(R.id.settingsbtn);


        //Button Listeners
        settingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnMain();
            }
        });

    }

    //Button Actions
    public void returnMain(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }



}