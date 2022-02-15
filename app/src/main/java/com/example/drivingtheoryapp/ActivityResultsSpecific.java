package com.example.drivingtheoryapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;


public class ActivityResultsSpecific extends AppCompatActivity {


    private static final String TAG = "EditDataActivity";



    TestDbHelper mDatabaseHelper;

    private String selectedName;
    private String selectedResult;
    private int selectedID;
    private TextView reviewResults;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_specific);

        Bundle recdData = getIntent().getExtras();
        String myVal1 = recdData.getString("id");
        String myVal2 = recdData.getString("username_key");
        String myVal3 = recdData.getString("result");

        TextView textView = (TextView)findViewById(R.id.tvReviewResults);

        textView.setText(myVal3); }







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
        Intent intent = new Intent(this, ActivityMainMenu.class);
        startActivity(intent);
    }
*/




}


