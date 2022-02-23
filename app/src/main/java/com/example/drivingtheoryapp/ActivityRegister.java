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


            }
        });
    }








}