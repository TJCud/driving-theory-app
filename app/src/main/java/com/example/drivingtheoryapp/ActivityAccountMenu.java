package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        CardView progressbtn = (CardView) findViewById(R.id.progressbtn);
        CardView deletebtn = (CardView) findViewById(R.id.deletebtn);
        CardView changepwbtn = (CardView) findViewById(R.id.changepwbtn);
        CardView changeunbtn = (CardView) findViewById(R.id.changeunbtn);
        ImageView backButtonIcon = (ImageView) findViewById(R.id.ID_returnButton);

        //Button Listeners
        progressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProgress(username);
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount();
            }
        });

        changepwbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

        changeunbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUsername();
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

    public void deleteAccount() {
        Intent intent = new Intent(this, RBsaverTest.class);
        startActivity(intent);

    }

    public void changePassword() {
    }

    public void changeUsername() {
    }
}