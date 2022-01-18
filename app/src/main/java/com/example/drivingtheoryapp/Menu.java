package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        EditText displayName = (EditText) findViewById(R.id.username);
        TextView welcomeLabel = (TextView) findViewById(R.id.welcomeLabel);
        welcomeLabel.setText("Welcome, " + displayName);

        //Declaring buttons
        CardView logoutbtn = (CardView) findViewById(R.id.logoutbtn);
        CardView testbtn = (CardView) findViewById(R.id.testbtn);
        CardView accmgmtbtn = (CardView) findViewById(R.id.accmgmtbtn);
        CardView settingbtn = (CardView) findViewById(R.id.settingsbtn);

        //Button Listeners
        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testMenu();
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

    public void testMenu(){
        Intent intent = new Intent(this, TestMenu.class);
        startActivity(intent);
    }

    public void accMgmt(){
        Intent intent = new Intent(this, AccountMenu.class);
        startActivity(intent);
    }

    public void settings(){
        Intent intent = new Intent(this, SettingsMenu.class);
        startActivity(intent);
    }

}
