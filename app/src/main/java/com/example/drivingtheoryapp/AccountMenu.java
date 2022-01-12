package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AccountMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_menu);

        //Declaring buttons
        CardView progressbtn = (CardView) findViewById(R.id.progressbtn);
        CardView deletebtn = (CardView) findViewById(R.id.deletebtn);
        CardView changepwbtn = (CardView) findViewById(R.id.changepwbtn);
        CardView changeunbtn = (CardView) findViewById(R.id.changeunbtn);

        //Button Listeners
        progressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProgress();
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


    }

    //Button Actions
    public void openProgress() {

    }

    public void deleteAccount() {

    }

    public void changePassword() {
    }

    public void changeUsername() {
    }
}
