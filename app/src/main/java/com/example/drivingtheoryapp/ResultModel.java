package com.example.drivingtheoryapp;

public class ResultModel {


    //DECLARING VARIABLES
    private String username;
    private int questionsCorrect;
    private int questionsTotal;




    //CONSTRUCTOR
    public ResultModel(String username, int questionsCorrect, int questionsTotal) {
        this.username = username;
        this.questionsCorrect = questionsCorrect;
        this.questionsTotal = questionsTotal;
    }

    //EMPTY CONSTRUCTOR
    public ResultModel() {
    }


    //GETTERS AND SETTERS
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public int getQuestionsCorrect() {
        return questionsCorrect;
    }
    public void setQuestionsCorrect(int questionsCorrect) { this.questionsCorrect = questionsCorrect; }

    public int getQuestionsTotal() {
        return questionsTotal;
    }
    public void setQuestionsTotal(int questionsTotal) {
        this.questionsTotal = questionsTotal;
    }
}




