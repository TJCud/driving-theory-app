package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ActivityAdminTools extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tools);


        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        username = intent.getStringExtra("username_key");

        //Declaring buttons
        CardView editUsersCV = (CardView) findViewById(R.id.editUsers);
        CardView editQuestionsCV = (CardView) findViewById(R.id.editQuestions);



        //Button Listeners
        editUsersCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            editUsers();
            }
        });


        editQuestionsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editQuestions();
            }
        });



    }

    //Button Actions
    public void editUsers() {
        Intent intent = new Intent(getApplicationContext(), ActivityAdminAllUsers.class);
        startActivity(intent);
        finish();

    }



    public void editQuestions() {
        Intent intent = new Intent(getApplicationContext(), ActivityAdminAllQuestions.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ActivityAdminTools.this, ActivityMainMenu.class);
        intent.putExtra("username_key","admin");
        startActivity(intent);
        finish();
    }


}