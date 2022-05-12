package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityFullExamResult extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_exam_result);

        //Variables
        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        TextView verdictLabel = (TextView) findViewById(R.id.verdictLabel);
        Button retryBtn = (Button) findViewById(R.id.retrybtn);
        Button menuBtn = (Button) findViewById(R.id.menubtn);
        Button resultsBtn = (Button) findViewById(R.id.resultsbtn);

        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        username = intent.getStringExtra("username_key");
        String category = intent.getStringExtra("category_key");
        String examType = intent.getStringExtra("exam_type_key");
        int examScore = intent.getIntExtra("exam_score_key", 0);
        int examTotalQuestions = intent.getIntExtra("total_questions_key",0);





        //IF EXAM TYPE IS 'PRACTICE', JUST DISPLAY CATEGORY TYPE AND SCORE
        if (examType.equals("practice")) {
            scoreLabel.setText("Category: " + category);
            verdictLabel.setText(examScore + " out of " + examTotalQuestions);
            retryBtn.setText("New Category");


        }
        //IF THE EXAM TYPE IS 'FULL', CALCULATE AND SHOW SCORE
        else{
            //CHECKS IF PASS PERCENTAGE IS ACHIEVED AND DISPLAYS OUTCOME
            if (ExamMethods.getExamOutcome(examScore, examTotalQuestions).equals("PASS")) {
                verdictLabel.setText("Exam Passed.\n Congratulations!");
            } else {
                verdictLabel.setText("Exam Failed.\n Please try again.");
            }
            scoreLabel.setText(examScore + " out of " + examTotalQuestions + "\n" + "Accuracy " + ExamMethods.getScorePercentage(examScore,examTotalQuestions) + "%" + "\n" + "(86% or higher required)");


        }


        //Button Listeners
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent; //Initialise intent

                //Depending on which type of exam the user conducted, go back to full exam or practice menu
                if(examType.equals("practice")){
                    intent = new Intent(ActivityFullExamResult.this, ActivityPracticeMenu.class);
                }
                else {
                    intent = new Intent(ActivityFullExamResult.this, ActivityFullExam.class);
                }
                intent.putExtra("username_key",username); //Pass username to next activity
                startActivity(intent); //Start next activity
                finish(); //Close current activity
            }
        });



        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;

                //If user is a guest, return to Learn To Drive Menu
                if (username.equals("guest")){
                    intent = new Intent(ActivityFullExamResult.this, ActivityLearnToDriveMenu.class);
                }
                //Return to main menu if user is registered
                else{
                    intent = new Intent(ActivityFullExamResult.this, ActivityMainMenu.class);
                }

                intent.putExtra("username_key",username);
                startActivity(intent);
                finish();
            }
        });



        resultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //If user is a guest, disable results button
                if (username.equals("guest")){
                    Toast.makeText(getApplicationContext(),"Guest results are not save. Please sign in or create an account.",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(ActivityFullExamResult.this, ActivityResultsAll.class);
                    intent.putExtra("username_key", username);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }



    //On back press, return to Learn to Drive activity
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ActivityFullExamResult.this, ActivityLearnToDriveMenu.class);
        intent.putExtra("username_key",username);
        startActivity(intent);
        finish();
    }




}