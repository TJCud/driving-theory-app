package com.example.drivingtheoryapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ActivityPracticeTest extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {

    private QuestionModel currentQuestion;
    private List<QuestionModel> questionList;
    private TextView tvQuestion, tvQuestionNo, tvTimer, tvExitTest,tvAnswerWarning;;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button btnNext;
    private Button btnExplanation;
    private ImageView questionImage;
    private ImageView ttsImage;
    private String imageID;
    private String questionExplanation;
    private int totalQuestions;
    private int qCounter;
    private int score;
    private boolean answered;
    private TextToSpeech mTTS;


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
        btnNext = findViewById(R.id.btnNext);
        btnExplanation = findViewById(R.id.btnExplanationOrPrevQuestion);
        tvExitTest = findViewById(R.id.tvExitTest);
        tvAnswerWarning = findViewById(R.id.tvAnswerWarning);
        ttsImage = findViewById(R.id.ivTTSicon);


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



        //TEXT TO SPEECH INITIALISATION
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        ttsImage.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });


        //TEXT TO SPEECH LISTENER
        ttsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        //RADIO BUTTON LISTENERS
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(username);
            }
        });

        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(username);
            }
        });

        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(username);
            }
        });

        rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(username);
            }
        });


        //WHEN BUTTON IS CLICKED, CHECK TO SEE AN ANSWER HAS BEEN SELECTED
        btnNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (answered == false){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                        showNextQuestion(username);
                    } else {
                        tvAnswerWarning.setVisibility(View.VISIBLE);}
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




        //EXPLANATION BUTTON ACTIONS AND LISTENER
        btnExplanation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answered == false){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                        openDialog(username,questionExplanation,"Explanation", "OK","");
                    } else {
                        tvAnswerWarning.setVisibility(View.VISIBLE);}

                } else {
                    tvAnswerWarning.setVisibility(View.GONE);
                    openDialog(username,questionExplanation,"Explanation", "OK","");
                }
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
        tvAnswerWarning.setVisibility(View.GONE); //CLEARS ANSWER WARNING (IF VISIBLE)

        if(qCounter < totalQuestions){
            currentQuestion = questionList.get(qCounter);
            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            //CODE FOR RETRIEVING IMAGE
            imageID = currentQuestion.getImageID();
            String uri = "@drawable/question_image_"+imageID;
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable drawable = getResources().getDrawable(imageResource);
            questionImage.setImageDrawable(drawable);

            //CODE FOR RETRIEVING EXPLANATION
            questionExplanation = currentQuestion.getExplanation();

            qCounter++;
            btnNext.setText("Next Question");
            btnNext.setBackgroundColor(Color.parseColor("#808080"));
            btnExplanation.setBackgroundColor(Color.parseColor("#808080"));
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
        btnNext.setBackgroundColor(Color.parseColor("#4287f5"));
        btnExplanation.setBackgroundColor(Color.parseColor("#4287f5"));

        //ADDING TO SCORE IF ANSWER IS CORRECT
        if(answerNo == currentQuestion.getAnswerNr()){
            score++;
        }

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

    //TEXT TO SPEECH FUNCTION
    private void speak() {
        String question = tvQuestion.getText().toString();
        String answer1 = rb1.getText().toString();
        String answer2 = rb2.getText().toString();
        String answer3 = rb3.getText().toString();
        String answer4 = rb4.getText().toString();

        mTTS.setPitch(1);
        mTTS.setSpeechRate(1);

        mTTS.speak(question, TextToSpeech.QUEUE_ADD, null,null);
        mTTS.playSilentUtterance(500, TextToSpeech.QUEUE_ADD,null);
        mTTS.speak(answer1, TextToSpeech.QUEUE_ADD, null,null);
        mTTS.playSilentUtterance(500, TextToSpeech.QUEUE_ADD,null);
        mTTS.speak(answer2, TextToSpeech.QUEUE_ADD, null,null);
        mTTS.playSilentUtterance(500, TextToSpeech.QUEUE_ADD,null);
        mTTS.speak(answer3, TextToSpeech.QUEUE_ADD, null,null);
        mTTS.playSilentUtterance(500, TextToSpeech.QUEUE_ADD,null);
        mTTS.speak(answer4, TextToSpeech.QUEUE_ADD, null,null);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }


    //OPENING DIALOG
    public void openDialog(String username, String questionExplanation, String title, String positiveButton, String negativeButton) {
        ExampleDialog exampleDialog = new ExampleDialog(username,questionExplanation,title,positiveButton,negativeButton);
        exampleDialog.show(getSupportFragmentManager(), "example dialog");

    }


    //APPLY CHOICE OF DIALOG BOX
    @Override
    public void applyChoice(String username) {

    }
}




