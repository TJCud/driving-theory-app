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
        Intent logOut = new Intent(this, Login.class);
        logOut.removeExtra(passUsername);
        startActivity(logOut);
        finish();
    }

    public void testMenu(String passUsername){
        Intent openResults = new Intent(getApplicationContext(), TestMenu.class);
        openResults.putExtra("username_key",passUsername);
        startActivity(openResults);
    }

    public void accMgmt(String passUsername){
        Intent openAccMgmt = new Intent(getApplicationContext(), AccountMenu.class);
        openAccMgmt.putExtra("username_key",passUsername);
        startActivity(openAccMgmt);
    }

    public void settings(String passUsername){
        Intent openSettings = new Intent(getApplicationContext(), SettingsMenu.class);
        openSettings.putExtra("username_key",passUsername);
        startActivity(openSettings);
    }


    //LOG OUT ON BACK BUTTON
    @Override
    public void onBackPressed()
    {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();

            Intent logOut = new Intent(this, Login.class);
            startActivity(logOut);
            finish();


        } else {
            Toast.makeText(getBaseContext(), "Press back again to log out", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }



}
