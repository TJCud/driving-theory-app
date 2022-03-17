package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityAdminEditQuestion extends AppCompatActivity {

    private String fetchedResult;
    private ProgressBar progressBar;
    private TextView progressBarText;
    private EditText questionIDEditText, questionEditText, option1EditText, option2EditText, option3EditText, option4EditText,explanationEditText;
    private Spinner categorySpinner, correctAnswerSpinner;
    private Button searchIDButton, saveQuestionButton, newQuestionButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_question);


        searchIDButton = findViewById(R.id.searchButton);
        saveQuestionButton = findViewById(R.id.saveQuestionButton);
        newQuestionButton = findViewById(R.id.newQuestionButton);

        categorySpinner = findViewById(R.id.CategorySpinner);
        correctAnswerSpinner = findViewById(R.id.AnswerSpinner);

        questionIDEditText = findViewById(R.id.EditTextQuestionID);
        questionEditText = findViewById(R.id.EditTextQuestion);
        option1EditText = findViewById(R.id.EditTextOption1);
        option2EditText = findViewById(R.id.EditTextOption2);
        option3EditText = findViewById(R.id.EditTextOption3);
        option4EditText = findViewById(R.id.EditTextOption4);
        explanationEditText = findViewById(R.id.EditTextExplanation);





        //BUTTON LISTENERS
        searchIDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestionsByID(questionIDEditText.getText().toString());
                displayQuestionByID();

            }
        });


        saveQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[10];
                        field[0] = "category";
                        field[1] = "question";
                        field[2] = "option1";
                        field[3] = "option2";
                        field[4] = "option3";
                        field[5] = "option4";
                        field[6] = "answer";
                        field[7] = "image";
                        field[8] = "explanation";
                        field[9] = "categoryID";
                        //Creating array for data
                        String[] data = new String[10];
                        data[0] = categorySpinner.getSelectedItem().toString();
                        data[1] = questionEditText.getText().toString();
                        data[2] = option1EditText.getText().toString();
                        data[3] = option2EditText.getText().toString();
                        data[4] = option3EditText.getText().toString();
                        data[5] = option4EditText.getText().toString();
                        data[6] = correctAnswerSpinner.getSelectedItem().toString();
                        data[7] = "default";
                        data[8] = explanationEditText.getText().toString();
                        data[9] = "1";


                        PostData postData = new PostData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/newQuestion.php", "POST", field, data);

                        if (postData.startPut()) {
                            if (postData.onComplete()) {
                                String result = postData.getResult();
                                if (result.equals("Sign Up Success")) {
                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                }

                                else{

                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();

                                }

                            }}}
                });





            }
        });



        newQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Show progress bar
              //  progressBar.setVisibility(View.VISIBLE);
             //   progressBarText.setVisibility(View.VISIBLE);

                FetchData fetchData = new FetchData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/getquestions.php");
                if (fetchData.startFetch()) {
                    if (fetchData.onComplete()) {
                        fetchedResult = fetchData.getData();
                        Log.i("FetchData", fetchedResult);

                        //End ProgressBar (Set visibility to GONE)
                       // progressBar.setVisibility(View.GONE);
                      //  progressBarText.setVisibility(View.GONE);

                    }
                }

                try {
                    JSONObject obj = new JSONObject(fetchedResult);
                    JSONArray questionData = obj.getJSONArray("questiondata");
                    int n = questionData.length();
                    int newQuestion = n+1;
                    questionIDEditText.setText(String.valueOf(newQuestion));
                    questionEditText.getText().clear();
                    option1EditText.getText().clear();
                    option2EditText.getText().clear();
                    option3EditText.getText().clear();
                    option4EditText.getText().clear();
                    explanationEditText.getText().clear();





                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        });






    }



    public void getQuestionsByID(String questionID){

        //Show progress bar
//        progressBar.setVisibility(View.VISIBLE);
   //     progressBarText.setVisibility(View.VISIBLE);

        String[] field = new String[1];
        field[0] = "ID";
        //Creating array for data
        String[] data = new String[1];
        data[0] = questionID;


        PostData postData = new PostData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/getQuestionByID.php", "POST", field, data);
        if (postData.startPut()) {
            if (postData.onComplete()) {
                fetchedResult = postData.getData();
                Log.i("FetchData", fetchedResult);

                //End ProgressBar (Set visibility to GONE)
         //       progressBar.setVisibility(View.GONE);
           //     progressBarText.setVisibility(View.GONE);

            }
        }
    }



    public void displayQuestionByID(){

        try {
            JSONObject obj = new JSONObject(fetchedResult);
            JSONArray questionData = obj.getJSONArray("questionDataByID");
            int n = questionData.length();


            //CHECK IF ANY EXAMS EXIST FOR USER
            if (n<1){

            }
            else{

                for (int i = 0; i < n; ++i) {
                    JSONObject questionObj = questionData.getJSONObject(i);

                    //PARSING DATA FROM JSON TO VARIABLES
                    int fetchedCategory = questionObj.getInt("categoryID");
                    categorySpinner.setSelection(fetchedCategory-1);
                    String fetchedQuestion = questionObj.getString("question");
                    questionEditText.setText(fetchedQuestion);
                    String fetchedOption1 = questionObj.getString("option1");
                    option1EditText.setText(fetchedOption1);
                    String fetchedOption2 = questionObj.getString("option2");
                    option2EditText.setText(fetchedOption2);
                    String fetchedOption3 = questionObj.getString("option3");
                    option3EditText.setText(fetchedOption3);
                    String fetchedOption4 = questionObj.getString("option4");
                    option4EditText.setText(fetchedOption4);
                    int fetchedAnswer = questionObj.getInt("answer");
                    correctAnswerSpinner.setSelection(fetchedAnswer-1);
                    String fetchedImageID = questionObj.getString("image");
                    String fetchedExplanation = questionObj.getString("explanation");
                    explanationEditText.setText(fetchedExplanation);

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }






}