package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Locale;

public class Login extends AppCompatActivity {

    EditText EditTextUsername, EditTextPassword;
    Button loginButton, registerButton;
    TextView textViewSignUp;
    private long pressedTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        EditTextUsername = findViewById(R.id.username);
        EditTextPassword = findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginbtn);
        registerButton = (Button) findViewById(R.id.registerbtn);



        //BUTTON LISTENERS
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernamerawdata, username, password;

                //Takes value of 'usernamerawdata' and converts to lower case
                usernamerawdata = String.valueOf(EditTextUsername.getText());
                password = String.valueOf(EditTextPassword.getText());
                username = usernamerawdata.toLowerCase();

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
                            PutData putData = new PutData("http://192.168.1.97/LoginRegister/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {

                                    String result = putData.getResult();
                                    if (result.equals("Login Success")) {

                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        openMainMenu(username);
                                        finish();
                                    }

                                    else{
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();

                                    }

                                }}}
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                }
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });

    }


    //BUTTON ACTIONS
    public void openRegister(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }


    public void openMainMenu(String passUsername){
        //FOR PASSING USERNAME TO OTHER ACTIVITIES
        Intent openMenu = new Intent(getApplicationContext(), Menu.class);
        openMenu.putExtra("username_key",passUsername);
        startActivity(openMenu);
    }



    //DISABLE BACK BUTTON
    @Override
    public void onBackPressed()
    {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();

            Intent intent = new Intent(Login.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);


        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}