package com.example.drivingtheoryapp;

public class ResultModel {


    //DECLARING VARIABLES
    private String username;
    private String category;
    private int questionsCorrect;
    private int questionsTotal;
    private String savedQuestion;







    //CONSTRUCTOR
    public ResultModel(String username, String category, int questionsCorrect, int questionsTotal, String savedQuestion) {
        this.username = username;
        this.category = category;
        this.questionsCorrect = questionsCorrect;
        this.questionsTotal = questionsTotal;
        this.savedQuestion = savedQuestion;
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

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
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

    public String getSavedQuestion() {
        return savedQuestion;
    }
    public void setSavedQuestion(String savedQuestion) {
        this.savedQuestion = savedQuestion;
    }


}




