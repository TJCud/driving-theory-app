package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ActivityAdminTools extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tools);


        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        String username = intent.getStringExtra("username_key");


        //Declaring buttons
        CardView editUsersCV = (CardView) findViewById(R.id.editUsers);
        CardView editResultsCV = (CardView) findViewById(R.id.editResults);
        CardView editQuestionsCV = (CardView) findViewById(R.id.editQuestions);
        ImageView backButtonIcon = (ImageView) findViewById(R.id.ID_returnButton);


        if (!username.equals("admin")){
            editUsersCV.setVisibility(View.GONE);
            editResultsCV.setVisibility(View.GONE);
            editQuestionsCV.setVisibility(View.GONE);
        }


        //Button Listeners
        editUsersCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            editUsers(username);
            }
        });

        editResultsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        editQuestionsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editQuestions(username);
            }
        });



        backButtonIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnMenu = new Intent(getApplicationContext(), ActivityAccountMenu.class);
                returnMenu.putExtra("username_key",username);
                finish();
                startActivity(returnMenu);
            }
        });

    }

    //Button Actions
    public void editUsers(String username) {
        Intent intent = new Intent(getApplicationContext(), ActivityAdminAllUsers.class);
        intent.putExtra("username_key",username);
        finish();
        startActivity(intent);

    }

    public void editResults() {
    }

    public void editQuestions(String username) {
        Intent intent = new Intent(getApplicationContext(), ActivityAdminEditQuestion.class);
        intent.putExtra("username_key",username);
        finish();
        startActivity(intent);
    }



}