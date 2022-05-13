package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class ActivityLogin extends AppCompatActivity {

    public EditText editTextUsername, editTextPassword;
    public CardView signInButton, registerButton, guestUserBtn, forgotPasswordBtn;
    private long pressedTime;
    private ProgressBar pbProgressBar;
    private TextView tvProgressBarText;
    private String username, password;
    private TextView tvUsernameWarning,tvPasswordWarning;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //CLOSING APP ON BACK PRESS
        if (getIntent().getBooleanExtra("EXIT", false))
        { finish(); }


        //ASSIGNING VARIABLES TO ID
        pbProgressBar=(ProgressBar)findViewById(R.id.pbProgressBar);
        tvProgressBarText = findViewById(R.id.tvProgressBarText);
        pbProgressBar.setVisibility(View.GONE);
        tvProgressBarText.setVisibility(View.GONE);
        editTextUsername = findViewById(R.id.etUsername);
        editTextPassword = findViewById(R.id.etPassword);
        signInButton = (CardView) findViewById(R.id.signInBtn);
        registerButton = (CardView) findViewById(R.id.registerBtn);
        guestUserBtn = (CardView) findViewById(R.id.guestBtn);
        forgotPasswordBtn = (CardView) findViewById(R.id.forgotPasswordBtn);
        tvUsernameWarning = findViewById(R.id.tvUsernameWarning);
        tvPasswordWarning = findViewById(R.id.tvPasswordWarning);


        //HIDING INPUT WARNINGS
        tvUsernameWarning.setVisibility(View.GONE);
        tvPasswordWarning.setVisibility(View.GONE);




        //BUTTON LISTENERS
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setValuesFromFields(); //Sets value from edit text fields and passes into variables

                //IF ALL FIELDS ARE NOT EMPTY AND HAVE PASSED INITIAL DATA VALIDATION
                if(allFieldsValid()) {

                    attemptLogin(); //Begin login process

                }
                else {
                    hideProgressBar(); //MAKES PROGRESS BAR DISAPPEAR, AND OTHER OBJECTS APPEAR
                }


            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityRegister.class);
                startActivity(intent);
                finish();
            }
        });

        guestUserBtn.setOnClickListener(new View.OnClickListener() {  //PROCEED TO MAIN MENU AS A GUEST USER
            @Override
            public void onClick(View view) {
                openNextMenu("guest");
                finish();
            }
        });

        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {  //PROCEED TO MAIN MENU AS A GUEST USER
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });


        //Set background colours back to original when user clicks on edit text field
        editTextUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextUsername.setBackgroundColor(Color.parseColor("#B3ffffff"));
                }
            }
        });
        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextPassword.setBackgroundColor(Color.parseColor("#B3ffffff"));
                }
            }
        });

    }



    //METHOD FOR OPENING MAIN MENU, PASSING IN USERNAME
    public void openNextMenu(String passUsername){
        //FOR PASSING USERNAME TO OTHER ACTIVITIES


        //IF USER IS A GUEST ACCOUNT, PROCEED TO EXAM MENU
        Intent openMenu;
        if(passUsername.equals("guest")) {
            openMenu = new Intent(getApplicationContext(), ActivityLearnToDriveMenu.class);
        }
        //IF USER IS NOT A GUEST, PROCEED TO MAIN MENU
        else {
            openMenu = new Intent(getApplicationContext(), ActivityMainMenu.class);
        }

        openMenu.putExtra("username_key", passUsername);
        startActivity(openMenu);


    }


    //CHECKS IF ALL FIELDS ARE VALID
    private boolean allFieldsValid(){


        boolean allFieldsValid = true;

        //HIDES VALIDATION WARNINGS
        tvUsernameWarning.setVisibility(View.GONE);
        tvPasswordWarning.setVisibility(View.GONE);

        //USERNAME VALIDATION
        if (username.equals("")) {
            tvUsernameWarning.setTextColor(Color.RED);
            tvUsernameWarning.setVisibility(View.VISIBLE);
            editTextUsername.setBackgroundColor(Color.parseColor("#B3eb4034"));
            tvUsernameWarning.setText("Username field cannot be empty");
            allFieldsValid = false;
        }
        //PASSWORD VALIDATION
        if (password.equals("")) {
            tvPasswordWarning.setTextColor(Color.RED);
            tvPasswordWarning.setVisibility(View.VISIBLE);
            editTextPassword.setBackgroundColor(Color.parseColor("#B3eb4034"));
            tvPasswordWarning.setText("Password field cannot be empty");
            allFieldsValid = false;
        }

        return allFieldsValid;
    }



    //Sets value from edit text fields and passes into variables
    private void setValuesFromFields(){
        username = String.valueOf(editTextUsername.getText());
        password = String.valueOf(editTextPassword.getText());

    }

    //EXIT SYSTEM ON BACK PRESS
    @Override
    public void onBackPressed()
    {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();

            Intent intent = new Intent(ActivityLogin.this, ActivityLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);


        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    //MAKES PROGRESS BAR APPEAR, AND OTHER OBJECTS DISAPPEAR
    public void showProgressBar(){
        pbProgressBar.setVisibility(View.VISIBLE);
        tvProgressBarText.setVisibility(View.VISIBLE);
        editTextUsername.setVisibility(View.INVISIBLE);
        editTextPassword.setVisibility(View.INVISIBLE);
        signInButton.setVisibility(View.INVISIBLE);
        registerButton.setVisibility(View.INVISIBLE);
        guestUserBtn.setVisibility(View.INVISIBLE);
    }

    //MAKES PROGRESS BAR DISAPPEAR, AND OTHER OBJECTS APPEAR
    public void hideProgressBar(){
        pbProgressBar.setVisibility(View.INVISIBLE);
        tvProgressBarText.setVisibility(View.INVISIBLE);
        editTextUsername.setVisibility(View.VISIBLE);
        editTextPassword.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.VISIBLE);
        guestUserBtn.setVisibility(View.VISIBLE);
    }

    //Runs login function
    public void attemptLogin(){

        //SIGN IN VARIABLES
        String usernameRawData, username, password;

        //Takes value of 'usernameRawData' and converts to lower case
        usernameRawData = String.valueOf(editTextUsername.getText());
        password = String.valueOf(editTextPassword.getText());
        username = usernameRawData.toLowerCase();

        //MAKES PROGRESS BAR APPEAR, AND OTHER OBJECTS DISAPPEAR
        showProgressBar();


        //CHECKING IF USERNAME AND PASSWORD FIELDS ARE NOT BLANK
        if (!username.equals("") && !password.equals("")) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {

                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[2];
                    field[0] = "username";
                    field[1] = "password";
                    //Creating array for data
                    String[] data = new String[2];
                    data[0] = username;
                    data[1] = password;
                    PostData postData = new PostData(getResources().getString(R.string.login), "POST", field, data);
                    if (postData.startPut()) {
                        if (postData.onComplete()) {

                            String result = postData.getResult();
                            if (result.equals("Login Success")) {

                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                openNextMenu(username);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                //MAKES PROGRESS BAR DISAPPEAR, AND OTHER OBJECTS REAPPEAR
                                hideProgressBar();

                            }
                        }
                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            //MAKES PROGRESS BAR DISAPPEAR, AND OTHER OBJECTS REAPPEAR
            hideProgressBar();
        }








    }


}