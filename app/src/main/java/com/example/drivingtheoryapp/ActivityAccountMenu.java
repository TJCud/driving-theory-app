package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class ActivityAccountMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_menu);

        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        String username = intent.getStringExtra("username_key");


        //Declaring buttons
        CardView trackProgressCV = (CardView) findViewById(R.id.trackProgress);
        CardView deleteAccountCV = (CardView) findViewById(R.id.deleteAccount);
        CardView changePasswordCV = (CardView) findViewById(R.id.changePassword);
        CardView changeUsernameCV = (CardView) findViewById(R.id.changeUsername);
        CardView adminToolsCV = (CardView) findViewById(R.id.adminTools);
        adminToolsCV.setVisibility(View.GONE);
        ImageView backButtonIcon = (ImageView) findViewById(R.id.ID_returnButton);


        if (username.equals("admin")){
            changeUsernameCV.setVisibility(View.GONE);
            changePasswordCV.setVisibility(View.GONE);
            deleteAccountCV.setVisibility(View.GONE);
            adminToolsCV.setVisibility(View.VISIBLE);
        }


        //Button Listeners
        trackProgressCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProgress(username);
            }
        });

        deleteAccountCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount(username);
            }
        });

        changePasswordCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword(username);
            }
        });

        changeUsernameCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUsername(username);
            }
        });

        adminToolsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminTools(username);
            }
        });


        backButtonIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnMenu = new Intent(getApplicationContext(), ActivityMainMenu.class);
                returnMenu.putExtra("username_key",username);
                finish();
                startActivity(returnMenu);
            }
        });

    }

    //Button Actions
    public void openProgress(String passUsername) {
        Intent intent = new Intent(this, ActivityResultsAll.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);
    }

    public void deleteAccount(String passUsername) {
        Intent intent = new Intent(this, RBsaverTest.class);
        startActivity(intent);

    }

    public void changePassword(String passUsername) {
    }

    public void changeUsername(String passUsername) {
    }

    public void adminTools(String passUsername) {
        Intent intent = new Intent(this, ActivityAdminTools.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);
    }



}
