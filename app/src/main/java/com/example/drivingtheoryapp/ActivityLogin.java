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

    public EditText editTextUsername, editTextPassword, editTextPasswordConfirm, editTextEmail;
    public Button signInButton, registerButton, guestUserBtn, forgotPasswordBtn;
    private boolean regFieldsOpen;
    private long pressedTime;
    private ProgressBar pbProgressBar;
    private TextView tvProgressBarText,tvEmailWarning,tvUsernameWarning,tvPasswordWarning,tvPasswordConfirmWarning;





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
        guestUserBtn = findViewById(R.id.guestBtn);
        editTextUsername = findViewById(R.id.etUsername);
        editTextPassword = findViewById(R.id.etPassword);
        editTextPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        editTextEmail = findViewById(R.id.etEmail);
        signInButton = (Button) findViewById(R.id.signInBtn);
        registerButton = (Button) findViewById(R.id.registerBtn);
        forgotPasswordBtn = (Button) findViewById(R.id.forgotPasswordBtn);
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
        editTextEmail.setVisibility(View.GONE);
        editTextPasswordConfirm.setVisibility(View.GONE);
        regFieldsOpen = false;




        //BUTTON LISTENERS
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //IF REGISTRATION FIELDS ARE VISIBLE, HIDE THEM
                if (regFieldsOpen) {
                    editTextEmail.setVisibility(View.GONE);
                    editTextPasswordConfirm.setVisibility(View.GONE);
                    tvUsernameWarning.setVisibility(View.GONE);
                    tvEmailWarning.setVisibility(View.GONE);
                    tvPasswordWarning.setVisibility(View.GONE);
                    tvPasswordConfirmWarning.setVisibility(View.GONE);


                    registerButton.setText("Sign Up");
                    registerButton.setBackgroundColor(0x0Dffffff);
                    registerButton.setTextColor(Color.BLACK);

                    signInButton.setText("Log In");
                    signInButton.setBackgroundColor(0x80000000);
                    signInButton.setTextColor(Color.WHITE);

                    regFieldsOpen = false;
                } else {


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
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                //IF REGISTRATION FIELDS ARE NOT VISIBLE, SHOW THEM
                if(!regFieldsOpen){
                editTextEmail.setVisibility(View.VISIBLE);
                editTextPasswordConfirm.setVisibility(View.VISIBLE);

                registerButton.setText("COMPLETE SIGN UP");
                registerButton.setBackgroundColor(Color.parseColor("#80000000"));
                registerButton.setTextColor(Color.WHITE);

                signInButton.setText("Back to Log In");
                signInButton.setBackgroundColor(0x0Dffffff);
                signInButton.setTextColor(Color.BLACK);



                
                

                regFieldsOpen = true;}
                else{



                    //DECLARING VARIABLES
                    String username, usernameRaw, password,passwordConfirm, email;
                    boolean allFieldsValid = true;

                    //Takes value of usernameRaw and converts to lower case
                    usernameRaw = String.valueOf(editTextUsername.getText());
                    username = usernameRaw.toLowerCase();
                    password = String.valueOf(editTextPassword.getText());
                    passwordConfirm = String.valueOf(editTextPasswordConfirm.getText());
                    email = String.valueOf(editTextEmail.getText());


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
                        allFieldsValid = false;}
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
                    }
                    if (password.equals("")) {
                        tvPasswordWarning.setTextColor(Color.RED);
                        tvPasswordWarning.setVisibility(View.VISIBLE);
                        editTextPassword.setBackgroundColor(Color.parseColor("#B3eb4034"));
                        tvPasswordWarning.setText("Password field cannot be empty");
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
                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy '  ' HH:mm");
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
                                //id	username	userID	questions_correct	questions_total	outcome	date	saved_question

                                PostData postData = new PostData(getResources().getString(R.string.signUp), "POST", field, data);

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
            }
        });

        
        //Set background colours back to original when user clicks on object
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


    //CHECK IF EMAIL IS VALID
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
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


}