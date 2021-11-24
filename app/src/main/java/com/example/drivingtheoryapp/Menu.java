package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        //Declaring buttons
        Button logoutbtn = (Button) findViewById(R.id.logoutbtn);
        Button testbtn = (Button) findViewById(R.id.testbtn);
        Button accmgmtbtn = (Button) findViewById(R.id.accmgmtbtn);
        Button settingbtn = (Button) findViewById(R.id.settingsbtn);

        //Button Listeners
        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTest();
            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        accmgmtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accMgmt();
            }
        });

        settingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings();
            }
        });




    }

    //Button Actions
    public void logOut(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void startTest(){
        Intent intent = new Intent(this, MockTest.class);
        startActivity(intent);
    }

    public void accMgmt(){
    }

    public void settings(){
    }

}
