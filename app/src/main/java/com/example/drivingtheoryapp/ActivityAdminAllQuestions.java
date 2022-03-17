package com.example.drivingtheoryapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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


public class ActivityAdminAllQuestions extends AppCompatActivity {

    private ListView allQuestionsListView;
    private ArrayList<String> arrayListAllQuestionsOverview = new ArrayList<>();
    private ArrayList<Integer> arrayListAllQuestionsByID = new ArrayList<>();


    private String fetchedResult;
    private ProgressBar progressBar;
    private TextView progressBarText;





    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_questions);

        TextView tvTestStats = (TextView) findViewById(R.id.tvTestStats);
        allQuestionsListView = (ListView) findViewById(R.id.allResultsListView);
        ImageView backButtonIcon = (ImageView) findViewById(R.id.ID_returnButton);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        progressBarText = findViewById(R.id.progressBarText);
        progressBarText.setVisibility(View.GONE);



        //Code for passing username from last activity and assigning to string variable
        Intent intent = getIntent();
        String username = intent.getStringExtra("username_key");



        getAllQuestions();
        displayQuestions(tvTestStats);




        backButtonIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnMenu = new Intent(getApplicationContext(), ActivityAdminTools.class);
                returnMenu.putExtra("username_key",username);
                finish();
                startActivity(returnMenu);
            }
        });
    }



    public void getAllQuestions(){


        //Show progress bar
        progressBar.setVisibility(View.VISIBLE);
        progressBarText.setVisibility(View.VISIBLE);

        FetchData fetchData = new FetchData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/getquestions.php");
        if (fetchData.startFetch()) {
            if (fetchData.onComplete()) {
                fetchedResult = fetchData.getData();
                Log.i("FetchData", fetchedResult);

                //End ProgressBar (Set visibility to GONE)
                progressBar.setVisibility(View.GONE);
                progressBarText.setVisibility(View.GONE);

            }
        }
    }



    public void displayQuestions(TextView overviewLabel){

        try {
            JSONObject obj = new JSONObject(fetchedResult);
            JSONArray questionData = obj.getJSONArray("questiondata");
            int n = questionData.length();


            //CHECK IF ANY EXAMS EXIST FOR USER
            if (n<1){

            }
            else{

                for (int i = 0; i < n; ++i) {
                    JSONObject questionObj = questionData.getJSONObject(i);

                    //PARSING DATA FROM JSON TO VARIABLES
                    int ID = questionObj.getInt("id");
                    String category = questionObj.getString("category");
                    String question = questionObj.getString("question");
                    String option1 = questionObj.getString("option1");
                    String option2 = questionObj.getString("option2");
                    String option3 = questionObj.getString("option3");
                    String option4 = questionObj.getString("option4");
                    int answer = questionObj.getInt("answer");
                    String imageID = questionObj.getString("image");
                    String explanation = questionObj.getString("explanation");




                    //ADDS EXAM DATA TO ARRAY LISTS
                    arrayListAllQuestionsOverview.add("Question ID: " + ID + "\nCategory: " + category+ "\nQuestion: " + question);
                    arrayListAllQuestionsByID.add(ID);

                    //CREATE AND SET THE LIST ADAPTER FOR DISPLAYING EXAM INFO
                    ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListAllQuestionsOverview);
                    allQuestionsListView.setAdapter(adapter);

                    //set an onItemClickListener to the ListView
                    allQuestionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            int getSavedQuestionString = arrayListAllQuestionsByID.get(position);
                            Intent editScreenIntent = new Intent(ActivityAdminAllQuestions.this, ActivityAdminEditQuestion.class);
                            editScreenIntent.putExtra("questionID",getSavedQuestionString);
                            startActivity(editScreenIntent);
                        }
                    });
                }


                //SET EXAM OVERVIEW LABEL
                overviewLabel.setText("Total Questions Found: " + n);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}