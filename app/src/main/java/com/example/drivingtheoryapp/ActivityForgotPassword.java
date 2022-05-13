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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityForgotPassword extends AppCompatActivity {

    private EditText editTextEmail;
    private Button resetPasswordButton;
    private String email;
    private TextView tvEmailWarning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        //ASSIGNING VARIABLES TO ID
        editTextEmail = findViewById(R.id.editTextEmail);
        tvEmailWarning = findViewById(R.id.tvEmailWarning);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);

        tvEmailWarning.setVisibility(View.GONE); //




        //BUTTON LISTENER
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValuesFromFields(); //Sets value from edit text fields and passes into variables

                //IF ALL FIELDS ARE NOT EMPTY AND HAVE PASSED INITIAL DATA VALIDATION
                if(allFieldsValid()) {

                    resetEmail(); //Begin email reset process

                }
            }
        });


    }


    //Reset email function - uses PHPMailer
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
                    data[0] = email;



                    PostData postData = new PostData(getResources().getString(R.string.resetPassword), "POST", field, data);

                    if (postData.startPut()) {
                        if (postData.onComplete()) {
                            Toast.makeText(getApplicationContext(), "Please check your email inbox", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ActivityForgotPassword.this, ActivityLogin.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            });
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


    //Sets value from edit text fields and passes into variables
    private void setValuesFromFields(){
        email = String.valueOf(editTextEmail.getText());
    }


    //EXIT TO LOGIN ON BACK PRESS
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityForgotPassword.this, ActivityLogin.class);
        startActivity(intent);
        finish();
    }

}