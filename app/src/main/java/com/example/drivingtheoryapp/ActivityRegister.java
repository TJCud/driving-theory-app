package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ActivityRegister extends AppCompatActivity {

    public EditText editTextUsername, editTextPassword, editTextPasswordConfirm, editTextEmail;
    public CardView registerButton;
    private ProgressBar pbProgressBar;
    private TextView tvProgressBarText,tvEmailWarning,tvUsernameWarning,tvPasswordWarning,tvPasswordConfirmWarning;
    private String username, usernameRaw, password,passwordConfirm, email;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //ASSIGNING VARIABLES TO ID
        pbProgressBar=(ProgressBar)findViewById(R.id.pbProgressBar);
        tvProgressBarText = findViewById(R.id.tvProgressBarText);
        pbProgressBar.setVisibility(View.GONE);
        tvProgressBarText.setVisibility(View.GONE);
        editTextUsername = findViewById(R.id.etUsername);
        editTextPassword = findViewById(R.id.etPassword);
        editTextPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        editTextEmail = findViewById(R.id.etEmail);
        registerButton = (CardView) findViewById(R.id.registerBtn);
        tvEmailWarning = findViewById(R.id.tvEmailWarning);
        tvUsernameWarning = findViewById(R.id.tvUsernameWarning);
        tvPasswordWarning = findViewById(R.id.tvPasswordWarning);
        tvPasswordConfirmWarning = findViewById(R.id.tvPasswordConfirmWarning);

        //HIDING INPUT WARNINGS
        tvEmailWarning.setVisibility(View.GONE);
        tvUsernameWarning.setVisibility(View.GONE);
        tvPasswordWarning.setVisibility(View.GONE);
        tvPasswordConfirmWarning.setVisibility(View.GONE);


        //BUTTON LISTENERS
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setValuesFromFields(); //Sets value from edit text fields and passes into variables

                    //IF ALL FIELDS ARE NOT EMPTY AND HAVE PASSED INITIAL DATA VALIDATION
                    if(allFieldsValid()) {

                        attemptRegister(); //Begin registration process

                    }
                    else {
                        hideProgressBar(); //MAKES PROGRESS BAR DISAPPEAR, AND OTHER OBJECTS APPEAR
                    }
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
        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextEmail.setBackgroundColor(Color.parseColor("#B3ffffff"));
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
        editTextPasswordConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextPasswordConfirm.setBackgroundColor(Color.parseColor("#B3ffffff"));
                }
            }
        });

    }



    //RUN IF REGISTRATION IS SUCCESSFUL
    public void registrationSuccess(){
        Toast.makeText(getApplicationContext(),"Registration Success!",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), ActivityMainMenu.class);
        intent.putExtra("username_key", username);
        startActivity(intent);
        finish();
    }


    //EXIT TO LOGIN ON BACK PRESS
    @Override
    public void onBackPressed() {
            Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
            startActivity(intent);
            finish();
    }



    //CHECKS IF PASSWORD CONTAINS AT LEAST ONE CAPITAL LETTER, LOWERCASE LETTER AND NUMBER
    private static boolean isValidPassword(String password) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;


        for(int i=0;i < password.length();i++) {
            ch = password.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }

    //CHECKS IF USERNAME ONLY CONTAINS LETTERS OR NUMBERS
    private static boolean isValidUsername(String username){


        int len = username.length();
        for (int i = 0; i < len; i++) {
            // checks whether the character is neither a letter nor a digit
            // if it is neither a letter nor a digit then it will return false
            if ((!Character.isLetterOrDigit(username.charAt(i)))) {
                return false;
            }
        }
        return true;
    }


    //CHECK IF EMAIL IS VALID FORMAT (EXAMPLE @ EXAMPLE . COM)
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    //CHECKS IF ALL FIELDS ARE VALID
    private boolean allFieldsValid(){


        boolean allFieldsValid = true;

        //HIDES VALIDATION WARNINGS
        tvEmailWarning.setVisibility(View.GONE);
        tvUsernameWarning.setVisibility(View.GONE);
        tvPasswordWarning.setVisibility(View.GONE);
        tvPasswordConfirmWarning.setVisibility(View.GONE);

        //USERNAME VALIDATION
        if (username.equals("")) {
            tvUsernameWarning.setTextColor(Color.RED);
            tvUsernameWarning.setVisibility(View.VISIBLE);
            editTextUsername.setBackgroundColor(Color.parseColor("#B3eb4034"));
            tvUsernameWarning.setText("Username field cannot be empty");
            allFieldsValid = false;
        }
        if (username.length() > 16 || username.length() <3) {
            tvUsernameWarning.setTextColor(Color.RED);
            tvUsernameWarning.setVisibility(View.VISIBLE);
            editTextUsername.setBackgroundColor(Color.parseColor("#B3eb4034"));
            tvUsernameWarning.setText("Username must contain 3-16 characters");
            allFieldsValid = false;
        }
        if (!isValidUsername(username)){
            tvUsernameWarning.setTextColor(Color.RED);
            tvUsernameWarning.setVisibility(View.VISIBLE);
            editTextUsername.setBackgroundColor(Color.parseColor("#B3eb4034"));
            tvUsernameWarning.setText("Username can only contain letters or numbers");
            allFieldsValid = false;
        }


        //PASSWORD VALIDATION
        if (passwordConfirm.equals("")) {
            tvPasswordConfirmWarning.setTextColor(Color.RED);
            tvPasswordConfirmWarning.setVisibility(View.VISIBLE);
            editTextPasswordConfirm.setBackgroundColor(Color.parseColor("#B3eb4034"));
            tvPasswordConfirmWarning.setText("Password field cannot be empty");
            allFieldsValid = false;
        }
        if (password.equals("")) {
            tvPasswordWarning.setTextColor(Color.RED);
            tvPasswordWarning.setVisibility(View.VISIBLE);
            editTextPassword.setBackgroundColor(Color.parseColor("#B3eb4034"));
            tvPasswordWarning.setText("Password field cannot be empty");
            allFieldsValid = false;
        }
        if (password.length() < 6) {
            tvPasswordWarning.setTextColor(Color.RED);
            tvPasswordWarning.setVisibility(View.VISIBLE);
            editTextPassword.setBackgroundColor(Color.parseColor("#B3eb4034"));
            tvPasswordWarning.setText("Password must contain a minimum of 6 characters");
            tvPasswordConfirmWarning.setTextColor(Color.RED);
            tvPasswordConfirmWarning.setVisibility(View.VISIBLE);
            editTextPasswordConfirm.setBackgroundColor(Color.parseColor("#B3eb4034"));
            tvPasswordConfirmWarning.setText("Password must contain a minimum of 6 characters");
            allFieldsValid = false;
        }
        if (password.length() > 32) {
            tvPasswordWarning.setTextColor(Color.RED);
            tvPasswordWarning.setVisibility(View.VISIBLE);
            editTextPassword.setBackgroundColor(Color.parseColor("#B3eb4034"));
            tvPasswordWarning.setText("Password must not exceed 32 characters");
            tvPasswordConfirmWarning.setTextColor(Color.RED);
            tvPasswordConfirmWarning.setVisibility(View.VISIBLE);
            editTextPasswordConfirm.setBackgroundColor(Color.parseColor("#B3eb4034"));
            tvPasswordConfirmWarning.setText("Password must not exceed 32 characters");
            allFieldsValid = false;
        }
        if (!isValidPassword(password)){
            tvPasswordWarning.setTextColor(Color.RED);
            tvPasswordWarning.setVisibility(View.VISIBLE);
            editTextPassword.setBackgroundColor(Color.parseColor("#B3eb4034"));
            tvPasswordWarning.setText("Password must contain a lowercase, uppercase letter and number");
            tvPasswordConfirmWarning.setTextColor(Color.RED);
            tvPasswordConfirmWarning.setVisibility(View.VISIBLE);
            editTextPasswordConfirm.setBackgroundColor(Color.parseColor("#B3eb4034"));
            tvPasswordWarning.setText("Password must contain a lowercase, uppercase letter and number");
            allFieldsValid = false;
        }
        if  (!password.equals(passwordConfirm)) {
            tvPasswordWarning.setTextColor(Color.RED);
            tvPasswordWarning.setVisibility(View.VISIBLE);
            editTextPassword.setBackgroundColor(Color.parseColor("#B3eb4034"));
            tvPasswordWarning.setText("Password does not match");
            tvPasswordConfirmWarning.setTextColor(Color.RED);
            tvPasswordConfirmWarning.setVisibility(View.VISIBLE);
            editTextPasswordConfirm.setBackgroundColor(Color.parseColor("#B3eb4034"));
            tvPasswordConfirmWarning.setText("Password does not match");
            allFieldsValid = false;
        }


        //EMAIL VALIDATION
        if (email.equals("")) {
            tvEmailWarning.setTextColor(Color.RED);
            tvEmailWarning.setVisibility(View.VISIBLE);
            editTextEmail.setBackgroundColor(Color.parseColor("#B3eb4034"));
            tvEmailWarning.setText("E-mail field cannot be empty");
            allFieldsValid = false;
        }
        if (!isValidEmail(email)) {
            tvEmailWarning.setTextColor(Color.RED);
            tvEmailWarning.setVisibility(View.VISIBLE);
            editTextEmail.setBackgroundColor(Color.parseColor("#B3eb4034"));
            tvEmailWarning.setText("E-mail is invalid");
            allFieldsValid = false;
        }


        return allFieldsValid;
    }


    //Begin registration process
    private void attemptRegister(){

        //MAKES PROGRESS BAR APPEAR, AND OTHER OBJECTS DISAPPEAR
        showProgressBar();

        //HIDES INPUT WARNINGS (ONLY IF VISIBLE)
        tvEmailWarning.setVisibility(View.INVISIBLE);
        tvUsernameWarning.setVisibility(View.INVISIBLE);
        tvPasswordWarning.setVisibility(View.INVISIBLE);
        tvPasswordConfirmWarning.setVisibility(View.INVISIBLE);


        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[4];
                field[0] = "username";
                field[1] = "password";
                field[2] = "email";
                field[3] = "date";
                //Creating array for data
                String[] data = new String[4];
                data[0] = username;
                data[1] = password;
                data[2] = email;
                data[3] = Methods.getDate();


                PostData postData = new PostData(getResources().getString(R.string.signUp), "POST", field, data);

                if (postData.startPut()) {
                    if (postData.onComplete()) {
                        String result = postData.getResult();
                        if (result.equals("Sign Up Success")) { //IF SIGN UP SUCCESS, CONFIRM REGISTRATION AND RETURN TO LOGIN
                            registrationSuccess();
                        }
                        //CHECK IF USERNAME IS ALREADY TAKEN
                        if (result.equals("That username is taken. Try another.")){
                            hideProgressBar();
                            tvUsernameWarning.setTextColor(Color.RED);
                            tvUsernameWarning.setVisibility(View.VISIBLE);
                            editTextUsername.setBackgroundColor(Color.parseColor("#B3eb4034"));
                            tvUsernameWarning.setText(result);
                        }
                        //CHECK IF EMAIL IS ALREADY TAKEN
                        if (result.equals("That email is taken. Try another.")){
                            hideProgressBar();
                            tvEmailWarning.setTextColor(Color.RED);
                            tvEmailWarning.setVisibility(View.VISIBLE);
                            editTextEmail.setBackgroundColor(Color.parseColor("#B3eb4034"));
                            tvEmailWarning.setText(result);
                        }


                        else{
                            //MAKES PROGRESS BAR DISAPPEAR, AND OTHER OBJECTS APPEAR
                            hideProgressBar();

                            //DISPLAYS RETURN MESSAGE
                            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                        }

                    }}}
        });


    }


    //Sets value from edit text fields and passes into variables
    private void setValuesFromFields(){
        //Takes value of usernameRaw and converts to lower case
        usernameRaw = String.valueOf(editTextUsername.getText());
        username = usernameRaw.toLowerCase();
        password = String.valueOf(editTextPassword.getText());
        passwordConfirm = String.valueOf(editTextPasswordConfirm.getText());
        email = String.valueOf(editTextEmail.getText());
    }

    //MAKES PROGRESS BAR APPEAR, AND OTHER OBJECTS DISAPPEAR
    public void showProgressBar(){
        pbProgressBar.setVisibility(View.VISIBLE);
        tvProgressBarText.setVisibility(View.VISIBLE);
        editTextUsername.setVisibility(View.INVISIBLE);
        editTextPassword.setVisibility(View.INVISIBLE);
        editTextEmail.setVisibility(View.INVISIBLE);
        editTextPasswordConfirm.setVisibility(View.INVISIBLE);
        registerButton.setVisibility(View.INVISIBLE);
    }

    //MAKES PROGRESS BAR DISAPPEAR, AND OTHER OBJECTS APPEAR
    public void hideProgressBar(){
        pbProgressBar.setVisibility(View.INVISIBLE);
        tvProgressBarText.setVisibility(View.INVISIBLE);
        editTextUsername.setVisibility(View.VISIBLE);
        editTextPassword.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.VISIBLE);
    }


}
