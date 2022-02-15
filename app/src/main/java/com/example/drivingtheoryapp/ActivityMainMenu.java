package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityMainMenu extends AppCompatActivity {

    //Calculating double press when logging out
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        String username = intent.getStringExtra("username_key");

        //Displaying username in welcome header
        TextView welcomeLabel = (TextView) findViewById(R.id.welcomeLabel);
        welcomeLabel.setText("Welcome, " + username + "!");

        //Declaring buttons
        CardView logoutbtn = (CardView) findViewById(R.id.logoutbtn);
        CardView testbtn = (CardView) findViewById(R.id.testbtn);
        CardView accmgmtbtn = (CardView) findViewById(R.id.accmgmtbtn);
        CardView settingbtn = (CardView) findViewById(R.id.settingsbtn);

        //Button Listeners
        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testMenu(username);
            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut(username);
            }
        });

        accmgmtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accMgmt(username);
            }
        });

        settingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings(username);
            }
        });




    }

    //Button Actions
    public void logOut(String passUsername){
        Intent intent = new Intent(this, ActivityLogin.class);
        intent.removeExtra(passUsername);
        startActivity(intent);
        finish();
    }

    public void testMenu(String passUsername){
        Intent intent = new Intent(getApplicationContext(), ActivityLearnToDriveMenu.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);
    }

    public void accMgmt(String passUsername){
        Intent intent = new Intent(getApplicationContext(), ActivityAccountMenu.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);
    }

    public void settings(String passUsername){
        Intent intent = new Intent(getApplicationContext(), ActivitySettingsMenu.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);
    }


    //LOG OUT ON BACK BUTTON
    @Override
    public void onBackPressed()
    {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();

            Intent logOut = new Intent(this, ActivityLogin.class);
            startActivity(logOut);
            finish();


        } else {
            Toast.makeText(getBaseContext(), "Press back again to log out", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }



}
