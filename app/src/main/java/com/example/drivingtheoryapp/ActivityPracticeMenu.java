package com.example.drivingtheoryapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ActivityPracticeMenu extends AppCompatActivity {


    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8, rb9, rb10, rb11, rb12, rb13, rb14;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_menu);

        //ASSIGN VARIABLES TO ID's
        radioGroup = findViewById(R.id.ID_category_rg);
        rb1 = findViewById(R.id.ID_category_rb1);
        rb2 = findViewById(R.id.ID_category_rb2);
        rb3 = findViewById(R.id.ID_category_rb3);
        rb4 = findViewById(R.id.ID_category_rb4);
        rb5 = findViewById(R.id.ID_category_rb5);
        rb6 = findViewById(R.id.ID_category_rb6);
        rb7 = findViewById(R.id.ID_category_rb7);
        rb8 = findViewById(R.id.ID_category_rb8);
        rb9 = findViewById(R.id.ID_category_rb9);
        rb10 = findViewById(R.id.ID_category_rb10);
        rb11 = findViewById(R.id.ID_category_rb11);
        rb12 = findViewById(R.id.ID_category_rb12);
        rb13 = findViewById(R.id.ID_category_rb13);
        rb14 = findViewById(R.id.ID_category_rb14);
        Button btnNext = findViewById(R.id.btnNext);

        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        String username = intent.getStringExtra("username_key");






        //WHEN BUTTON IS CLICKED, CHECK TO SEE AN ANSWER HAS BEEN SELECTED
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(     rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked() ||
                            rb5.isChecked() || rb6.isChecked() || rb7.isChecked() || rb8.isChecked() ||
                            rb9.isChecked() || rb10.isChecked() || rb11.isChecked() || rb12.isChecked() ||
                            rb13.isChecked() || rb14.isChecked()
                    ){
                        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
                        String selectedCategory = (String) rbSelected.getText();
                        startTest(username,selectedCategory);
                    } else {
                        Toast.makeText(ActivityPracticeMenu.this, "Please select a category", Toast.LENGTH_SHORT).show();}

            }
        });
    }


    //Button Actions
    public void startTest(String passUsername, String selectedCategory){

        Intent intent = new Intent(ActivityPracticeMenu.this, ActivityPracticeTest.class);
        intent.putExtra("username_key",passUsername);
        intent.putExtra("category_key",selectedCategory);
        startActivity(intent);
        finish();
    }



}
