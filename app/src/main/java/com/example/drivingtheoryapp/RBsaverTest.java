package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RBsaverTest extends AppCompatActivity {

    private int ival;
    public static int glob=1;
    static SharedPreferences sPref;
    private SharedPreferences.Editor sE;
    RadioGroup rbMain;
    RadioButton rb1,rb2,rb3,rb4,rb5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rbsaver_test);

        rbMain = (RadioGroup) findViewById(R.id.rgMain);
        rb1 = (RadioButton)findViewById(R.id.radio1);
        rb2 = (RadioButton)findViewById(R.id.radio2);
        rb3 = (RadioButton)findViewById(R.id.radio3);
        rb4 = (RadioButton)findViewById(R.id.radio4);
        rb5 = (RadioButton)findViewById(R.id.radio5);

        sPref = getSharedPreferences("Pref",0);
        sE = sPref.edit();
        ival = sPref.getInt("Pref", 0);

        if(ival == R.id.radio1){
            rb1.setChecked(true);
            glob=1;
        }else if(ival == R.id.radio2){
            rb2.setChecked(true);
            glob=2;
        }else if(ival == R.id.radio3){
            rb3.setChecked(true);
            glob=3;
        }else if(ival == R.id.radio4){
            rb4.setChecked(true);
            glob=4;
        }else if(ival == R.id.radio5){
            rb5.setChecked(true);
            glob=5;
        }

        rbMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            int state = 0;
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio1){
                    sE.clear();
                    sE.putInt("Pref", checkedId);
                    sE.apply();
                }else if(checkedId == R.id.radio2){
                    sE.clear();
                    sE.putInt("Pref", checkedId);
                    sE.apply();
                }else if(checkedId == R.id.radio3){
                    sE.clear();
                    sE.putInt("Pref", checkedId);
                    sE.apply();
                }else if(checkedId == R.id.radio4){
                    sE.clear();
                    sE.putInt("Pref", checkedId);
                    sE.apply();
                }else if(checkedId == R.id.radio5){
                    sE.clear();
                    sE.putInt("Pref", checkedId);
                    sE.apply();
                }

            }
        });

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        sPref = getSharedPreferences("Pref",0);
        sE = sPref.edit();
        ival = sPref.getInt("Pref", 0);

        if(ival == R.id.radio1){
            rb1.setChecked(true);
            glob=1;
        }else if(ival == R.id.radio2){
            rb2.setChecked(true);
            glob=2;
        }else if(ival == R.id.radio3){
            rb3.setChecked(true);
            glob=3;
        }else if(ival == R.id.radio4){
            rb4.setChecked(true);
            glob=4;
        }else if(ival == R.id.radio5){
            rb5.setChecked(true);
            glob=5;
        }
    }

}