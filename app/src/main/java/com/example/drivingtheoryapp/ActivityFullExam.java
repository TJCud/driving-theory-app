package com.example.drivingtheoryapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.speech.tts.TextToSpeech;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import com.google.gson.Gson;

public class ActivityFullExam extends AppCompatActivity implements DialogPositiveNegative.ExampleDialogListener {


    public static List<String> saveQuestionList;
    private QuestionModel currentQuestion;
    private List<QuestionModel> questionList;
    private TextView tvQuestionWithImage,tvQuestionWithoutImage, tvQuestionNo, tvTimer, tvExitTest, tvAnswerWarning;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button btnNext;
    private ImageView questionImage,ttsImage;
    private String username;
    private String saveQuestionListString;
    private int totalQuestions, qCounter, score;
    private boolean answered;
    private CountDownTimer countDownTimer;
    private TextToSpeech mTTS;
    private ProgressBar pbProgressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_exam);

        //Initialising array lists
        questionList = new ArrayList<>();
        saveQuestionList = new ArrayList<>();

        //Assigning on screen text view objects
        tvQuestionWithImage = findViewById(R.id.tvQuestionWithImage);
        tvQuestionWithoutImage = findViewById(R.id.tvQuestionWithoutImage);
        tvQuestionNo = findViewById(R.id.tvQuestionNumber);
        tvTimer = findViewById(R.id.tvTimer);
        tvExitTest = findViewById(R.id.tvExitTest);
        //Assigning on screen radio group and button object
        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        //Assigning on screen image objects
        questionImage = findViewById(R.id.ID_questionImage);
        ttsImage = findViewById(R.id.ivTTSicon);
        //Assigning on screen button objects
        btnNext = findViewById(R.id.btnNext);
        //Assigning and hiding progress bar
        pbProgressBar = findViewById(R.id.pbProgressBar);
        pbProgressBar.setVisibility(View.GONE);
        //Assigning and hiding unanswered question warning
        tvAnswerWarning = findViewById(R.id.tvAnswerWarning);
        tvAnswerWarning.setVisibility(View.GONE);



        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        username = intent.getStringExtra("username_key");


        //Getting JSON data from splash screen via SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String fetchedQuestionJSON = sharedPreferences.getString("questionJSON_key","");
        boolean connected = sharedPreferences.getBoolean("connected_key",true);




        //IF connected to the database, begin parsing questions
        if(connected){
            parseJSONtoQuestionModel(fetchedQuestionJSON); //Parsing JSON String into Question Model
        }
        //IF offline, initialise SQLite database
        else{
            TestDbHelper dbHelper = new TestDbHelper(this); //Initialise SQLite database
            questionList = dbHelper.getAllQuestions(); //Loads questions into list
        }



        Collections.shuffle(questionList);
        timer(username); //Begin Timer
        totalQuestions = 50; //Displays number of questions
        showNextQuestion(username); //Shows the first question


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
                speak(tvQuestionNo.getText().toString());
            }
        });


        //WHEN BUTTON IS CLICKED, CHECK TO SEE AN ANSWER HAS BEEN SELECTED
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        mTTS.stop();
                        checkAnswer(username);
                    } else {
                        tvAnswerWarning.setVisibility(View.VISIBLE);
                    }
                } else {
                    mTTS.stop();
                    showNextQuestion(username);
                }
            }
        });

        //EXIT TEST LISTENER
        tvExitTest.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                openDialog(username,"Are you sure you void and exit the exam?", "Warning", "Yes", "No");
            }
        });

        //RADIO BUTTON LISTENERS
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRadioButtonColors();
                rb1.setBackgroundColor(Color.parseColor("#06a800"));
                btnNext.setBackgroundColor(Color.parseColor("#4287f5"));
            }
        });

        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRadioButtonColors();
                rb2.setBackgroundColor(Color.parseColor("#06a800"));
                btnNext.setBackgroundColor(Color.parseColor("#4287f5"));
            }
        });

        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRadioButtonColors();
                rb3.setBackgroundColor(Color.parseColor("#06a800"));
                btnNext.setBackgroundColor(Color.parseColor("#4287f5"));
            }
        });

        rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRadioButtonColors();
                rb4.setBackgroundColor(Color.parseColor("#06a800"));
                btnNext.setBackgroundColor(Color.parseColor("#4287f5"));
            }
        });

    }

    // SHOW NEXT QUESTION FUNCTION
    private void showNextQuestion(String passUsername) {

        radioGroup.clearCheck(); //CLEARS SELECTED ANSWER
        clearRadioButtonColors();//CLEARS RADIO BUTTON COLORS
        tvAnswerWarning.setVisibility(View.GONE); //CLEARS ANSWER WARNING (IF VISIBLE)


        if(qCounter < totalQuestions){
            currentQuestion = questionList.get(qCounter);
            tvQuestionWithImage.setText(currentQuestion.getQuestion());
            tvQuestionWithoutImage.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            //CODE FOR LOADING IMAGE
            String imageID = currentQuestion.getImageID();
            String uri = "@drawable/question_image_"+ imageID;
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


            qCounter++; //ADD TO COUNTER
            btnNext.setText("Next Question");//CHANGE BUTTON CONTENTS
            btnNext.setBackgroundColor(Color.parseColor("#808080"));
            tvQuestionNo.setText("Question "+qCounter+" of "+totalQuestions + ":"); //CHANGE QUESTION NUMBER
            answered = false; //SET ANSWERED TO FALSE

        } else {
            finishTest(passUsername); //RUN FINISH TEST FUNCTION IF QUESTION COUNTER EXCEEDS TOTAL QUESTIONS
        }
    }

    //Check answer is correct
    private void checkAnswer(String username) {

        answered = true; //Set

        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo = radioGroup.indexOfChild(rbSelected) +1;
        if(answerNo == currentQuestion.getAnswerNr()){
            score++;
        }


        saveQuestionList.add(String.valueOf(qCounter)); //Adding Question Number to List
        saveQuestionList.add(currentQuestion.getQuestion()); //Adding Current Question to List
        saveQuestionList.add(rbSelected.getText().toString()); //Adding Users Answer to List

        //Adding the correct answer to list
        switch (currentQuestion.getAnswerNr()) {
            case 1:
                saveQuestionList.add(currentQuestion.getOption1());
                break;
            case 2:
                saveQuestionList.add(currentQuestion.getOption2());
                break;
            case 3:
                saveQuestionList.add(currentQuestion.getOption3());
                break;
            case 4:
                saveQuestionList.add(currentQuestion.getOption4());
                break;}


        //Adding correct/incorrect answer string
        if(answerNo == currentQuestion.getAnswerNr()){
            saveQuestionList.add("CORRECT"); }
        else{ saveQuestionList.add("INCORRECT"); }


        //If the question counter is less than the total questions, continue to show next question
        if(qCounter < totalQuestions){
            showNextQuestion(username);

        //If the user has answered all questions
        } else{
            resultToGSON(saveQuestionList); //Saves the Question List to GSON Object
            countDownTimer.cancel(); //Stops the timer
            finishTest(username); //Performs function to finish test and proceed to next activity
        }
    }

    //TIMER FUNCTION
    private void timer(String username) {
        countDownTimer = new CountDownTimer(3421000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(""+String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFinish() {
                Toast.makeText(ActivityFullExam.this, "Time up!", Toast.LENGTH_SHORT).show();
                finishTest(username);

            }
        }.start();
    }


    //Method when exam is finished
    public void finishTest(String username) {


        //MAKES PROGRESS BAR APPEAR, AND OTHER OBJECTS DISAPPEAR
        pbProgressBar.setVisibility(View.VISIBLE);
        tvQuestionWithoutImage.setVisibility(View.VISIBLE);
        tvQuestionWithoutImage.setText("SAVING EXAM RESULTS...");
        tvQuestionWithImage.setVisibility(View.INVISIBLE);
        radioGroup.setVisibility(View.INVISIBLE);
        questionImage.setVisibility(View.INVISIBLE);
        tvExitTest.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        tvAnswerWarning.setVisibility(View.INVISIBLE);
        ttsImage.setVisibility(View.INVISIBLE);


        //If the user is not a guest, continue saving results to database
        if(!username.equals("guest")){

            //Creating array for parameters
            String[] field = new String[6];
            field[0] = "username";
            field[1] = "questions_correct";
            field[2] = "questions_total";
            field[3] = "outcome";
            field[4] = "date";
            field[5] = "saved_question";

            //Creating array for data
            String[] data = new String[6];
            data[0] = username;
            data[1] = String.valueOf(score);
            data[2] = String.valueOf(totalQuestions);
            data[3] = ExamMethods.getExamOutcome(score, totalQuestions);;
            data[4] = ExamMethods.getDate();
            data[5] = saveQuestionListString;

            Log.i("Status", saveQuestionListString);
            PostData postData = new PostData(getResources().getString(R.string.saveTestResults), "POST", field, data);

            if (postData.startPut()) {
                if (postData.onComplete()) {
                    String result = postData.getResult();
                    if (result.equals("success")) {
                        openFullExamResult(username);
                    } else {
                        openFullExamResult(username);
                        Toast.makeText(getApplicationContext(), postData.getResult(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        else{
            openFullExamResult(username);
        }
    }


    //TEXT TO SPEECH FUNCTION
    private void speak(String questionNumber) {
        String question = tvQuestionWithImage.getText().toString();
        String answer1 = rb1.getText().toString();
        String answer2 = rb2.getText().toString();
        String answer3 = rb3.getText().toString();
        String answer4 = rb4.getText().toString();

        //Setting pitch and speech rate
        mTTS.setPitch(1);
        mTTS.setSpeechRate(1);


        //QUESTION NUMBER
        mTTS.speak(questionNumber, TextToSpeech.QUEUE_ADD, null,null);
        mTTS.playSilentUtterance(250, TextToSpeech.QUEUE_ADD,null);
        //QUESTION
        mTTS.speak(question, TextToSpeech.QUEUE_ADD, null,null);
        mTTS.playSilentUtterance(500, TextToSpeech.QUEUE_ADD,null);
        //ANSWER 1
        mTTS.speak("Option 1", TextToSpeech.QUEUE_ADD, null,null);
        mTTS.playSilentUtterance(250, TextToSpeech.QUEUE_ADD,null);
        mTTS.speak(answer1, TextToSpeech.QUEUE_ADD, null,null);
        mTTS.playSilentUtterance(500, TextToSpeech.QUEUE_ADD,null);
        //ANSWER 2
        mTTS.speak("Option 2", TextToSpeech.QUEUE_ADD, null,null);
        mTTS.playSilentUtterance(250, TextToSpeech.QUEUE_ADD,null);
        mTTS.speak(answer2, TextToSpeech.QUEUE_ADD, null,null);
        mTTS.playSilentUtterance(500, TextToSpeech.QUEUE_ADD,null);
        //ANSWER 3
        mTTS.speak("Option 3", TextToSpeech.QUEUE_ADD, null,null);
        mTTS.playSilentUtterance(250, TextToSpeech.QUEUE_ADD,null);
        mTTS.speak(answer3, TextToSpeech.QUEUE_ADD, null,null);
        mTTS.playSilentUtterance(500, TextToSpeech.QUEUE_ADD,null);
        //ANSWER 4
        mTTS.speak("Option 4", TextToSpeech.QUEUE_ADD, null,null);
        mTTS.playSilentUtterance(250, TextToSpeech.QUEUE_ADD,null);
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






    //Method for proceeding to next activity
    public void openFullExamResult(String username) {
        Intent intent = new Intent(this, ActivityFullExamResult.class);
        intent.putExtra("total_questions_key", totalQuestions);
        intent.putExtra("exam_score_key", score);
        intent.putExtra("username_key",username);
        intent.putExtra("exam_type_key","full");
        startActivity(intent);
        finish();
    }


    //Clear radio button colours when next question is shown
    public void clearRadioButtonColors() {
        rb1.setBackgroundColor(Color.parseColor("#4287f5"));
        rb2.setBackgroundColor(Color.parseColor("#4287f5"));
        rb3.setBackgroundColor(Color.parseColor("#4287f5"));
        rb4.setBackgroundColor(Color.parseColor("#4287f5"));
        btnNext.setBackgroundColor(Color.parseColor("#4287f5"));
    }





    //OPENING DIALOG
    public void openDialog(String username, String input, String title, String positiveButton, String negativeButton) {
        DialogPositiveNegative dialogPositiveNegative = new DialogPositiveNegative(username,input,title,positiveButton,negativeButton);
        dialogPositiveNegative.show(getSupportFragmentManager(), "example dialog");
    }


    //APPLY CHOICE OF DIALOG BOX
    @Override
    public void applyChoice(String username) {
        Intent intent = new Intent(getApplicationContext(), ActivityLearnToDriveMenu.class);
        intent.putExtra("username_key", username);
        mTTS.stop();
        startActivity(intent);
        finish();
    }



    //PARSES JSON OF QUESTIONS AND ANSWERS AND STORES INTO QUESTIONMODEL
    public void parseJSONtoQuestionModel(String fetchedQuestionJson){

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

                //Add QuestionModel object to QuestionList
                questionList.add(questionModel);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void resultToGSON(List<String> saveQuestionList){
        Gson gson = new Gson().newBuilder().setPrettyPrinting().create(); //Initialise GSON Object
        saveQuestionListString = gson.toJson(saveQuestionList); //Store Question List into JSON

    }




    //EXIT TEST WARNING ON BACK PRESS
    @Override
    public void onBackPressed()
    {
        openDialog(username,"Are you sure you void and exit the exam?", "Warning", "Yes", "No");
    }


}
