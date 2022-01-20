package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Locale;

public class Login extends AppCompatActivity {

    EditText EditTextUsername, EditTextPassword;
    Button buttonLogin;
    TextView textViewSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditTextUsername = findViewById(R.id.username);
        EditTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.loginbtn);





        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

        Button loginbtn = (Button) findViewById(R.id.loginbtn);
        Button registerbtn = (Button) findViewById(R.id.registerbtn);

        //admin and admin

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String usernamerawdata, username, password;

                //Takes value of usernamerawdata and converts to lower case
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

                                        //FOR PASSING USERNAME TO OTHER ACTIVITIES
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Menu.class);
                                        intent.putExtra("key",username);
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


        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });




    }

    public void openMenu(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void openRegister(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}