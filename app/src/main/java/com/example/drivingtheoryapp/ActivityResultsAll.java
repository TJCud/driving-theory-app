package com.example.drivingtheoryapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ActivityResultsAll extends AppCompatActivity {


    private ListView allResultsListView;
    private ArrayList<String> arrayListExamOutcome = new ArrayList<>();
    private ArrayList<String> arrayListAskedQuestions = new ArrayList<>();
    private String fetchedResult;
    private int pass = 0;
    private ResultModel resultModel = new ResultModel();


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_all);

        TextView tvTestStats = (TextView) findViewById(R.id.tvTestStats);
        allResultsListView = (ListView) findViewById(R.id.allResultsListView);
        ImageView backButtonIcon = (ImageView) findViewById(R.id.ID_returnButton);

        //Code for passing username from last activity and assigning to string variable
        Intent intent = getIntent();
        String username = intent.getStringExtra("username_key");


        //Results method
        getResults(username, tvTestStats);



        backButtonIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnMenu = new Intent(getApplicationContext(), ActivityAccountMenu.class);
                returnMenu.putExtra("username_key",username);
                finish();
                startActivity(returnMenu);
            }
        });
    }



    public void getResults(String passUsername, TextView overviewLabel){

        //Creating array for parameters
        String[] field = new String[1];
        field[0] = "username";


        //Creating array for data
        String[] data = new String[1];
        data[0] = passUsername;


        //pbProgressBar.setVisibility(View.VISIBLE);
        PostData postData = new PostData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/getresults2.php", "POST", field, data);
        if (postData.startPut()) {
            if (postData.onComplete()) {
                fetchedResult = postData.getData();
                Log.i("FetchData", fetchedResult);
                //End ProgressBar (Set visibility to GONE)
//                pbProgressBar.setVisibility(View.GONE);

            }
        }

        try {
            JSONObject obj = new JSONObject(fetchedResult);
            JSONArray questionData = obj.getJSONArray("resultdata");
            int n = questionData.length();


            //CHECK IF ANY EXAMS EXIST FOR USER
            if (n<1){
                overviewLabel.setText("\nNo results found for "+ passUsername +".");
            }
            else{

                for (int i = 0; i < n; ++i) {
                    JSONObject questionObj = questionData.getJSONObject(i);

                    //PARSING DATA FROM JSON TO VARIABLES
                    int ID = questionObj.getInt("id");
                    String questionsCorrect = questionObj.getString("questions_correct");
                    String questionsTotal = questionObj.getString("questions_total");
                    String outcome = questionObj.getString("outcome");
                    if(outcome.equals("PASS")){ pass++; } //COUNTING NUMBER OF TIMES USER HAS PASSED EXAM
                    String date = questionObj.getString("date");
                    String savedQuestion = questionObj.getString("saved_question");
                    resultModel.setSavedQuestion(savedQuestion);
                    //CHECKS IF PASS PERCENTAGE IS ACHIEVED AND DISPLAYS OUTCOME
                    double passCheck = Integer.parseInt(questionsCorrect) * 100 / Integer.parseInt(questionsTotal);


                    //ADDS EXAM DATA TO ARRAY LISTS
                    arrayListExamOutcome.add("Exam ID: " + ID +"\n"+date + "\n" + "Exam Score: " + questionsCorrect + "/" + questionsTotal + " (" + passCheck + "%) " + "Outcome: " + outcome);
                    arrayListAskedQuestions.add("Exam ID: " + ID +"\n\n"+savedQuestion + "\n\n");


                    //CREATE AND SET THE LIST ADAPTER FOR DISPLAYING EXAM INFO
                    ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListExamOutcome);
                    allResultsListView.setAdapter(adapter);

                    //set an onItemClickListener to the ListView
                    allResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            String getSavedQuestionString = arrayListAskedQuestions.get(position);
                            Intent editScreenIntent = new Intent(ActivityResultsAll.this, ActivityResultsSpecific.class);
                            editScreenIntent.putExtra("result",getSavedQuestionString);
                            startActivity(editScreenIntent);
                        }
                    });
                }




                //CALCULATING PASS RATE
                double overallPassRate;
                overallPassRate = pass * 100 / n;

                // Now the list is in reverse order (most recent exam at top)
                Collections.reverse(arrayListExamOutcome);
                Collections.reverse(arrayListAskedQuestions);

                //SET EXAM OVERVIEW LABEL
                overviewLabel.setText("Total Exams Taken: " + n + "              Pass rate: " + overallPassRate + "%");

                //DISPLAY MESSAGE TO GUEST ACCOUNT
                if (passUsername.equals("Guest")){
                    overviewLabel.setText("Guest exam results are not saved. Please sign in.");}

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}


