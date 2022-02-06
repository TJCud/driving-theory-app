package com.example.drivingtheoryapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

public class ActivityPracticeTest extends AppCompatActivity {

    private QuestionModel currentQuestion;
    private List<QuestionModel> questionList;
    private TextView tvQuestion, tvQuestionNo, tvTimer, tvExitTest;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button btnNext;
    private ImageView questionImage;
    private ImageView timerImage;
    private String imageID;
    private int totalQuestions;
    private int qCounter;
    private int score;
    private boolean answered;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test);

        //ASSIGN VARIABLES TO ID's
        questionList = new ArrayList<>();
        tvQuestion = findViewById(R.id.tvQuestion);
        tvQuestionNo = findViewById(R.id.tvQuestionNumber);
        tvTimer = findViewById(R.id.tvTimer);
        tvTimer.setText("00:00");
        tvTimer.setVisibility(View.INVISIBLE);
        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        questionImage = findViewById(R.id.ID_questionImage);
//        timerImage = findViewById(R.id.ID_timerImage);
//        timerImage.setVisibility(View.INVISIBLE);
        btnNext = findViewById(R.id.btnNext);
        tvExitTest = findViewById(R.id.tvExitTest);

        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        String username = intent.getStringExtra("username_key");
        String category = intent.getStringExtra("category_key");

       // timer(username); //Begin Timer
        TestDbHelper dbHelper = new TestDbHelper(this); //Initialise database
        questionList = dbHelper.getCategoryQuestions(category); //Loads questions into list
        Collections.shuffle(questionList); //Shuffles question order
        totalQuestions = questionList.size(); //Displays number of questions

        showNextQuestion(username);

        //WHEN BUTTON IS CLICKED, CHECK TO SEE AN ANSWER HAS BEEN SELECTED
        btnNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (answered == false){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                        checkAnswer(username);
                    } else {
                        Toast.makeText(ActivityPracticeTest.this, "Please select an answer", Toast.LENGTH_SHORT).show();}

                } else {
                    showNextQuestion(username);
                }
            }
        });



        //EXIT BUTTON ACTIONS
        tvExitTest.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityPracticeMenu.class);
                intent.putExtra("username_key",username);
                startActivity(intent);
                finish();

            }
        });



    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showNextQuestion(String passUsername) {

        rb1.setBackgroundResource(R.drawable.checkbox_background);
        rb2.setBackgroundResource(R.drawable.checkbox_background);
        rb3.setBackgroundResource(R.drawable.checkbox_background);
        rb4.setBackgroundResource(R.drawable.checkbox_background);
        radioGroup.clearCheck();

        if(qCounter < totalQuestions){
            currentQuestion = questionList.get(qCounter);

            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            //CODE FOR LOADING IMAGE
            imageID = currentQuestion.getImageID();
            String uri = "@drawable/question_image_"+imageID;
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable drawable = getResources().getDrawable(imageResource);
            questionImage.setImageDrawable(drawable);

            qCounter++;
            btnNext.setText("Confirm Answer");
            tvQuestionNo.setText("Question "+qCounter+" of "+totalQuestions + ":");
            answered = false;

        } else {

            finishTest(passUsername);
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkAnswer(String passUsername) {

        answered = true;
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo = radioGroup.indexOfChild(rbSelected) +1;


        //ADDING TO SCORE IF ANSWER IS CORRECT
        if(answerNo == currentQuestion.getAnswerNr()){
            score++;
            Toast.makeText(ActivityPracticeTest.this, "Correct answer", Toast.LENGTH_SHORT).show();
        }
        else { Toast.makeText(ActivityPracticeTest.this, "Incorrect answer", Toast.LENGTH_SHORT).show(); }



        //SHOW SOLUTION
        showSolution(passUsername);

    }




    //NAVIGATES TO RESULT SCREEN
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void finishTest(String passUsername){

        //Assign current date and time to string
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("'Test Date: 'MMMM dd yyyy ' Test Time: ' hh:mm a");
        String dateToStr = format.format(today);

        //Saves results and date/time to database
        if (passUsername.equals("Guest")){
            Intent intent = new Intent(this, MockTestResults.class);
            intent.putExtra("TOTAL_QUESTIONS", totalQuestions);
            intent.putExtra("SCORE", score);
            intent.putExtra("username_key",passUsername);
            startActivity(intent);
            finish();


        }
        else{

            Intent intent = new Intent(this, MockTestResults.class);
            intent.putExtra("TOTAL_QUESTIONS", totalQuestions);
            intent.putExtra("SCORE", score);
            intent.putExtra("username_key",passUsername);
            startActivity(intent);
            finish();
        }
    }


    //END TEST TO PREVENT CHEATING (LOOKING UP ANSWERS ON INTERNET APPLICATION ETC)
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showSolution(String passUsername) {

        rb1.setBackgroundColor(Color.parseColor("#ff5757"));
        rb2.setBackgroundColor(Color.parseColor("#ff5757"));
        rb3.setBackgroundColor(Color.parseColor("#ff5757"));
        rb4.setBackgroundColor(Color.parseColor("#ff5757"));


        switch (currentQuestion.getAnswerNr()) {
            case 1:
                rb1.setBackgroundColor(Color.parseColor("#57ff81"));
                break;
            case 2:
                rb2.setBackgroundColor(Color.parseColor("#57ff81"));
                break;
            case 3:
                rb3.setBackgroundColor(Color.parseColor("#57ff81"));
                break;
            case 4:
                rb4.setBackgroundColor(Color.parseColor("#57ff81"));
                break;
        }

        if(qCounter < totalQuestions){
            btnNext.setText("Next Question");
        } else{
            btnNext.setText("FINISH TEST");
            btnNext.setBackgroundColor(Color.parseColor("#00ff44"));
        }
    }



}
