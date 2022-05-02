package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
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


        //Declaring buttons
        CardView logoutCV = (CardView) findViewById(R.id.logoutCV);
        CardView learnToDriveCV = (CardView) findViewById(R.id.learnToDriveCV);
        CardView accountManagementCV = (CardView) findViewById(R.id.accountManagementCV);
        CardView adminToolsCV = (CardView) findViewById(R.id.adminToolsCV);
        GridLayout adminGrid = findViewById(R.id.adminGrid);



        //IF USER IS GUEST, HIDE ACCOUNT MANAGEMENT OPTION
        if (username.equals("guest")){
            accountManagementCV.setVisibility(View.GONE);
        }

        //IF USER IS ADMIN, SHOW ADMIN TOOLS OPTION
        if (username.equals("admin")){
            adminGrid.setVisibility(View.VISIBLE);
            adminToolsCV.setVisibility(View.VISIBLE);
        }





        //Button Listeners
        learnToDriveCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityLearnToDriveMenu.class);
                intent.putExtra("username_key",username);
                startActivity(intent);
                finish();
            }
        });

        logoutCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
                intent.removeExtra(username);
                startActivity(intent);
                finish();
            }
        });

        accountManagementCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityAccountMenu.class);
                intent.putExtra("username_key",username);
                startActivity(intent);
                finish();
            }
        });


        adminToolsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityAdminTools.class);
                intent.putExtra("username_key",username);
                startActivity(intent);
                finish();
            }
        });
    }



    //LOG OUT ON BACK BUTTON
    @Override
    public void onBackPressed()
    {
        //BACK BUTTON MUST BE PRESSED TWICE WITHIN 2 SECONDS FOR ACCOUNT TO LOG OUT
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
