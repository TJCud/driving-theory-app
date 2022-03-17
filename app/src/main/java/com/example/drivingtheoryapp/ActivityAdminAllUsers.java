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


public class ActivityAdminAllUsers extends AppCompatActivity {

    private ListView allUsersListView;
    private ArrayList<String> arrayListAllUsers = new ArrayList<>();


    private String fetchedResult;
    private ProgressBar progressBar;
    private TextView progressBarText;





    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_users);

        TextView tvTestStats = (TextView) findViewById(R.id.tvTestStats);
        allUsersListView = (ListView) findViewById(R.id.allResultsListView);
        ImageView backButtonIcon = (ImageView) findViewById(R.id.ID_returnButton);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        progressBarText = findViewById(R.id.progressBarText);
        progressBarText.setVisibility(View.GONE);



        //Code for passing username from last activity and assigning to string variable
        Intent intent = getIntent();
        String username = intent.getStringExtra("username_key");



            getAllUsers();
            displayUsers();




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



    public void getAllUsers(){


        //Show progress bar
        progressBar.setVisibility(View.VISIBLE);
        progressBarText.setVisibility(View.VISIBLE);

        FetchData fetchData = new FetchData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/getallusers.php");
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



    public void displayUsers(){

        try {
            JSONObject obj = new JSONObject(fetchedResult);
            JSONArray questionData = obj.getJSONArray("allusersdata");
            int n = questionData.length();


            //CHECK IF ANY EXAMS EXIST FOR USER
            if (n<1){

            }
            else{

                for (int i = 0; i < n; ++i) {
                    JSONObject questionObj = questionData.getJSONObject(i);

                    //PARSING DATA FROM JSON TO VARIABLES
                    int fetchedID = questionObj.getInt("id");
                    String fetchedUsername = questionObj.getString("username");
                    String fetchedPassword = questionObj.getString("password");
                    String fetchedEmail = questionObj.getString("email");
                    String fetchedDate = questionObj.getString("date");




                    //ADDS EXAM DATA TO ARRAY LISTS
                    arrayListAllUsers.add("Username: " + fetchedUsername + " (User ID: " + fetchedID +")\nDate Registered: "+fetchedDate + "\n" + "Password: " + fetchedPassword + "\n"  + fetchedEmail);


                    //CREATE AND SET THE LIST ADAPTER FOR DISPLAYING EXAM INFO
                    ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListAllUsers);
                    allUsersListView.setAdapter(adapter);

                    //set an onItemClickListener to the ListView
                    allUsersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            String getSavedQuestionString = arrayListAllUsers.get(position);
                            Intent editScreenIntent = new Intent(ActivityAdminAllUsers.this, ActivityResultsSpecific.class);
                            editScreenIntent.putExtra("result",getSavedQuestionString.replaceAll(",", ""));
                            startActivity(editScreenIntent);
                        }
                    });
                }




            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}