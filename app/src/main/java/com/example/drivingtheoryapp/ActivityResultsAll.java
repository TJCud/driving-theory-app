package com.example.drivingtheoryapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;



public class ActivityResultsAll extends AppCompatActivity {


    private ListView allResultsListView;
    private final ArrayList<String> arrayListExamOutcome = new ArrayList<>();
    private final ArrayList<String> arrayListAskedQuestions = new ArrayList<>();

    private String fetchedResult;
    private String username;
    private int pass = 0;
    private ProgressBar progressBar;
    private TextView progressBarText;
    private final ResultModel resultModel = new ResultModel();
    private Button viewChange;

    //FOR CREATING CHART
    BarChart barChart;
    private Boolean chartVisible;
    ArrayList<BarEntry> failedExamEntries = new ArrayList<>();
    ArrayList<BarEntry> passedExamEntries = new ArrayList<>();



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_all);

        TextView tvTestStats = (TextView) findViewById(R.id.tvTestStats);
        allResultsListView = (ListView) findViewById(R.id.allResultsListView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        progressBarText = findViewById(R.id.progressBarText);
        progressBarText.setVisibility(View.GONE);
        viewChange = findViewById(R.id.viewChange);
        barChart = findViewById(R.id.barChart);


        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        username = intent.getStringExtra("username_key");


        barChart.setVisibility(View.GONE);

        //DISPLAY MESSAGE TO GUEST ACCOUNT
        if (username.equals("guest")){
            tvTestStats.setText("Guest exam results are not saved. Please sign in.");
            viewChange.setVisibility(View.GONE);
        }
        else {

            getResults(username);
            displayResultList(username, tvTestStats);
            drawChart(username, tvTestStats);
            barChart.setVisibility(View.GONE);
            viewChange.setText("Chart View");

        }




        //FOR CHANGING BETWEEN CHART AND LIST VIEW
        chartVisible = false;
        viewChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(chartVisible){
                    viewChange.setText("Chart View");
                    barChart.setVisibility(View.GONE);
                    allResultsListView.setVisibility(View.VISIBLE);
                    chartVisible = false;
                } else {
                    viewChange.setText("List View");
                    barChart.setVisibility(View.VISIBLE);
                    allResultsListView.setVisibility(View.GONE);
                    chartVisible = true;
                }
            }
        });



    }



    public void getResults(String passUsername){

            //Creating array for parameters
            String[] field = new String[1];
            field[0] = "username";


            //Creating array for data
            String[] data = new String[1];
            data[0] = passUsername;

            //Show progress bar
            progressBar.setVisibility(View.VISIBLE);
            progressBarText.setVisibility(View.VISIBLE);

            PostData postData = new PostData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/getresults.php", "POST", field, data);
            if (postData.startPut()) {
                if (postData.onComplete()) {
                    fetchedResult = postData.getData();
                    Log.i("FetchData", fetchedResult);

                    //End ProgressBar (Set visibility to GONE)
                    progressBar.setVisibility(View.GONE);
                    progressBarText.setVisibility(View.GONE);

                }
            }
        }

        public void displayResultList(String passUsername, TextView overviewLabel){

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
                    int questionsCorrect = questionObj.getInt("questions_correct");
                    int questionsTotal = questionObj.getInt("questions_total");
                    String outcome = questionObj.getString("outcome");
                    if(outcome.equals("PASS")){ pass++; } //COUNTING NUMBER OF TIMES USER HAS PASSED EXAM
                    String date = questionObj.getString("date");
                    String savedQuestion = questionObj.getString("saved_question");
                    resultModel.setSavedQuestion(savedQuestion);
                    //CHECKS IF PASS PERCENTAGE IS ACHIEVED AND DISPLAYS OUTCOME
                    double passCheck = questionsCorrect * 100 / questionsTotal;


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
                            Intent resultsSpecificIntent = new Intent(ActivityResultsAll.this, ActivityResultsSpecific.class);
                            resultsSpecificIntent.putExtra("result_key",getSavedQuestionString.replaceAll(",", ""));
                            resultsSpecificIntent.putExtra("username_key", passUsername);
                            startActivity(resultsSpecificIntent);
                            finish();
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



            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void drawChart(String passUsername, TextView overviewLabel){

        try {
            JSONObject obj = new JSONObject(fetchedResult);
            JSONArray questionData = obj.getJSONArray("resultdata");
            int n = questionData.length();


            //CHECK IF ANY EXAMS EXIST FOR USER
            if (n<1){
                overviewLabel.setText("\nNo results found for "+ passUsername +".");
            }
            else{


                int testNo = 0;

                for (int i = 0; i < n; ++i) {

                    JSONObject questionObj = questionData.getJSONObject(i);
                    int examScore = questionObj.getInt("questions_correct");
                    String examOutcome = questionObj.getString("outcome");
                    testNo++;


                    //IF EXAM SCORE IS LESS THAN 44, ADD TO FAIL LIST, ELSE ADD TO PASS LIST
                    if (examOutcome.equals("FAIL")){
                    failedExamEntries.add(new BarEntry(testNo, examScore));}
                    else{
                        passedExamEntries.add(new BarEntry(testNo, examScore));
                    }

                }


                BarDataSet barDataSetFail = new BarDataSet(failedExamEntries, "FailedEntries");
                BarDataSet barDataSetPass = new BarDataSet(passedExamEntries, "FailedEntries");

                //FAILED EXAMS
                barDataSetFail.setColor(Color.RED);
                barDataSetFail.setValueTextColor(Color.BLACK);
                barDataSetFail.setValueTextSize(16f);

                //PASSED EXAMS
                barDataSetPass.setColor(Color.GREEN);
                barDataSetPass.setValueTextColor(Color.BLACK);
                barDataSetPass.setValueTextSize(16f);

                //ADDS DATA SETS TO BAR DATA
                BarData barData = new BarData();
                barData.addDataSet(barDataSetFail);
                barData.addDataSet(barDataSetPass);

                //REMOVES DECIMAL POINT FROM RESULTS
                barData.setValueFormatter(new MyValueFormatter());

                barChart.setFitBars(true);
                barChart.setData(barData);
                barChart.getLegend().setEnabled(false);
                barChart.getAxisRight().setDrawLabels(false);
                barChart.getAxisRight().setEnabled(false);
                barChart.getAxisLeft().setDrawLabels(false);
                barChart.getAxisLeft().setEnabled(false);
                barChart.getXAxis().setEnabled(false);
                barChart.getDescription().setText("");
                barChart.setVisibleXRangeMaximum(15);
                barChart.getAxisRight().setAxisMinimum(0);
                barChart.getAxisRight().setAxisMaximum(50);
                barChart.setDrawGridBackground(true);





            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ActivityResultsAll.this, ActivityAccountMenu.class);
        intent.putExtra("username_key",username);
        startActivity(intent);
        finish();
    }





}


