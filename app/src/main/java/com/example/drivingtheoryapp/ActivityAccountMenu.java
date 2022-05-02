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

    private String username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_menu);

        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        username = intent.getStringExtra("username_key");


        //Declaring buttons
        CardView trackProgressCV = (CardView) findViewById(R.id.trackProgress);
        CardView deleteAccountCV = (CardView) findViewById(R.id.deleteAccount);
        CardView changePasswordCV = (CardView) findViewById(R.id.changePassword);



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



    }

    //Button Actions
    public void openProgress(String passUsername) {
        Intent intent = new Intent(this, ActivityResultsAll.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);
        finish();
    }

    public void deleteAccount(String passUsername) {

    }

    public void changePassword(String passUsername) {
    }



    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ActivityAccountMenu.this, ActivityMainMenu.class);
        intent.putExtra("username_key",username);
        startActivity(intent);
        finish();
    }


}
