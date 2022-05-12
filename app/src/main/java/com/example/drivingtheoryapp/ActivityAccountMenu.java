package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityAccountMenu extends AppCompatActivity implements DialogDeleteAccount.ExampleDialogListener {

    private String username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_menu);

        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        username = intent.getStringExtra("username_key");


        //Declaring buttons
        CardView trackProgressCV = (CardView) findViewById(R.id.trackProgress);
        CardView deleteAccountCV = (CardView) findViewById(R.id.deleteAccount);
        CardView changePasswordCV = (CardView) findViewById(R.id.changePassword);



        //Button Listeners
        trackProgressCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProgress(username);
            }
        });
        deleteAccountCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount(username);
            }
        });
        changePasswordCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword(username);
            }
        });



    }

    //Button Actions
    public void openProgress(String passUsername) {
        Intent intent = new Intent(this, ActivityResultsAll.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);
        finish();
    }

    public void deleteAccount(String passUsername) {
        openDialog();
    }

    public void changePassword(String passUsername) {
        Intent intent = new Intent(this, ActivityChangePassword.class);
        intent.putExtra("username_key",passUsername);
        startActivity(intent);
        finish();
    }



    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ActivityAccountMenu.this, ActivityMainMenu.class);
        intent.putExtra("username_key",username);
        startActivity(intent);
        finish();
    }

    //OPENING DIALOG
    public void openDialog() {
        DialogDeleteAccount dialogDeleteAccount = new DialogDeleteAccount();
        dialogDeleteAccount.show(getSupportFragmentManager(), "example dialog");
    }


    //APPLY CHOICE OF DIALOG BOX
    @Override
    public void applyChoice() {
        confirmDelete(username);
    }


    public void confirmDelete(String username){

        //Prohibits deletion of administrator or guest account
        if(username.equals("admin") || username.equals("guest")){
            Toast.makeText(getApplicationContext(),"Unable to delete administrator account!",Toast.LENGTH_SHORT).show();
        }
        else {


            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {

                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[1];
                    field[0] = "username";

                    //Creating array for data
                    String[] data = new String[1];
                    data[0] = username;

                    PostData postData = new PostData(getResources().getString(R.string.deleteAccount), "POST", field, data);

                    if (postData.startPut()) {
                        if (postData.onComplete()) {
                            String result = postData.getResult();
                            if (result.equals("success")) {
                                Intent intent = new Intent(ActivityAccountMenu.this, ActivityLogin.class);
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
    }
}
