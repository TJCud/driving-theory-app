package com.example.drivingtheoryapp;

public class ResultModel {


    //DECLARING VARIABLES
    private int ID;
    private String username;
    private String questionsCorrect;
    private String questionsTotal;
    private String savedQuestion;
    private String outcome;
    private String date;







    //CONSTRUCTOR
    public ResultModel(int ID, String username, String questionsCorrect, String questionsTotal, String outcome, String date, String savedQuestion) {
        this.ID = ID;
        this.username = username;
        this.questionsCorrect = questionsCorrect;
        this.questionsTotal = questionsTotal;
        this.outcome = outcome;
        this.date = date;
        this.savedQuestion = savedQuestion;
    }

    //EMPTY CONSTRUCTOR
    public ResultModel() {
    }


    //GETTERS AND SETTERS
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getQuestionsTotal() {
        return questionsTotal;
    }
    public void setQuestionsTotal(String questionsTotal) {
        this.questionsTotal = questionsTotal;
    }

    public String getQuestionsCorrect() {
        return questionsCorrect;
    }
    public void setQuestionsCorrect(String questionsCorrect) { this.questionsCorrect = questionsCorrect;
    }

    public String getOutcome() {
        return outcome;
    }
    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getSavedQuestion(int savedQuestion) {
        return this.savedQuestion;
    }
    public void setSavedQuestion(String savedQuestion) {
        this.savedQuestion = savedQuestion;
    }


}




