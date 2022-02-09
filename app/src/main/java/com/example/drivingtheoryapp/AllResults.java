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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class AllResults extends AppCompatActivity {

    public static String saveQuestionString;
    public static String saveResultString;
    TestDbHelper mDatabaseHelper;
    private ListView mListView;
    private ArrayList<String> listDataOutcome = new ArrayList<>();
    private ArrayList<String> listDataQuestionsAndAnswers = new ArrayList<>();
    
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_results);
        TextView tvTestStats = (TextView) findViewById(R.id.tvTestStats);
        mListView = (ListView) findViewById(R.id.listView);
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
                Intent returnMenu = new Intent(getApplicationContext(), AccountMenu.class);
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

            int score = data.getInt(2);
            int questions = data.getInt(3);
            String date = data.getString(4);
            String verdict = "";
            int size = listDataOutcome.size();

            //CHECKS IF PASS PERCENTAGE IS ACHIEVED AND DISPLAYS OUTCOME
            double passCheck = score * 100 / questions;
            if(passCheck > 85){ verdict = "PASS";pass++; }
            else { verdict = "FAIL";fail++; }

            listDataOutcome.add("\n"+date + "\n" + "Exam Score: " + score + "/" + questions + " (" + passCheck + "%) " + "Outcome: " + verdict + "\n");

            String allAskedQuestions = data.getString(5);
            String allUserAnswers = data.getString(6);
            String allCorrectAnswers = data.getString(7);



            for (int i=0;i<listDataOutcome.size();i++){
                String[] askedQuestion = allAskedQuestions.split((Pattern.quote("|")));
                String[] userAnswer = allUserAnswers.split((Pattern.quote("|")));
                String[] correctAnswer = allCorrectAnswers.split((Pattern.quote("|")));
                listDataQuestionsAndAnswers.add("Question: " + askedQuestion[i] + "\n\nYour answer: " + userAnswer[i] + "\n\nCorrect Answer:" + correctAnswer[i] + "\n\n\n\n");

            }

            saveQuestionString = listDataQuestionsAndAnswers.stream().map(Object::toString)
                    .collect(Collectors.joining(" | "));

            saveResultString = listDataOutcome.stream().map(Object::toString)
                    .collect(Collectors.joining(" | "));
        }


        int size = listDataOutcome.size();
        double overallPassRate;

        //ERROR HANDLING IF USER HAS NOT TAKEN ANY PREVIOUS EXAMS
        if (size<1){
            overviewLabel.setText("No full mock exams found.");
        }
        else{
        overallPassRate = pass * 100 / size;
        Collections.reverse(listDataOutcome); // Now the list is in reverse order (most recent exam at top)
            overviewLabel.setText("Total Exams Taken: " + size + "              Pass rate: " + overallPassRate + "%");

        }
        if (getUsernameData.equals("Guest")){
            overviewLabel.setText("Guest exam results are not saved. Please sign in.");}



      //  listDataOutcome.addAll(listDataQuestionsAndAnswers);

        // New list containing a union b
        List<String> merged = new ArrayList<String>(Collections.singleton(saveResultString));
       // merged.addAll(listDataQuestionsAndAnswers);


        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listDataOutcome);


        mListView.setAdapter(adapter);
        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String resultID = adapterView.getItemAtPosition(i).toString();


                Cursor data = mDatabaseHelper.getAllResults(resultID); //get the id associated with that name
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }


                    Intent editScreenIntent = new Intent(AllResults.this, ActivityReviewResults.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("username_key",getUsernameData);
                    editScreenIntent.putExtra("result",saveQuestionString);
                    startActivity(editScreenIntent);
            }
        });




    }
}




