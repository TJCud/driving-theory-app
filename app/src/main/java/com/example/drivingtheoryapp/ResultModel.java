package com.example.drivingtheoryapp;

public class ResultModel {


    //DECLARING VARIABLES
    private String username;
    private String category;
    private int questionsCorrect;
    private int questionsTotal;

    private String askedQuestion;
    private String userAnswer;
    private String correctAnswer;






    //CONSTRUCTOR
    public ResultModel(String username, String category, int questionsCorrect, int questionsTotal, String askedQuestion, String userAnswer, String correctAnswer) {
        this.username = username;
        this.category = category;
        this.questionsCorrect = questionsCorrect;
        this.questionsTotal = questionsTotal;
        this.askedQuestion = askedQuestion;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
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

    public String getAskedQuestion() {
        return askedQuestion;
    }
    public void setAskedQuestion(String askedQuestion) {
        this.askedQuestion = askedQuestion;
    }

    public String getUserAnswer() {
        return userAnswer;
    }
    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }


}




