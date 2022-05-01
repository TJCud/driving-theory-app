package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ActivityAdminTools extends AppCompatActivity {

    private ImageView returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tools);



        //Declaring buttons
        CardView editUsersCV = (CardView) findViewById(R.id.editUsers);
        CardView editQuestionsCV = (CardView) findViewById(R.id.editQuestions);
        returnButton = findViewById(R.id.returnButton);






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
        finish();
        startActivity(intent);

    }



    public void editQuestions() {
        Intent intent = new Intent(getApplicationContext(), ActivityAdminAllQuestions.class);
        finish();
        startActivity(intent);
    }



}