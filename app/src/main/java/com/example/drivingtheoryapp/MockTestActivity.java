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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MockTestActivity extends AppCompatActivity {


    private QuestionModel currentQuestion;
    private List<QuestionModel> questionList;

    private TextView tvQuestion, tvQuestionNo, tvTimer;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button btnNext;

    private int totalQuestions;
    private int qCounter;
    private int score;
    private boolean answered;

    private ColorStateList dfRbColor;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test);

        //ASSIGN VARIABLES TO ID's
        questionList = new ArrayList<>();
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


        timer(); //Begin Timer
        TestDbHelper dbHelper = new TestDbHelper(this); //Initialise database
        questionList = dbHelper.getAllQuestions(); //Loads questions into list
        totalQuestions = questionList.size(); //Displays number of questions
        Collections.shuffle(questionList); //Shuffles question order

        showNextQuestion();

        //WHEN BUTTON IS CLICKED, CHECK TO SEE AN ANSWER HAS BEEN SELECTED
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answered == false){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                        checkAnswer();
                    } else {
                        Toast.makeText(MockTestActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();}

                } else {
                    showNextQuestion();
                }

            }
        });
    }


    private void showNextQuestion() {

        radioGroup.clearCheck();


        if(qCounter < totalQuestions){
            currentQuestion = questionList.get(qCounter);

            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            qCounter++;
            btnNext.setText("Submit Answer");
            tvQuestionNo.setText("Question "+qCounter+" of "+totalQuestions + ":");
            answered = false;

        } else {

            finishTest();
        }
    }


    private void checkAnswer() {
        answered = true;
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo = radioGroup.indexOfChild(rbSelected) +1;
        if(answerNo == currentQuestion.getAnswerNr()){
            score++;
        }


        if(qCounter < totalQuestions){
            showNextQuestion();

        } else{
            countDownTimer.cancel();
            tvQuestionNo.setVisibility(View.GONE);
            tvQuestion.setVisibility(View.GONE);
            rb1.setVisibility(View.GONE);
            rb2.setVisibility(View.GONE);
            rb3.setVisibility(View.GONE);
            rb4.setVisibility(View.GONE);
            btnNext.setText("See results");
            btnNext.setBackgroundColor(Color.parseColor("#00ff44"));
        }
    }



    //TIMER FUNCTION
    private void timer() {
        countDownTimer = new CountDownTimer(3421000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(""+String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                Toast.makeText(MockTestActivity.this, "Time up!", Toast.LENGTH_SHORT).show();

            }
        }.start();
    }


    //NAVIGATES TO RESULT SCREEN
    public void finishTest(){
        Intent intent = new Intent(this, MockTestResults.class);
        intent.putExtra("TOTAL_QUESTIONS", totalQuestions);
        intent.putExtra("SCORE", score);
        startActivity(intent);
        finish();
    }

}

