package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MockTest extends AppCompatActivity {

    //VARIABLES

    private QuestionModel currentQuestion;
    private List<QuestionModel> questionsList;

    private TextView tvQuestion, tvQuestionNo, tvTimer;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button btnNext;

    int totalQuestions;
    int qCounter = 0;
    int score = 0;

    ColorStateList dfRbColor;
    boolean answered;
    CountDownTimer countDownTimer;




    //WHEN MOCK TEST STARTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test);

        //LOAD QUESTIONS AND ANSWERS
        questionsList = new ArrayList<>();
        tvQuestion = findViewById(R.id.textQuestion);
        tvQuestionNo = findViewById(R.id.textQuestionNo);
        tvTimer = findViewById(R.id.textTimer);
        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        btnNext = findViewById(R.id.btnNext);
        dfRbColor = rb1.getTextColors();

        //BEGIN TIMER
        timer();

        //DISPLAY QUESTIONS
        addQuestions();
        totalQuestions = questionsList.size();
        showNextQuestion();

        //WHEN BUTTON IS CLICKED, CHECK TO SEE AN ANSWER HAS BEEN SELECTED
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answered == false){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                        checkAnswer();
                    } else {
                    Toast.makeText(MockTest.this, "Please select an answer", Toast.LENGTH_SHORT).show();}

                } else {
                    showNextQuestion();
                }

            }
        });
    }




    //FUNCTION TO CHECK ANSWER. IF CORRECT ADD ONE TO SCORE. DISPLAY CORRECT ANSWER AS GREEN TEXT AND INCORRECT ANSWERS AS RED TEXT
    private void checkAnswer() {
        answered = true;
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo = radioGroup.indexOfChild(rbSelected) +1;
        if(answerNo == currentQuestion.getCorrectAnsNo()){
        score++;
        }


        if(qCounter < totalQuestions){
            showNextQuestion();

        } else{
            countDownTimer.cancel();
            btnNext.setText("See results");
            btnNext.setBackgroundColor(Color.parseColor("#00ff44"));
        }
    }



    //FUNCTION THAT CHECKS TO SEE IF QUESTION COUNTER HAS ELAPSED NUMBER OF QUESTIONS. IF IT HAS THEN END THE TEST.
    // FUNCTION ALSO RESETS RADIO BUTTON COLOURS.
    private void showNextQuestion() {

        radioGroup.clearCheck();
        rb1.setTextColor(dfRbColor);
        rb2.setTextColor(dfRbColor);
        rb3.setTextColor(dfRbColor);
        rb4.setTextColor(dfRbColor);

        if(qCounter < totalQuestions){
        currentQuestion = questionsList.get(qCounter);
        tvQuestion.setText(currentQuestion.getQuestion());
        rb1.setText(currentQuestion.getOption1());
        rb2.setText(currentQuestion.getOption2());
        rb3.setText(currentQuestion.getOption3());
        rb4.setText(currentQuestion.getOption4());

        qCounter++;
        btnNext.setText("Submit");
        tvQuestionNo.setText("Question: "+qCounter+"/"+totalQuestions);
        answered = false;

        } else {

            finishTest();
        }
    }



//TIMER FUNCTION
    private void timer() {
        countDownTimer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(""+String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                Toast.makeText(MockTest.this, "Time up!", Toast.LENGTH_SHORT).show();
                finishTest();
            }
        }.start();
    }

    //NAVIGATES TO RESULT SCREEN
    public void finishTest(){
        Intent intent = new Intent(this, MockTestResults.class);
        intent.putExtra("TOTAL_QUESTIONS", totalQuestions);
        intent.putExtra("SCORE", score);
        startActivity(intent);
    }

//LIST OF QUESTIONS
    private void addQuestions() {

        for (int i = 0; i < 5; i++) {
            questionsList.add(new QuestionModel("What is 1+1?","1","2","3","4",2));
        }


    }
}