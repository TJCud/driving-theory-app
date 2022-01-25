package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class AllResults extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    TestDbHelper mDatabaseHelper;

    private ListView mListView;
    private Button returnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_results);
        //returnBtn = findViewById(R.id.returnbtn);
        TextView overviewLabel = (TextView) findViewById(R.id.ID_overview_stats);
        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new TestDbHelper(this);

        //Code for passing username from last activity and assigning to string variable
        Intent intent = getIntent();
        String username = intent.getStringExtra("username_key");

        //Run List function passing in username
        populateListView(username, overviewLabel);





    }

    private void populateListView(String getUsernameData, TextView overviewLabel) {
        //get the data and append to a list
        Cursor data = mDatabaseHelper.getAllResults(getUsernameData);

        int pass=0, fail=0;
        ArrayList<String> listData = new ArrayList<>();


        while(data.moveToNext()){
            int score = data.getInt(2);
            int questions = data.getInt(3);
            String date = data.getString(4);
            String verdict = "";


            //CHECKS IF PASS PERCENTAGE IS ACHIEVED AND DISPLAYS OUTCOME
            double passCheck = score * 100 / questions;
            if(passCheck > 85){ verdict = "PASS";pass++; }
            else { verdict = "FAIL";fail++; }

            listData.add(date + "\n" + "Test Score: " + score + "/" + questions + " (" + passCheck + "%) " + "Outcome: " + verdict);
            double averageScore = score / listData.size();
        }

        int size = listData.size();
        double overallPassRate;

        //ERROR HANDLING IF USER HAS NOT TAKEN ANY PREVIOUS TESTS
        if (size<1){
            overviewLabel.setText("No tests found.");
        }
        else{
        overallPassRate = pass * 100 / size;
        Collections.reverse(listData); // Now the list is in reverse order (most recent test at top)

            overviewLabel.setText("Total Tests Taken: " + size + " | Pass rate: " + overallPassRate + "%");

        }


        if (getUsernameData.equals("Guest")){
            overviewLabel.setText("Guest results are not saved. Please sign in.");}


        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

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