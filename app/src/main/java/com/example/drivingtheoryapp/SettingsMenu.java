package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.inappmessaging.model.Button;

public class SettingsMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);

        boolean darkModeOn = false;

        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        String username = intent.getStringExtra("username_key");


        LinearLayout background = findViewById(R.id.ID_settingsPage);


        TextView soundButtonText = (TextView) findViewById(R.id.ID_soundTextControl);
        TextView darkModeButtonText = (TextView) findViewById(R.id.ID_darkModeTextControl);
        TextView questionBankButtonText = (TextView) findViewById(R.id.ID_questionBankTextControl);
        TextView voiceAssistantButtonText = (TextView) findViewById(R.id.ID_voiceAssistantTextControl);
        TextView settingsHeaderText = (TextView) findViewById(R.id.ID_settingsHeaderText);


        //Declaring buttons
        CardView soundButton = (CardView) findViewById(R.id.ID_soundControl);
        CardView darkModeOnButton = (CardView) findViewById(R.id.ID_darkMode);
       // CardView darkModeOffButton = (CardView) findViewById(R.id.ID_darkMode);
        CardView questionBankButton = (CardView) findViewById(R.id.ID_questionBank);
        CardView voiceAssistantButton = (CardView) findViewById(R.id.ID_voiceAssistant);



        //Button Listeners
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundControl();
            }
        });


        darkModeOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                background.setBackground(getResources().getDrawable(R.drawable.background_nightime));
                darkModeButtonText.setTextColor(Color.WHITE);
                soundButtonText.setTextColor(Color.WHITE);
                questionBankButtonText.setTextColor(Color.WHITE);
                voiceAssistantButtonText.setTextColor(Color.WHITE);
                settingsHeaderText.setTextColor(Color.WHITE);
                darkModeButtonText.setText("Dark Mode\n ON");

/*                background.setBackground(getResources().getDrawable(R.drawable.background_daytime));
                darkModeButtonText.setTextColor(Color.BLACK);
                soundButtonText.setTextColor(Color.BLACK);
                questionBankButtonText.setTextColor(Color.BLACK);
                voiceAssistantButtonText.setTextColor(Color.BLACK);
                settingsHeaderText.setTextColor(Color.BLACK);
                darkModeButtonText.setText("Dark Mode\n OFF");*/



            }
        });















/*        darkModeOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                background.setBackground(getResources().getDrawable(R.drawable.background_daytime));
                darkModeButtonText.setTextColor(Color.BLACK);
                soundButtonText.setTextColor(Color.BLACK);
                questionBankButtonText.setTextColor(Color.BLACK);
                voiceAssistantButtonText.setTextColor(Color.BLACK);
                settingsHeaderText.setTextColor(Color.BLACK);
                darkModeButtonText.setText("Dark Mode\n OFF");
                darkModeOnButton.setVisibility(View.GONE);
                darkModeOffButton.setVisibility(View.VISIBLE);
            }
        });*/

        questionBankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionBank();
            }
        });

        voiceAssistantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voiceAssistant();
            }
        });
    }

    //Button Actions
    public void soundControl() {

    }

    public void darkMode() {

    }

    public void questionBank() {
    }

    public void voiceAssistant() {
    }


}