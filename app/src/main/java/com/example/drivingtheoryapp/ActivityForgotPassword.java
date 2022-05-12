package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityForgotPassword extends AppCompatActivity {

    public EditText editTextEmail;
    public Button resetPasswordButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail = findViewById(R.id.editTextEmail);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);




        resetPasswordButton.setOnClickListener(new View.OnClickListener() {  //PROCEED TO MAIN MENU AS A GUEST USER
            @Override
            public void onClick(View view) {
                resetEmail();
            }
        });





    }


    public void resetEmail(){

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {

                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[1];
                    field[0] = "email";


                    //Creating array for data
                    String[] data = new String[1];
                    data[0] = String.valueOf(editTextEmail.getText());



                    PostData postData = new PostData(getResources().getString(R.string.resetPassword), "POST", field, data);

                    if (postData.startPut()) {
                        if (postData.onComplete()) {
                            String result = postData.getResult();
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ActivityForgotPassword.this, ActivityLogin.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            });
        }



}