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
import android.speech.tts.TextToSpeech;

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

    //TXT2SPETEST
    TextToSpeech t1;
    EditText ed1;
    Button b1;




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

        questionsList.add(new QuestionModel("When would you use the right-hand lane on a three-lane motorway?",
                    "When you're turning right",
                    "When you're overtaking",
                    "When you're travelling above the speed limit",
                    "When you're trying to save fuel",
                    2));

        questionsList.add(new QuestionModel("You wish to turn right ahead. Why should you take up the correct position in good time?",
                    "To allow other drivers to pull out in front of you",
                    "To give a better view into the road that you're joining",
                    "To help other road users know what you intend to do",
                    "To allow drivers to pass you on the right",
                    3));

        questionsList.add(new QuestionModel("You take some cough medicine given to you by a friend. What should you do before driving your car?",
                    "Ask your friend if taking the medicine affected their driving",
                    "Drink some strong coffee one hour before driving",
                    "Check the label to see if the medicine will affect your driving",
                    "Drive a short distance to see if the medicine is affecting your driving",
                    3));

        questionsList.add(new QuestionModel("What should you do when you move off from behind a parked car?",
                    "Give a signal after moving off",
                    "Look around before moving off",
                    "Look around after moving off",
                    "Use the exterior mirrors only",
                    2));

        questionsList.add(new QuestionModel("Following a collision, a person has been injured. What would be a warning sign for shock?",
                "Flushed complexion",
                "Warm dry skin",
                "Slow pulse",
                "Rapid shallow breathing",
                4));

        questionsList.add(new QuestionModel("At an incident, someone is suffering from severe burns. How could you help them?",
                "Apply lotions to the injury",
                "Burst any blisters",
                "Remove anything sticking to the burns",
                "Douse the burns with clean, cool water",
                4));

        questionsList.add(new QuestionModel("You're following two cyclists as they approach a roundabout in the left-hand lane. Where would you expect the cyclists to go?",
                "Left",
                "Right",
                "Straight ahead",
                "Any direction",
                4));

        questionsList.add(new QuestionModel("What should you do when you're passing loose sheep on the road?",
                "Briefly sound your horn",
                "Go very slowly",
                "Pass quickly but quietly",
                "Herd them to the side of the road",
                2));

        questionsList.add(new QuestionModel("You're approaching a red light at a puffin crossing. Pedestrians are on the crossing. When will the red light change?",
                "When you start to edge forward onto the crossing",
                "When the pedestrians have cleared the crossing",
                "When the pedestrians push the button on the far side of the crossing",
                "When a driver from the opposite direction reaches the crossing",
                2));

        questionsList.add(new QuestionModel("Why is it important to make full use of the slip road as you join a motorway?",
                "Because there's space available to turn round if you need to",
                "To allow you direct access to the overtaking lanes",
                "To allow you to fit safely into the traffic flow in the left-hand lane",
                "Because you can continue on the hard shoulder",
                3));

        questionsList.add(new QuestionModel("You're driving towards a zebra crossing. What should you do if a person in a wheelchair is waiting to cross?",
                "Continue on your way",
                "Wave to the person to cross",
                "Wave to the person to wait",
                "Be prepared to stop",
                4));

        questionsList.add(new QuestionModel("Why should you allow extra room while overtaking a motorcyclist on a windy day?",
                "The rider may turn off suddenly to get out of the wind",
                "The rider may be blown in front of you",
                "The rider may stop suddenly",
                "The rider may be travelling faster than normal",
                2));

        questionsList.add(new QuestionModel("What's the main benefit of driving a four-wheel-drive vehicle?",
                "Improved grip on the road",
                "Lower fuel consumption",
                "Shorter stopping distances",
                "Improved passenger comfort",
                1));

        questionsList.add(new QuestionModel("What should you do if you think the driver of the vehicle in front has forgotten to cancel their right indicator?",
                "Flash your lights to alert the driver",
                "Sound your horn before overtaking",
                "Overtake on the left if there's room",
                "Stay behind and don't overtake",
                4));

        questionsList.add(new QuestionModel("You're approaching a roundabout. What should you do if a cyclist ahead is signalling to turn right?",
                "Overtake on the right",
                "Give a warning with your horn",
                "Signal the cyclist to move across",
                "Give the cyclist plenty of room",
                4));

        questionsList.add(new QuestionModel("What should you do when you leave your car unattended for a few minutes?",
                "Leave the engine running",
                "Switch the engine off but leave the key in",
                "Lock it and remove the key",
                "Park near a traffic warden",
                3));

        questionsList.add(new QuestionModel("Why is it dangerous to travel too close to the vehicle ahead?",
                "Your engine will overheat",
                "Your mirrors will need adjusting",
                "Your view of the road ahead will be restricted",
                "Your satnav will be confused",
                3));

        questionsList.add(new QuestionModel("You wish to tow a trailer. Where would you find the maximum noseweight for your vehicle’s tow hitch?",
                "In the vehicle handbook",
                "In The Highway Code",
                "In your vehicle registration certificate",
                "In your licence documents",
                1));

        questionsList.add(new QuestionModel("How should you dispose of a used vehicle battery?",
                "Bury it in your garden",
                "Put it in the dustbin",
                "Take it to a local-authority disposal site",
                "Leave it on waste land",
                1));

        questionsList.add(new QuestionModel("You're approaching traffic lights and the red light is showing. What signal will show next?",
                "Red and amber",
                "Green alone",
                "Amber alone",
                "Green and amber",
                1));



    }
}