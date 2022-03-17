package com.example.drivingtheoryapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import android.speech.tts.TextToSpeech;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ActivityFullExam extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {


    public static ArrayList<String> saveQuestion= new ArrayList<>();;
    private QuestionModel currentQuestion;
    private List<QuestionModel> questionList;
    private List<QuestionModel> questionListFromRemote;
    private TextView tvQuestionWithImage,tvQuestionWithoutImage, tvQuestionNo, tvTimer, tvExitTest, tvAnswerWarning;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button btnNext,btnPrev,btnFinish;
    private ImageView questionImage;
    private ImageView ttsImage;
    private String imageID, fetchedResult;
    private int totalQuestions, qCounter, score;
    private boolean answered;
    private CountDownTimer countDownTimer;
    private TextToSpeech mTTS;
    private ProgressBar pbProgressBar;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test);

        //ASSIGN VARIABLES TO ID's
        questionList = new ArrayList<>();
        questionListFromRemote = new ArrayList<>();
        tvQuestionWithImage = findViewById(R.id.tvQuestionWithImage);
        tvQuestionWithoutImage = findViewById(R.id.tvQuestionWithoutImage);
        tvQuestionNo = findViewById(R.id.tvQuestionNumber);
        tvTimer = findViewById(R.id.tvTimer);
        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        questionImage = findViewById(R.id.ID_questionImage);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnExplanationOrPrevQuestion);
        btnFinish = findViewById(R.id.btnFinish);
        tvExitTest = findViewById(R.id.tvExitTest);
        tvAnswerWarning = findViewById(R.id.tvAnswerWarning);
        tvAnswerWarning.setVisibility(View.GONE);
        ttsImage = findViewById(R.id.ivTTSicon);
        pbProgressBar = findViewById(R.id.pbProgressBar);
        pbProgressBar.setVisibility(View.GONE);


        //GET QUESTIONS FROM REMOTE DB
        getQuestions();


        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        String username = intent.getStringExtra("username_key");



        timer(username); //Begin Timer
        TestDbHelper dbHelper = new TestDbHelper(this); //Initialise database
        questionList = dbHelper.getAllQuestions(); //Loads questions into list
        Collections.shuffle(questionList); //Shuffles sqlite question order
        Collections.shuffle(questionListFromRemote); //Shuffle remote question order
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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (answered == false) {
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
    }




    // SHOW NEXT QUESTION FUNCTION
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showNextQuestion(String passUsername) {
        radioGroup.clearCheck(); //CLEARS SELECTED ANSWER
        tvAnswerWarning.setVisibility(View.GONE); //CLEARS ANSWER WARNING (IF VISIBLE)

        if(qCounter < totalQuestions){
            currentQuestion = questionListFromRemote.get(qCounter);
            tvQuestionWithImage.setText(currentQuestion.getQuestion());
            tvQuestionWithoutImage.setText(currentQuestion.getQuestion());
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
            btnPrev.setText("Previous Question");
            tvQuestionNo.setText("Question "+qCounter+" of "+totalQuestions + ":"); //CHANGE QUESTION NUMBER
            answered = false; //SET ANSWERED TO FALSE

        } else {
            finishTest(passUsername); //RUN FINISH TEST FUNCTION IF QUESTION COUNTER EXCEEDS TOTAL QUESTIONS
        }
    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkAnswer(String passUsername) {

        answered = true;
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());

        int answerNo = radioGroup.indexOfChild(rbSelected) +1;
        if(answerNo == currentQuestion.getAnswerNr()){
            score++;
        }




        saveQuestion.add("Question "+ qCounter +": " + currentQuestion.getQuestion().replace("[", "").replace("]", ""));
        saveQuestion.add("\nYour answer: " + rbSelected.getText().toString().replace("[", "").replace("]", ""));

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                saveQuestion.add("\nCorrect answer: " + currentQuestion.getOption1().replace("[", "").replace("]", ""));
                break;
            case 2:
                saveQuestion.add("\nCorrect answer: " + currentQuestion.getOption2().replace("[", "").replace("]", ""));
                break;
            case 3:
                saveQuestion.add("\nCorrect answer: " + currentQuestion.getOption3().replace("[", "").replace("]", ""));
                break;
            case 4:
                saveQuestion.add("\nCorrect answer: " + currentQuestion.getOption4().replace("[", "").replace("]", ""));
                break;}


        if(answerNo == currentQuestion.getAnswerNr()){
            saveQuestion.add("\nCORRECT!\n\n".replace("[", "").replace("]", ""));
        }
        else{
            saveQuestion.add("\nINCORRECT!\n\n".replace("[", "").replace("]", ""));
        }


        if(qCounter < totalQuestions){
            showNextQuestion(passUsername);

        } else{
            countDownTimer.cancel();
            btnNext.setText("Finish Exam");
            btnNext.setBackgroundColor(Color.parseColor("#00ff44"));
        }
    }



    //TIMER FUNCTION
    private void timer(String passUsername) {
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
                finishTest(passUsername);

            }
        }.start();
    }


    //NAVIGATES TO RESULT SCREEN
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void finishTest(String passUsername){


        //MAKES PROGRESS BAR APPEAR, AND OTHER OBJECTS DISAPPEAR
        pbProgressBar.setVisibility(View.VISIBLE);
        tvQuestionWithoutImage.setVisibility(View.VISIBLE);
        tvQuestionWithoutImage.setText("SAVING EXAM RESULTS...");
        tvQuestionWithImage.setVisibility(View.INVISIBLE);
        radioGroup.setVisibility(View.INVISIBLE);
        questionImage.setVisibility(View.INVISIBLE);
        tvExitTest.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        btnPrev.setVisibility(View.INVISIBLE);
        btnFinish.setVisibility(View.INVISIBLE);
        tvAnswerWarning.setVisibility(View.INVISIBLE);
        ttsImage.setVisibility(View.INVISIBLE);

        //CHECKS IF PASS PERCENTAGE IS ACHIEVED AND DISPLAYS OUTCOME
        String outcome;
        double passCheck = score * 100 / totalQuestions;
        if(passCheck > 85){ outcome = "PASS"; }
        else { outcome = "FAIL"; }

        String saveQuestionStrings = saveQuestion.toString().replace("[", "").replace("]", "");
        String scoreAsString = String.valueOf(score);
        String totalQuestionsAsString = String.valueOf(totalQuestions);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {


                //Assign current date and time to string
                Date today = new Date();
                SimpleDateFormat format = new SimpleDateFormat("MMMM dd yyyy ' @ ' hh:mm a");
                String date = format.format(today);

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
                data[0] = passUsername;
                data[1] = scoreAsString;
                data[2] = totalQuestionsAsString;
                data[3] = outcome;
                data[4] = date;
                data[5] = saveQuestionStrings;


                PostData postData = new PostData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/saveResults.php", "POST", field, data);


                if (postData.startPut()) {





                    if (postData.onComplete()) {
                        String result = postData.getResult();
                        if (result.equals("Results Save Success")) {
                            openFullExamResult(passUsername);
                        }

                        else{
                            //MAKES PROGRESS BAR APPEAR, AND OTHER OBJECTS DISAPPEAR
                            pbProgressBar.setVisibility(View.INVISIBLE);
                            tvQuestionWithoutImage.setVisibility(View.VISIBLE);
                            tvQuestionWithoutImage.setText("Error saving exam");
                            tvQuestionWithImage.setVisibility(View.VISIBLE);
                            radioGroup.setVisibility(View.VISIBLE);
                            questionImage.setVisibility(View.VISIBLE);
                            tvExitTest.setVisibility(View.VISIBLE);
                            btnNext.setVisibility(View.VISIBLE);
                            btnPrev.setVisibility(View.VISIBLE);
                            btnFinish.setVisibility(View.VISIBLE);
                            tvAnswerWarning.setVisibility(View.VISIBLE);
                            ttsImage.setVisibility(View.VISIBLE);
                            //OPEN PREVIOUS SCREEN
                       //     openPrevScreen(passUsername);
                            //SHOW ERROR MESSAGE
                            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                        }

                    }}}
        });

    }



    //END TEST TO PREVENT CHEATING (LOOKING UP ANSWERS ON INTERNET APPLICATION ETC)
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }




    //TEXT TO SPEECH FUNCTION
    private void speak(String questionNumber) {
        String question = tvQuestionWithImage.getText().toString();
        String answer1 = rb1.getText().toString();
        String answer2 = rb2.getText().toString();
        String answer3 = rb3.getText().toString();
        String answer4 = rb4.getText().toString();

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


    //OPENING DIALOG
    public void openDialog(String username, String input, String title, String positiveButton, String negativeButton) {
        ExampleDialog exampleDialog = new ExampleDialog(username,input,title,positiveButton,negativeButton);
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }



    //PROCEED TO NEXT SCREEN
    public void openFullExamResult(String username) {
        Intent intent = new Intent(this, ActivityFullExamResult.class);
        intent.putExtra("TOTAL_QUESTIONS", totalQuestions);
        intent.putExtra("SCORE", score);
        intent.putExtra("username_key",username);
        startActivity(intent);
        finish();
    }




    //GO BACK TO PREVIOUS SCREEN (IN CASE OF RESULT SAVE FAILED)
    public void openPrevScreen(String username) {
        Intent intent = new Intent(getApplicationContext(), ActivityLearnToDriveMenu.class);
        intent.putExtra("username_key", username);
        mTTS.stop();
        startActivity(intent);
        finish();
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


    public void getQuestions(){

        pbProgressBar.setVisibility(View.VISIBLE);
        FetchData fetchData = new FetchData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/getquestions.php");
        if (fetchData.startFetch()) {
            if (fetchData.onComplete()) {
                fetchedResult = fetchData.getData();
                Log.i("FetchData", fetchedResult);
                //End ProgressBar (Set visibility to GONE)
                pbProgressBar.setVisibility(View.GONE);

            }
        }

        try {
            JSONObject obj = new JSONObject(fetchedResult);
            JSONArray questionData = obj.getJSONArray("questiondata");
            int n = questionData.length();
            for (int i = 0; i < n; ++i) {
                JSONObject questionObj = questionData.getJSONObject(i);
                QuestionModel q = new QuestionModel();
                int ID = questionObj.getInt("id");
                q.setID(ID);
                String category = questionObj.getString("category");
                q.setCategory(category);
                String question = questionObj.getString("question");
                q.setQuestion(question);
                String option1 = questionObj.getString("option1");
                q.setOption1(option1);
                String option2 = questionObj.getString("option2");
                q.setOption2(option2);
                String option3 = questionObj.getString("option3");
                q.setOption3(option3);
                String option4 = questionObj.getString("option4");
                q.setOption4(option4);
                int answer = questionObj.getInt("answer");
                q.setAnswerNr(answer);
                String imageID = questionObj.getString("image");
                q.setImageID(imageID);
                String explanation = questionObj.getString("explanation");
                q.setExplanation(explanation);
                questionListFromRemote.add(q);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}
