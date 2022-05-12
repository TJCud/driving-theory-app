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

public class ActivityChangePassword extends AppCompatActivity {

    public EditText etNewPassword, etNewPasswordConfirm;
    public Button updatePasswordButton;
    private TextView tvPasswordConfirmWarning,tvPasswordWarning;
    private String password, passwordConfirm;
    boolean allFieldsValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etNewPassword = findViewById(R.id.etNewPassword);
        etNewPasswordConfirm = findViewById(R.id.etNewPasswordConfirm);
        updatePasswordButton = findViewById(R.id.updatePasswordButton);
        tvPasswordConfirmWarning = findViewById(R.id.tvPasswordConfirmWarning);
        tvPasswordWarning = findViewById(R.id.tvPasswordWarning);

        //HIDING INPUT WARNINGS
        tvPasswordWarning.setVisibility(View.GONE);
        tvPasswordConfirmWarning.setVisibility(View.GONE);

        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        String username = intent.getStringExtra("username_key");


        updatePasswordButton.setOnClickListener(new View.OnClickListener() {  //PROCEED TO MAIN MENU AS A GUEST USER
            @Override
            public void onClick(View view) {




                password = String.valueOf(etNewPassword.getText());
                passwordConfirm = String.valueOf(etNewPasswordConfirm.getText());
                allFieldsValid = true;




                //PASSWORD VALIDATION
                if (passwordConfirm.equals("")) {
                    tvPasswordConfirmWarning.setTextColor(Color.RED);
                    tvPasswordConfirmWarning.setVisibility(View.VISIBLE);
                    etNewPasswordConfirm.setBackgroundColor(Color.parseColor("#B3eb4034"));
                    tvPasswordConfirmWarning.setText("Password field cannot be empty");
                    allFieldsValid = false;
                }
                if (password.equals("")) {
                    tvPasswordWarning.setTextColor(Color.RED);
                    tvPasswordWarning.setVisibility(View.VISIBLE);
                    etNewPassword.setBackgroundColor(Color.parseColor("#B3eb4034"));
                    tvPasswordWarning.setText("Password field cannot be empty");
                    allFieldsValid = false;
                }
                if (password.length() < 6) {
                    tvPasswordWarning.setTextColor(Color.RED);
                    tvPasswordWarning.setVisibility(View.VISIBLE);
                    etNewPassword.setBackgroundColor(Color.parseColor("#B3eb4034"));
                    tvPasswordWarning.setText("Password must contain a minimum of 6 characters");
                    tvPasswordConfirmWarning.setTextColor(Color.RED);
                    tvPasswordConfirmWarning.setVisibility(View.VISIBLE);
                    etNewPasswordConfirm.setBackgroundColor(Color.parseColor("#B3eb4034"));
                    tvPasswordConfirmWarning.setText("Password must contain a minimum of 6 characters");
                    allFieldsValid = false;
                }
                if (password.length() > 32) {
                    tvPasswordWarning.setTextColor(Color.RED);
                    tvPasswordWarning.setVisibility(View.VISIBLE);
                    etNewPassword.setBackgroundColor(Color.parseColor("#B3eb4034"));
                    tvPasswordWarning.setText("Password must not exceed 32 characters");

                    tvPasswordConfirmWarning.setTextColor(Color.RED);
                    tvPasswordConfirmWarning.setVisibility(View.VISIBLE);
                    etNewPasswordConfirm.setBackgroundColor(Color.parseColor("#B3eb4034"));
                    tvPasswordConfirmWarning.setText("Password must not exceed 32 characters");
                    allFieldsValid = false;
                }
                if (!isValidPassword(password)){
                    tvPasswordWarning.setTextColor(Color.RED);
                    tvPasswordWarning.setVisibility(View.VISIBLE);
                    etNewPassword.setBackgroundColor(Color.parseColor("#B3eb4034"));
                    tvPasswordWarning.setText("Password must contain a lowercase, uppercase letter and number");
                    tvPasswordConfirmWarning.setTextColor(Color.RED);
                    tvPasswordConfirmWarning.setVisibility(View.VISIBLE);
                    etNewPasswordConfirm.setBackgroundColor(Color.parseColor("#B3eb4034"));
                    tvPasswordWarning.setText("Password must contain a lowercase, uppercase letter and number");
                    allFieldsValid = false;
                }
                if  (!password.equals(passwordConfirm)) {
                    tvPasswordWarning.setTextColor(Color.RED);
                    tvPasswordWarning.setVisibility(View.VISIBLE);
                    etNewPassword.setBackgroundColor(Color.parseColor("#B3eb4034"));
                    tvPasswordWarning.setText("Password does not match");
                    tvPasswordConfirmWarning.setTextColor(Color.RED);
                    tvPasswordConfirmWarning.setVisibility(View.VISIBLE);
                    etNewPasswordConfirm.setBackgroundColor(Color.parseColor("#B3eb4034"));
                    tvPasswordConfirmWarning.setText("Password does not match");
                    allFieldsValid = false;
                }




                //IF ALL FIELDS ARE NOT EMPTY AND HAVE PASSED INITIAL DATA VALIDATION
                if(allFieldsValid) {
                    updatePassword(username);
                }
                else {
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                }
            }


        });




        etNewPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etNewPassword.setBackgroundColor(Color.parseColor("#B3ffffff"));
                }
            }
        });
        etNewPasswordConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etNewPasswordConfirm.setBackgroundColor(Color.parseColor("#B3ffffff"));
                }
            }
        });

    }


    public void updatePassword(String username){

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




                    PostData postData = new PostData(getResources().getString(R.string.updatePasswordURL), "POST", field, data);

                    if (postData.startPut()) {
                        if (postData.onComplete()) {
                            String result = postData.getResult();
                            if (result.equals("Password Successfully Updated")) {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ActivityChangePassword.this, ActivityAccountMenu.class);
                                intent.putExtra("username_key",username);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                }
            });
    }

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

}