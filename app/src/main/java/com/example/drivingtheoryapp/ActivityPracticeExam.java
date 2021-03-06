package com.example.drivingtheoryapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ActivityPracticeExam extends AppCompatActivity implements DialogPositiveNegative.ExampleDialogListener {

    private QuestionModel currentQuestion;
    private TextView tvQuestionWithImage,tvQuestionWithoutImage, tvQuestionNo, tvTimer, tvExitTest,tvAnswerWarning;;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button btnNext, btnExplanation;
    private ImageView questionImage, ttsImage;
    private String imageID, questionExplanation, selectedCategory, username;
    private int totalQuestions, qCounter, score;
    private boolean answered;
    private TextToSpeech mTTS;
    private ProgressBar pbProgressBar;
    private List<QuestionModel> questionListFromRemote;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pratice_exam);


        //ASSIGN VARIABLES TO ID's
        tvQuestionWithImage = findViewById(R.id.tvQuestionWithImage);
        tvQuestionWithoutImage = findViewById(R.id.tvQuestionWithoutImage);
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
        btnExplanation = findViewById(R.id.btnExplanation);
        tvExitTest = findViewById(R.id.tvExitTest);
        tvAnswerWarning = findViewById(R.id.tvAnswerWarning);
        ttsImage = findViewById(R.id.ivTTSicon);
        pbProgressBar = findViewById(R.id.pbProgressBar);
        pbProgressBar.setVisibility(View.GONE);
        questionListFromRemote = new ArrayList<>();


        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        username = intent.getStringExtra("username_key");
        selectedCategory = intent.getStringExtra("category_key");

        //Getting JSON data from splash screen via SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String fetchedQuestionJSON = sharedPreferences.getString("questionJSON_key","");

        //GET QUESTIONS FROM REMOTE DB
        parseJSONtoQuestionModel(fetchedQuestionJSON,selectedCategory);




        Collections.shuffle(questionListFromRemote); //Shuffle remote question order
        totalQuestions = questionListFromRemote.size(); //Displays number of questions

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
            currentQuestion = questionListFromRemote.get(qCounter);
            tvQuestionWithImage.setText(currentQuestion.getQuestion());
            tvQuestionWithoutImage.setText(currentQuestion.getQuestion());
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


            //CODE IF QUESTION DOES NOT INCLUDE IMAGE
            if(imageID.equals("default")){
                questionImage.setVisibility(View.INVISIBLE);
                tvQuestionWithImage.setVisibility(View.INVISIBLE);
                tvQuestionWithoutImage.setVisibility(View.VISIBLE);

            }
            else {questionImage.setVisibility(View.VISIBLE);
                tvQuestionWithImage.setVisibility(View.VISIBLE);
                tvQuestionWithoutImage.setVisibility(View.INVISIBLE);}


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


        Intent intent = new Intent(this, ActivityFullExamResult.class);
        intent.putExtra("total_questions_key", totalQuestions);
        intent.putExtra("exam_score_key", score);
        intent.putExtra("username_key",passUsername);
        intent.putExtra("exam_type_key","practice");
        intent.putExtra("category_key",selectedCategory);
        startActivity(intent);
        finish();

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
        String question = tvQuestionWithImage.getText().toString();
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
        DialogPositiveNegative dialogPositiveNegative = new DialogPositiveNegative(username,questionExplanation,title,positiveButton,negativeButton);
        dialogPositiveNegative.show(getSupportFragmentManager(), "example dialog");

    }


    //APPLY CHOICE OF DIALOG BOX
    @Override
    public void applyChoice(String username) {

    }


    //PARSES JSON OF QUESTIONS AND ANSWERS AND STORES INTO QUESTIONMODEL
    public void parseJSONtoQuestionModel(String fetchedQuestionJson, String selectedCategory){


        try {

            JSONObject questionJsonObject = new JSONObject(fetchedQuestionJson); //Initialise JSON Object, passing in JSON data
            JSONArray questionJsonArray = questionJsonObject.getJSONArray("getAllQuestions"); //Assign JSON Object into JSON Array


            //Fill QuestionList until reaches end of array
            for (int i = 0; i<questionJsonArray.length(); ++i) {

                JSONObject questionObj = questionJsonArray.getJSONObject(i); //Pass values from array into new object

                QuestionModel questionModel = new QuestionModel(); //New QuestionModel


                //Set up question model from JSONObject elements
                questionModel.setID(questionObj.getInt("id"));
                questionModel.setCategory(questionObj.getString("category"));
                questionModel.setQuestion(questionObj.getString("question"));
                questionModel.setOption1(questionObj.getString("option1"));
                questionModel.setOption2(questionObj.getString("option2"));
                questionModel.setOption3(questionObj.getString("option3"));
                questionModel.setOption4(questionObj.getString("option4"));
                questionModel.setAnswerNr(questionObj.getInt("answer"));
                questionModel.setImageID(questionObj.getString("image"));
                questionModel.setExplanation(questionObj.getString("explanation"));

                //ONLY ADD TO QUESTION LIST IF CATEGORY EQUALS SELECTED CATEGORY
                if (questionModel.getCategory().equals(selectedCategory)){
                questionListFromRemote.add(questionModel);}

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //On back press, return to practice menu
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ActivityPracticeExam.this, ActivityPracticeMenu.class);
        intent.putExtra("username_key",username);
        startActivity(intent);
        finish();
    }


}




