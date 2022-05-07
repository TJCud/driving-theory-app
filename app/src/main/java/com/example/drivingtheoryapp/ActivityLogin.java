package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ActivityLogin extends AppCompatActivity {

    EditText EditTextUsername, EditTextPassword, EditTextPasswordConfirm, EditTextEmail;
    Button signInButton, registerButton;
    private boolean regFieldsOpen;
    private long pressedTime;
    private ProgressBar pbProgressBar;
    private TextView tvProgressBarText,tvEmailWarning,tvUsernameWarning,tvPasswordWarning,tvPasswordConfirmWarning,tvGuestUser;




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
        tvGuestUser = findViewById(R.id.tvGuestUser);
        EditTextUsername = findViewById(R.id.etUsername);
        EditTextPassword = findViewById(R.id.etPassword);
        EditTextPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        EditTextEmail = findViewById(R.id.etEmail);
        signInButton = (Button) findViewById(R.id.signInBtn);
        registerButton = (Button) findViewById(R.id.registerBtn);
        tvEmailWarning = findViewById(R.id.tvEmailWarning);
        tvUsernameWarning = findViewById(R.id.tvUsernameWarning);
        tvPasswordWarning = findViewById(R.id.tvPasswordWarning);
        tvPasswordConfirmWarning = findViewById(R.id.tvPasswordConfirmWarning);

        //HIDING INPUT WARNINGS
        tvEmailWarning.setVisibility(View.GONE);
        tvUsernameWarning.setVisibility(View.GONE);
        tvPasswordWarning.setVisibility(View.GONE);
        tvPasswordConfirmWarning.setVisibility(View.GONE);

        //HIDE INPUTS FOR REGISTRATION
        EditTextEmail.setVisibility(View.GONE);
        EditTextPasswordConfirm.setVisibility(View.GONE);
        regFieldsOpen = false;



        //BUTTON LISTENERS
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //IF REGISTRATION FIELDS ARE VISIBLE, HIDE THEM
                if (regFieldsOpen) {
                    EditTextEmail.setVisibility(View.GONE);
                    EditTextPasswordConfirm.setVisibility(View.GONE);
                    tvUsernameWarning.setVisibility(View.GONE);
                    tvEmailWarning.setVisibility(View.GONE);
                    tvPasswordWarning.setVisibility(View.GONE);
                    tvPasswordConfirmWarning.setVisibility(View.GONE);
                    tvGuestUser.setVisibility(View.VISIBLE);
                    registerButton.setText("REGISTER");
                    signInButton.setText("SIGN IN");
                    regFieldsOpen = false;
                } else {


                    //SIGN IN VARIABLES
                    String usernamerawdata, username, password;

                    //Takes value of 'usernamerawdata' and converts to lower case
                    usernamerawdata = String.valueOf(EditTextUsername.getText());
                    password = String.valueOf(EditTextPassword.getText());
                    username = usernamerawdata.toLowerCase();

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
                                PostData postData = new PostData("http://tcudden01.webhosting3.eeecs.qub.ac.uk/login.php", "POST", field, data);
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
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //IF REGISTRATION FIELDS ARE NOT VISIBLE, SHOW THEM
                if(!regFieldsOpen){
                EditTextEmail.setVisibility(View.VISIBLE);
                EditTextPasswordConfirm.setVisibility(View.VISIBLE);
                tvGuestUser.setVisibility(View.GONE);
                registerButton.setText("COMPLETE REGISTRATION");
                signInButton.setText("ALREADY REGISTERED? SIGN IN");
                regFieldsOpen = true;}
                else{



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
                                //Assign current date and time to string
                                Date today = new Date();
                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy '  ' hh:mm a");
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
                                            openNextMenu(username);
                                            finish();
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
                    else {
                        Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();

                        //MAKES PROGRESS BAR DISAPPEAR, AND OTHER OBJECTS APPEAR
                        hideProgressBar();


                    }
                }

            }
        });


        //PROCEED TO MAIN MENU AS A GUEST USER
        tvGuestUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextMenu("guest");
                finish();
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





    //DISABLE BACK BUTTON
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


    //FUNCTION TO CHECK IF EMAIL IS VALID
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }



    //MAKES PROGRESS BAR APPEAR, AND OTHER OBJECTS DISAPPEAR
    public void showProgressBar(){
        pbProgressBar.setVisibility(View.VISIBLE);
        tvProgressBarText.setVisibility(View.VISIBLE);
        EditTextUsername.setVisibility(View.INVISIBLE);
        EditTextPassword.setVisibility(View.INVISIBLE);
        signInButton.setVisibility(View.INVISIBLE);
        registerButton.setVisibility(View.INVISIBLE);
        tvGuestUser.setVisibility(View.INVISIBLE);
    }

    //MAKES PROGRESS BAR DISAPPEAR, AND OTHER OBJECTS APPEAR
    public void hideProgressBar(){
        pbProgressBar.setVisibility(View.INVISIBLE);
        tvProgressBarText.setVisibility(View.INVISIBLE);
        EditTextUsername.setVisibility(View.VISIBLE);
        EditTextPassword.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.VISIBLE);
        tvGuestUser.setVisibility(View.VISIBLE);
    }


}