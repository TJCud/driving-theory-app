package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityAdminEditUser extends AppCompatActivity {

    private String fetchedResult;
    private String username;
    private ProgressBar progressBar;
    private TextView progressBarText, statusTextView;
    private EditText userIDEditText, usernameEditText, emailEditText, passwordEditText;
    private Spinner accountTypeSpinner;
    private Button searchUserButton, newUserButton, saveUserButton;
    private ImageView returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_user);

        Bundle recdData = getIntent().getExtras();
        String passedValue = recdData.getString("passedID");

        searchUserButton = findViewById(R.id.searchButton);
        saveUserButton = findViewById(R.id.saveUserButton);
        newUserButton = findViewById(R.id.newUserButton);
        statusTextView = findViewById(R.id.userSearchStatus);
        accountTypeSpinner = findViewById(R.id.AccountTypeSpinner);
        returnButton = findViewById(R.id.returnButton);


        userIDEditText = findViewById(R.id.EditTextUserID);
        userIDEditText.setText(passedValue);
        usernameEditText = findViewById(R.id.EditTextUsername);
        emailEditText = findViewById(R.id.EditTextEmail);
        passwordEditText = findViewById(R.id.EditTextPassword);


        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        username = intent.getStringExtra("username_key");




        getUserByID(passedValue);



        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnMenu = new Intent(getApplicationContext(), ActivityAdminAllUsers.class);
                String username = "admin";
                returnMenu.putExtra("username_key",username);
                finish();
                startActivity(returnMenu);
            }
        });



        //BUTTON LISTENERS
        searchUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserByID(userIDEditText.getText().toString());
            }
        });


        saveUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[6];
                        field[0] = "id";
                        field[1] = "type";
                        field[2] = "username";
                        field[3] = "email";
                        field[4] = "date";
                        field[5] = "typeID";


                        //Creating array for data
                        String[] data = new String[6];
                        data[0] = userIDEditText.getText().toString();
                        String accountType;
                        accountType = accountTypeSpinner.getSelectedItem().toString();
                        data[1] = accountType;
                        data[2] = usernameEditText.getText().toString();
                        data[3] = emailEditText.getText().toString();

                        //Assign current date and time to string
                        Date today = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("MMMM dd yyyy ' @ ' hh:mm a");
                        String date = format.format(today);
                        data[4] = date;


                        int typeID;
                        typeID = accountTypeSpinner.getSelectedItemPosition() + 1;
                        String accountTypeIDToString = String.valueOf(typeID);
                        data[5] = accountTypeIDToString;

                        PostData postData = new PostData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/saveUser.php", "POST", field, data);

                        if (postData.startPut()) {
                            if (postData.onComplete()) {
                                String result = postData.getResult();
                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();

                            }}}
                });


            }
        });



        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Show progress bar
                //  progressBar.setVisibility(View.VISIBLE);
                //   progressBarText.setVisibility(View.VISIBLE);

                FetchData fetchData = new FetchData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/getallusers.php");
                if (fetchData.startFetch()) {
                    if (fetchData.onComplete()) {
                        fetchedResult = fetchData.getData();
                        Log.i("FetchData", fetchedResult);

                        try {
                            JSONObject obj = new JSONObject(fetchedResult);
                            JSONArray userData = obj.getJSONArray("allusersdata");
                            int n = userData.length();
                            int newUser = n+1;
                            statusTextView.setText("User ID: " + newUser);
                            statusTextView.setTextColor(Color.BLACK);
                            userIDEditText.setText(String.valueOf(newUser));
                            usernameEditText.getText().clear();
                            emailEditText.getText().clear();
                            passwordEditText.getText().clear();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        //End ProgressBar (Set visibility to GONE)
                        // progressBar.setVisibility(View.GONE);
                        //  progressBarText.setVisibility(View.GONE);

                    }
                }

            }
        });
    }



    public void getUserByID(String userID){

        //Show progress bar
//        progressBar.setVisibility(View.VISIBLE);
        //     progressBarText.setVisibility(View.VISIBLE);


        if (userID.isEmpty()){
            statusTextView.setText("Please enter an ID");
            statusTextView.setTextColor(Color.RED);
        }
        else {

            String[] field = new String[1];
            field[0] = "ID";
            //Creating array for data
            String[] data = new String[1];
            data[0] = userID;


            PostData postData = new PostData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/getUserByID.php", "POST", field, data);
            if (postData.startPut()) {
                if (postData.onComplete()) {
                    fetchedResult = postData.getData();
                    Log.i("FetchData", fetchedResult);


                    if (fetchedResult.contains("No Users found")) {
                        Toast.makeText(getApplicationContext(), "No user found for ID " + userID, Toast.LENGTH_SHORT).show();
                        statusTextView.setText("No users found for ID " + userID);
                        statusTextView.setTextColor(Color.RED);
                        userIDEditText.getText().clear();
                    } else {
                        try {
                            JSONObject obj = new JSONObject(fetchedResult);
                            JSONArray questionData = obj.getJSONArray("userDataByID");
                            int n = questionData.length();


                            //CHECK IF ANY EXAMS EXIST FOR USER
                            if (n < 1) {

                            } else {

                                for (int i = 0; i < n; ++i) {
                                    JSONObject questionObj = questionData.getJSONObject(i);

                                    //PARSING DATA FROM JSON TO VARIABLES
                                    String fetchedUserID = questionObj.getString("id");
                                    statusTextView.setText("User ID: " + fetchedUserID);
                                    statusTextView.setTextColor(Color.BLACK);
                                    String fetchedUsername = questionObj.getString("username");
                                    usernameEditText.setText(fetchedUsername);
                                    int fetchedTypeID = questionObj.getInt("typeID");
                                    accountTypeSpinner.setSelection(fetchedTypeID - 1);
                                    String fetchedEmail = questionObj.getString("email");
                                    emailEditText.setText(fetchedEmail);

                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //End ProgressBar (Set visibility to GONE)
                    //       progressBar.setVisibility(View.GONE);
                    //     progressBarText.setVisibility(View.GONE);

                }
            }
        }
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ActivityAdminEditUser.this, ActivityAdminAllUsers.class);
        intent.putExtra("username_key","admin");
        startActivity(intent);
        finish();
    }


}