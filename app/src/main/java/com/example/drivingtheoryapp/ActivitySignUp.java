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
import com.google.android.material.textfield.TextInputEditText;

// Making HttpURLConnection faster, easy and secure. Best method to impliment httpurlconnection in android.
// https://github.com/VishnuSivadasVS/Advanced-HttpURLConnection/
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class ActivitySignUp extends AppCompatActivity {

    EditText EditTextFullname, EditTextUsername, EditTextPassword, EditTextEmail;
    Button buttonSingUp;
    TextView textViewLogin,tvFullNameWarning,tvEmailWarning,tvUsernameWarning,tvPasswordWarning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditTextUsername = findViewById(R.id.username);
        EditTextFullname = findViewById(R.id.fullname);
        EditTextPassword = findViewById(R.id.password);
        EditTextEmail = findViewById(R.id.email);

        buttonSingUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.LoginText);
        tvFullNameWarning = findViewById(R.id.tvFullNameWarning);
        tvEmailWarning = findViewById(R.id.tvEmailWarning);
        tvUsernameWarning = findViewById(R.id.tvUsernameWarning);
        tvPasswordWarning = findViewById(R.id.tvPasswordWarning);

        tvFullNameWarning.setVisibility(View.GONE);
        tvEmailWarning.setVisibility(View.GONE);
        tvUsernameWarning.setVisibility(View.GONE);
        tvPasswordWarning.setVisibility(View.GONE);




        buttonSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullname, username, usernameraw, password, email;
                fullname = String.valueOf(EditTextFullname.getText());
                boolean allFieldsValid = true;

                //Takes value of usernamerawdata and converts to lower case
                usernameraw = String.valueOf(EditTextUsername.getText());
                username = usernameraw.toLowerCase();
                password = String.valueOf(EditTextPassword.getText());
                email = String.valueOf(EditTextEmail.getText());
               // String usernameSize = username.chars();

                tvFullNameWarning.setVisibility(View.GONE);
                tvEmailWarning.setVisibility(View.GONE);
                tvUsernameWarning.setVisibility(View.GONE);
                tvPasswordWarning.setVisibility(View.GONE);


                if (fullname.equals("")) {
                    tvFullNameWarning.setTextColor(Color.RED);
                    tvFullNameWarning.setVisibility(View.VISIBLE);
                    tvFullNameWarning.setText("Name field cannot be empty");
                    allFieldsValid = false;}
                if (fullname.length() > 20) {
                    tvFullNameWarning.setTextColor(Color.RED);
                    tvFullNameWarning.setVisibility(View.VISIBLE);
                    tvFullNameWarning.setText("Name cannot exceed 20 characters");
                    allFieldsValid = false;}

                if (username.equals("")) {
                    tvUsernameWarning.setTextColor(Color.RED);
                    tvUsernameWarning.setVisibility(View.VISIBLE);
                    tvUsernameWarning.setText("Username field cannot be emply");
                    allFieldsValid = false;}
                if (username.length() > 8) {
                    tvUsernameWarning.setTextColor(Color.RED);
                    tvUsernameWarning.setVisibility(View.VISIBLE);
                    tvUsernameWarning.setText("Username cannot exceed 8 characters");
                    allFieldsValid = false;}

                if (password.equals("")) {
                    tvPasswordWarning.setTextColor(Color.RED);
                    tvPasswordWarning.setVisibility(View.VISIBLE);
                    tvPasswordWarning.setText("Password field cannot be empty");
                    allFieldsValid = false;}
                if (password.length() < 8 || password.length() >20) {
                    tvPasswordWarning.setTextColor(Color.RED);
                    tvPasswordWarning.setVisibility(View.VISIBLE);
                    tvPasswordWarning.setText("Password must contain 8-20 characters");
                    allFieldsValid = false;}

                if (email.equals("")) {
                    tvEmailWarning.setTextColor(Color.RED);
                    tvEmailWarning.setVisibility(View.VISIBLE);
                    tvEmailWarning.setText("E-mail field cannot be empty");
                    allFieldsValid = false;
                }
                if (!isValidEmail(email)) {
                    tvEmailWarning.setTextColor(Color.RED);
                    tvEmailWarning.setVisibility(View.VISIBLE);
                    tvEmailWarning.setText("Invalid e-mail format");
                    allFieldsValid = false;
                }




              if(!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("") && allFieldsValid) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "email";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = password;
                            data[3] = email;

                            PutData putData = new PutData("http://192.168.1.97/LoginRegister/signup.php", "POST", field, data);

                            if (putData.startPut()) {
                                if (putData.onComplete()) {

                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")) {
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), ActivityLoginScreen.class);
                                        startActivity(intent);
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
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}