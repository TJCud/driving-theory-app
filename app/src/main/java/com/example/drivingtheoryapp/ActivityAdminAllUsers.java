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
    private ArrayList<String> arrayListAllUsersInfo = new ArrayList<>();
    private ArrayList<String> arrayListAllUsersID = new ArrayList<>();


    private String fetchedResult;
    private String username;
    private ProgressBar progressBar;
    private TextView progressBarText, allUsersStats;





    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_users);

        allUsersListView = (ListView) findViewById(R.id.allResultsListView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        progressBarText = findViewById(R.id.progressBarText);
        progressBarText.setVisibility(View.GONE);
        allUsersStats = findViewById(R.id.allUsersStats);

        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        username = intent.getStringExtra("username_key");


        getAllUsers();
        displayUsers();


    }



    public void getAllUsers(){


        //Show progress bar
        progressBar.setVisibility(View.VISIBLE);
        progressBarText.setVisibility(View.VISIBLE);

        FetchData fetchData = new FetchData(getResources().getString(R.string.getAllUsers));
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
                    String fetchedID = questionObj.getString("id");
                    String fetchedUsername = questionObj.getString("username");
                    String fetchedAccountType = questionObj.getString("type");
                    String fetchedEmail = questionObj.getString("email");
                    String fetchedDate = questionObj.getString("date");




                    //ADDS EXAM DATA TO ARRAY LISTS
                    arrayListAllUsersInfo.add("Username: " + fetchedUsername + " (User ID: " + fetchedID +")\nAccount Type: " + fetchedAccountType + "\nDate Registered: "+fetchedDate + "\n" + "Email: " + fetchedEmail);
                    arrayListAllUsersID.add(fetchedID);

                    //CREATE AND SET THE LIST ADAPTER FOR DISPLAYING EXAM INFO
                    ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListAllUsersInfo);
                    allUsersListView.setAdapter(adapter);

                    //set an onItemClickListener to the ListView
                    allUsersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            String passedID = arrayListAllUsersID.get(position);
                            Intent editScreenIntent = new Intent(ActivityAdminAllUsers.this, ActivityAdminEditUser.class);
                            editScreenIntent.putExtra("passedID",passedID);
                            startActivity(editScreenIntent);
                            finish();
                        }
                    });
                }

            allUsersStats.setText("Accounts on system: " + n);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ActivityAdminAllUsers.this, ActivityAdminTools.class);
        intent.putExtra("username_key","admin");
        startActivity(intent);
        finish();
    }




}