package com.example.drivingtheoryapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    public static String saveQuestionString;
    TestDbHelper mDatabaseHelper;
    private ListView allResultsListView;
    private ArrayList<String> arrayListExamOutcome = new ArrayList<>();
    private ArrayList<String> arrayListaskedQuestions = new ArrayList<>();
    private String fetchedResult;
    private ProgressBar pbProgressBar;
    private List<ResultModel> resultList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_all);
        TextView tvTestStats = (TextView) findViewById(R.id.tvTestStats);
        allResultsListView = (ListView) findViewById(R.id.allResultsListView);
        mDatabaseHelper = new TestDbHelper(this);
        ImageView backButtonIcon = (ImageView) findViewById(R.id.ID_returnButton);

        //Code for passing username from last activity and assigning to string variable
        Intent intent = getIntent();
        String username = intent.getStringExtra("username_key");

        //Run List function passing in username
        populateListView(username, tvTestStats);


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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void populateListView(String getUsernameData, TextView overviewLabel) {
        //get the data and append to a list
        Cursor data = mDatabaseHelper.getAllResults(getUsernameData);
        int pass=0, fail=0;


        while(data.moveToNext()){

            int examScore = data.getInt(2);
            int examNoOfQuestions = data.getInt(3);
            String examDate = data.getString(4);
            String verdict = "";
            String examAskedQuestionsAndAnswers = data.getString(5);


            //CHECKS IF PASS PERCENTAGE IS ACHIEVED AND DISPLAYS OUTCOME
            double passCheck = examScore * 100 / examNoOfQuestions;
            if(passCheck > 85){ verdict = "PASS";pass++; }
            else { verdict = "FAIL";fail++; }

            //ADD DATA TO LIST VIEW
            arrayListExamOutcome.add("\n"+examDate + "\n" + "Exam Score: " + examScore + "/" + examNoOfQuestions + " (" + passCheck + "%) " + "Outcome: " + verdict + "\n\n" + examAskedQuestionsAndAnswers);
        }





        //ERROR HANDLING IF USER HAS NOT TAKEN ANY PREVIOUS EXAMS OR IF USING GUEST ACCOUNT
        int size = arrayListExamOutcome.size();
        double overallPassRate;
        if (size<1){
            overviewLabel.setText("No full mock exams found.");
        }
        else{
        overallPassRate = pass * 100 / size;
        Collections.reverse(arrayListExamOutcome); // Now the list is in reverse order (most recent exam at top)
            overviewLabel.setText("Total Exams Taken: " + size + "              Pass rate: " + overallPassRate + "%");
        }
        if (getUsernameData.equals("Guest")){
            overviewLabel.setText("Guest exam results are not saved. Please sign in.");}



        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListExamOutcome);
        allResultsListView.setAdapter(adapter);


        //set an onItemClickListener to the ListView
        allResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //String resultID = adapterView.getItemAtPosition(i).toString();
                Intent editScreenIntent = new Intent(ActivityResultsAll.this, ActivityResultsSpecific.class);
                editScreenIntent.putExtra("result",arrayListaskedQuestions);
                startActivity(editScreenIntent);
            }
        });

    }








    public void getQuestions(){

        pbProgressBar.setVisibility(View.VISIBLE);
        FetchData fetchData = new FetchData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/getresults.php");
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
                ResultModel r = new ResultModel();

                int ID = questionObj.getInt("id");
                r.setID(ID);

                String username = questionObj.getString("username");
                r.setUsername(username);

                String questionsCorrect = questionObj.getString("questions_correct");
                r.setQuestionsCorrect(questionsCorrect);

                String questionsTotal = questionObj.getString("questions_total");
                r.setQuestionsTotal(questionsTotal);

                String outcome = questionObj.getString("outcome");
                r.setOutcome(outcome);

                String date = questionObj.getString("date");
                r.setDate(date);

                String savedQuestion = questionObj.getString("saved_question");
                r.setSavedQuestion(outcome);

                resultList.add(r);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }







}



