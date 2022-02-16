package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class ActivityLogin extends AppCompatActivity {

    EditText EditTextUsername, EditTextPassword;
    Button loginButton, registerButton;
    private long pressedTime;
    private ProgressBar pbProgressBar;
    private TextView tvHeader, tvProgressBarText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        pbProgressBar=(ProgressBar)findViewById(R.id.pbProgressBar);
        tvProgressBarText = findViewById(R.id.tvProgressBarText);
        pbProgressBar.setVisibility(View.GONE);
        tvProgressBarText.setVisibility(View.GONE);
        TextView guestUser = findViewById(R.id.ID_guest);
        EditTextUsername = findViewById(R.id.username);
        EditTextPassword = findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginbtn);
        registerButton = (Button) findViewById(R.id.registerbtn);
        tvHeader = findViewById(R.id.signintext);




        //BUTTON LISTENERS
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernamerawdata, username, password;

                //Takes value of 'usernamerawdata' and converts to lower case
                usernamerawdata = String.valueOf(EditTextUsername.getText());
                password = String.valueOf(EditTextPassword.getText());
                username = usernamerawdata.toLowerCase();

                //MAKES PROGRESS BAR APPEAR, AND OTHER OBJECTS DISAPPEAR
                pbProgressBar.setVisibility(View.VISIBLE);
                tvProgressBarText.setVisibility(View.VISIBLE);
                EditTextUsername.setVisibility(View.INVISIBLE);
                EditTextPassword.setVisibility(View.INVISIBLE);
                loginButton.setVisibility(View.INVISIBLE);
                registerButton.setVisibility(View.INVISIBLE);
                guestUser.setVisibility(View.INVISIBLE);
                tvHeader.setVisibility(View.INVISIBLE);
/*                tvUsernameWarning.setVisibility(View.INVISIBLE);
                tvPasswordWarning.setVisibility(View.INVISIBLE);*/


                if(!username.equals("") && !password.equals("")) {
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

                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        openMainMenu(username);
                                        finish();
                                    }

                                    else{
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        //MAKES PROGRESS BAR DISAPPEAR, AND OTHER OBJECTS REAPPEAR
                                        pbProgressBar.setVisibility(View.INVISIBLE);
                                        tvProgressBarText.setVisibility(View.INVISIBLE);
                                        EditTextUsername.setVisibility(View.VISIBLE);
                                        EditTextPassword.setVisibility(View.VISIBLE);
                                        loginButton.setVisibility(View.VISIBLE);
                                        registerButton.setVisibility(View.VISIBLE);
                                        guestUser.setVisibility(View.VISIBLE);
                                        tvHeader.setVisibility(View.VISIBLE);

                                    }

                                }}}
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                    //MAKES PROGRESS BAR DISAPPEAR, AND OTHER OBJECTS REAPPEAR
                    pbProgressBar.setVisibility(View.INVISIBLE);
                    tvProgressBarText.setVisibility(View.INVISIBLE);
                    EditTextUsername.setVisibility(View.VISIBLE);
                    EditTextPassword.setVisibility(View.VISIBLE);
                    loginButton.setVisibility(View.VISIBLE);
                    registerButton.setVisibility(View.VISIBLE);
                    guestUser.setVisibility(View.VISIBLE);
                    tvHeader.setVisibility(View.VISIBLE);
                }
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });


        guestUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainMenu("Guest");
                finish();
            }
        });

    }


    //BUTTON ACTIONS
    public void openRegister(){
        Intent intent = new Intent(this, ActivityRegister.class);
        startActivity(intent);
    }


    public void openMainMenu(String passUsername){
        //FOR PASSING USERNAME TO OTHER ACTIVITIES
        Intent openMenu = new Intent(getApplicationContext(), ActivityMainMenu.class);
        openMenu.putExtra("username_key",passUsername);
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
}