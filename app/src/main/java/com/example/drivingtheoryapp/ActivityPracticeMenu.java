package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

public class ActivityPracticeMenu extends AppCompatActivity {


    private CardView categoryCV1, categoryCV2, categoryCV3, categoryCV4, categoryCV5,
            categoryCV6, categoryCV7, categoryCV8, categoryCV9, categoryCV10,
            categoryCV11, categoryCV12, categoryCV13, categoryCV14;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_menu);

        //ASSIGN VARIABLES TO ID's
        categoryCV1 = findViewById(R.id.ID_buttonAlertness);
        categoryCV2 = findViewById(R.id.ID_buttonAttitude);
        categoryCV3 = findViewById(R.id.ID_buttonDocuments);
        categoryCV4 = findViewById(R.id.ID_buttonHazAware);
        categoryCV5 = findViewById(R.id.ID_buttonIncidents);
        categoryCV6 = findViewById(R.id.ID_buttonMotorway);
        categoryCV7 = findViewById(R.id.ID_buttonOtherVehicles);
        categoryCV8 = findViewById(R.id.ID_buttonRoadSigns);
        categoryCV9 = findViewById(R.id.ID_buttonRulesOfRoad);
        categoryCV10 = findViewById(R.id.ID_buttonSafetyAndYourVehicle);
        categoryCV11 = findViewById(R.id.ID_buttonSafetyMargins);
        categoryCV12 = findViewById(R.id.ID_buttonVehicleHandling);
        categoryCV13 = findViewById(R.id.ID_buttonVehicleLoading);
        categoryCV14 = findViewById(R.id.ID_buttonVulnerableRoadUsers);
        ImageView backButtonIcon = (ImageView) findViewById(R.id.ID_returnButton);


        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        String username = intent.getStringExtra("username_key");


        //Button Listeners
        categoryCV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent category = new Intent(getApplicationContext(), ActivityPracticeExam.class);
                category.putExtra("username_key",username);
                category.putExtra("category_key","Alertness");
                startActivity(category);
                finish();
            }
        });

        categoryCV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent category = new Intent(getApplicationContext(), ActivityPracticeExam.class);
                category.putExtra("username_key",username);
                category.putExtra("category_key","Attitude");
                startActivity(category);
                finish();
            }
        });

        categoryCV3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent category = new Intent(getApplicationContext(), ActivityPracticeExam.class);
                category.putExtra("username_key",username);
                category.putExtra("category_key","Documents");
                startActivity(category);
                finish();
            }
        });

        categoryCV4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent category = new Intent(getApplicationContext(), ActivityPracticeExam.class);
                category.putExtra("username_key",username);
                category.putExtra("category_key","Hazard awareness");
                startActivity(category);
                finish();
            }
        });


        categoryCV5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent category = new Intent(getApplicationContext(), ActivityPracticeExam.class);
                category.putExtra("username_key",username);
                category.putExtra("category_key","Incidents");
                startActivity(category);
                finish();
            }
        });

        categoryCV6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent category = new Intent(getApplicationContext(), ActivityPracticeExam.class);
                category.putExtra("username_key",username);
                category.putExtra("category_key","Motorway rules");
                startActivity(category);
                finish();
            }
        });

        categoryCV7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent category = new Intent(getApplicationContext(), ActivityPracticeExam.class);
                category.putExtra("username_key",username);
                category.putExtra("category_key","Other types of vehicle");
                startActivity(category);
                finish();
            }
        });

        categoryCV8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent category = new Intent(getApplicationContext(), ActivityPracticeExam.class);
                category.putExtra("username_key",username);
                category.putExtra("category_key","Road and traffic signs");
                startActivity(category);
                finish();
            }
        });

        categoryCV9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent category = new Intent(getApplicationContext(), ActivityPracticeExam.class);
                category.putExtra("username_key",username);
                category.putExtra("category_key","Rules of the road");
                startActivity(category);
                finish();
            }
        });

        categoryCV10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent category = new Intent(getApplicationContext(), ActivityPracticeExam.class);
                category.putExtra("username_key",username);
                category.putExtra("category_key","Safety and your vehicle");
                startActivity(category);
                finish();
            }
        });

        categoryCV11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent category = new Intent(getApplicationContext(), ActivityPracticeExam.class);
                category.putExtra("username_key",username);
                category.putExtra("category_key","Safety margins");
                startActivity(category);
                finish();
            }
        });

        categoryCV12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent category = new Intent(getApplicationContext(), ActivityPracticeExam.class);
                category.putExtra("username_key",username);
                category.putExtra("category_key","Vehicle handling");
                startActivity(category);
                finish();
            }
        });

        categoryCV13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent category = new Intent(getApplicationContext(), ActivityPracticeExam.class);
                category.putExtra("username_key",username);
                category.putExtra("category_key","Vehicle loading");
                startActivity(category);
                finish();
            }
        });

        categoryCV14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent category = new Intent(getApplicationContext(), ActivityPracticeExam.class);
                category.putExtra("username_key",username);
                category.putExtra("category_key","Vulnerable road users");
                startActivity(category);
                finish();
            }
        });


        backButtonIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnMenu = new Intent(getApplicationContext(), ActivityLearnToDriveMenu.class);
                returnMenu.putExtra("username_key",username);
                finish();
                startActivity(returnMenu);
            }
        });

    }

}
