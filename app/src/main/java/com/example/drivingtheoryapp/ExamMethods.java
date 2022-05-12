package com.example.drivingtheoryapp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExamMethods {



    public static String getExamOutcome(int score, int totalQuestions){

        double passCheck = score * 100 / totalQuestions; // Calculating Percentage

        //If percentage is greater than 85, the user has passed the test
        if (passCheck > 85) {
            return "PASS";
        } else {
            return "FAIL";
        }
    }


    public static String getDate(){
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date = format.format(today);
        return date;
    }


    public static double getScorePercentage(int score, int totalQuestions){


        return (double) score * 100 / totalQuestions;

    }






}
