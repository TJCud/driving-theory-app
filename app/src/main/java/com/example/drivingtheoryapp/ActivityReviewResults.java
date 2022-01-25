package com.example.drivingtheoryapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ListView;
import android.widget.TextView;


public class ActivityReviewResults extends AppCompatActivity {


    private static final String TAG = "EditDataActivity";



    TestDbHelper mDatabaseHelper;

    private String selectedName;
    private String selectedResult;
    private int selectedID;
    private TextView reviewResults;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_results);


        //get the intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id", -1); //NOTE: -1 is just the default value

        //now get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("username_key");

        //now get the name we passed as an extra
        selectedResult = receivedIntent.getStringExtra("result");


        reviewResults = (TextView) findViewById(R.id.ID_review_results);
        reviewResults.setText(selectedResult);
        reviewResults.setMovementMethod(new ScrollingMovementMethod());


    }






/*
        //Button Listeners
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainMenu();
            }
        }); }


    //Button Actions
    public void mainMenu(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
*/




}


