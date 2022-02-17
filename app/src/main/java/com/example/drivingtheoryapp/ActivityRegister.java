package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

// Making HttpURLConnection faster, easy and secure. Best method to impliment httpurlconnection in android.
// https://github.com/VishnuSivadasVS/Advanced-HttpURLConnection/


public class ActivityRegister extends AppCompatActivity {

    EditText EditTextUsername, EditTextPassword, EditTextPasswordConfirm, EditTextEmail;
    Button buttonSingUp;
    TextView textViewLogin,tvFullNameWarning,tvEmailWarning,tvUsernameWarning,tvPasswordWarning,tvPasswordConfirmWarning;
    private ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        EditTextUsername = findViewById(R.id.etUsername);
        EditTextPassword = findViewById(R.id.etPassword);
        EditTextPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        EditTextEmail = findViewById(R.id.etEmail);

        buttonSingUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.LoginText);
        tvEmailWarning = findViewById(R.id.tvEmailWarning);
        tvUsernameWarning = findViewById(R.id.tvUsernameWarning);
        tvPasswordWarning = findViewById(R.id.tvPasswordWarning);
        tvPasswordConfirmWarning = findViewById(R.id.tvPasswordConfirmWarning);

        tvEmailWarning.setVisibility(View.GONE);
        tvUsernameWarning.setVisibility(View.GONE);
        tvPasswordWarning.setVisibility(View.GONE);
        tvPasswordConfirmWarning.setVisibility(View.GONE);


        buttonSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //DECLARING VARIABLES
                String username, usernameRaw, password,passwordConfirm, email;
                boolean allFieldsValid = true;

                //Takes value of usernameRaw and converts to lower case
                usernameRaw = String.valueOf(EditTextUsername.getText());
                username = usernameRaw.toLowerCase();
                password = String.valueOf(EditTextPassword.getText());
                passwordConfirm = String.valueOf(EditTextPasswordConfirm.getText());
                email = String.valueOf(EditTextEmail.getText());


                //HIDES VALIDATION WARNINGS
                tvEmailWarning.setVisibility(View.GONE);
                tvUsernameWarning.setVisibility(View.GONE);
                tvPasswordWarning.setVisibility(View.GONE);
                tvPasswordConfirmWarning.setVisibility(View.GONE);


                //USERNAME VALIDATION
                if (username.equals("")) {
                    tvUsernameWarning.setTextColor(Color.RED);
                    tvUsernameWarning.setVisibility(View.VISIBLE);
                    tvUsernameWarning.setText("Username field cannot be empty");
                    allFieldsValid = false;}
                if (username.length() > 16 || username.length() <3) {
                    tvUsernameWarning.setTextColor(Color.RED);
                    tvUsernameWarning.setVisibility(View.VISIBLE);
                    tvUsernameWarning.setText("Username must contain 3-16 characters");
                    allFieldsValid = false;}

                //PASSWORD VALIDATION
                if  (!password.equals(passwordConfirm)) {
                    tvPasswordWarning.setTextColor(Color.RED);
                    tvPasswordWarning.setVisibility(View.VISIBLE);
                    tvPasswordWarning.setText("Password does not match");
                    tvPasswordConfirmWarning.setTextColor(Color.RED);
                    tvPasswordConfirmWarning.setVisibility(View.VISIBLE);
                    tvPasswordConfirmWarning.setText("Password does not match");
                    allFieldsValid = false;
                    }
                if (password.equals("")) {
                    tvPasswordWarning.setTextColor(Color.RED);
                    tvPasswordWarning.setVisibility(View.VISIBLE);
                    tvPasswordWarning.setText("Password field cannot be empty");
                    allFieldsValid = false;
                    }
                if (password.length() < 8 || password.length() > 20) {
                    tvPasswordWarning.setTextColor(Color.RED);
                    tvPasswordWarning.setVisibility(View.VISIBLE);
                    tvPasswordWarning.setText("Password must contain 8-20 characters");
                    allFieldsValid = false;
                    }
                if (passwordConfirm.equals("")) {
                    tvPasswordConfirmWarning.setTextColor(Color.RED);
                    tvPasswordConfirmWarning.setVisibility(View.VISIBLE);
                    tvPasswordConfirmWarning.setText("Password field cannot be empty");
                    allFieldsValid = false;
                }
                if (passwordConfirm.length() < 8 || password.length() > 20) {
                    tvPasswordConfirmWarning.setTextColor(Color.RED);
                    tvPasswordConfirmWarning.setVisibility(View.VISIBLE);
                    tvPasswordConfirmWarning.setText("Password must contain 8-20 characters");
                    allFieldsValid = false;
                }
                //EMAIL VALIDATION
                if (email.equals("")) {
                    tvEmailWarning.setTextColor(Color.RED);
                    tvEmailWarning.setVisibility(View.VISIBLE);
                    tvEmailWarning.setText("E-mail field cannot be empty");
                    allFieldsValid = false;
                }
                if (!isValidEmail(email)) {
                    tvEmailWarning.setTextColor(Color.RED);
                    tvEmailWarning.setVisibility(View.VISIBLE);
                    tvEmailWarning.setText("E-mail is invalid");
                    allFieldsValid = false;
                }



              //IF ALL FIELDS ARE NOT EMPTY AND HAVE PASSED INITIAL DATA VALIDATION
              if(!username.equals("") && !password.equals("") && !passwordConfirm.equals("") && !email.equals("") && allFieldsValid) {

                  //MAKES PROGRESS BAR APPEAR, AND OTHER OBJECTS DISAPPEAR
                  spinner.setVisibility(View.VISIBLE);
                  EditTextUsername.setVisibility(View.INVISIBLE);
                  EditTextPassword.setVisibility(View.INVISIBLE);
                  EditTextPasswordConfirm.setVisibility(View.INVISIBLE);
                  EditTextEmail.setVisibility(View.INVISIBLE);
                  buttonSingUp.setVisibility(View.INVISIBLE);
                  textViewLogin.setVisibility(View.INVISIBLE);
                  tvEmailWarning.setVisibility(View.INVISIBLE);
                  tvUsernameWarning.setVisibility(View.INVISIBLE);
                  tvPasswordWarning.setVisibility(View.INVISIBLE);
                  tvPasswordConfirmWarning.setVisibility(View.INVISIBLE);


                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Assign current date and time to string
                            Date today = new Date();
                            SimpleDateFormat format = new SimpleDateFormat("MMMM dd yyyy ' @ ' hh:mm a");
                            String date = format.format(today);

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
                            data[3] = date;


                            PostData postData = new PostData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/signup.php", "POST", field, data);

                            if (postData.startPut()) {
                                if (postData.onComplete()) {
                                    String result = postData.getResult();
                                    if (result.equals("Sign Up Success")) {
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    else{
                                        //MAKES PROGRESS BAR DISAPPEAR, AND OTHER OBJECTS APPEAR
                                        spinner.setVisibility(View.INVISIBLE);
                                        EditTextUsername.setVisibility(View.VISIBLE);
                                        EditTextPassword.setVisibility(View.VISIBLE);
                                        EditTextPasswordConfirm.setVisibility(View.VISIBLE);
                                        EditTextEmail.setVisibility(View.VISIBLE);
                                        buttonSingUp.setVisibility(View.VISIBLE);
                                        textViewLogin.setVisibility(View.VISIBLE);
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();

                            }

                        }}}
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();

                  //MAKES PROGRESS BAR DISAPPEAR, AND OTHER OBJECTS APPEAR
                  spinner.setVisibility(View.INVISIBLE);
                  EditTextUsername.setVisibility(View.VISIBLE);
                  EditTextPassword.setVisibility(View.VISIBLE);
                  EditTextPasswordConfirm.setVisibility(View.VISIBLE);
                  EditTextEmail.setVisibility(View.VISIBLE);
                  buttonSingUp.setVisibility(View.VISIBLE);
                  textViewLogin.setVisibility(View.VISIBLE);

                }
            }
        });
    }


    //FUNCTION TO CHECK IF EMAIL IS VALID
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }





}