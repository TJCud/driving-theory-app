package com.example.drivingtheoryapp;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class ResultModelTest {


    ResultModel resultModel = new ResultModel(1, "username", "questionsCorrect", "questionsTotal", "outcome", "date");


    @Test
    public void getID() {
        assertEquals(1,resultModel.getID());
    }

    @Test
    public void setID() {
        resultModel.setID(5);
        assertEquals(5,resultModel.getID());
    }

    @Test
    public void getUsername() {
        assertEquals("username",resultModel.getUsername());
    }

    @Test
    public void setUsername() {
        resultModel.setUsername("testing username");
        assertEquals("testing username",resultModel.getUsername());
    }

    @Test
    public void getQuestionsTotal() {
        assertEquals("questionsTotal",resultModel.getQuestionsTotal());
    }

    @Test
    public void setQuestionsTotal() {
        resultModel.setQuestionsTotal("testing questionsTotal");
        assertEquals("testing questionsTotal",resultModel.getQuestionsTotal());
    }

    @Test
    public void getQuestionsCorrect() {
        assertEquals("questionsCorrect",resultModel.getQuestionsCorrect());
    }

    @Test
    public void setQuestionsCorrect() {
        resultModel.setQuestionsCorrect("testing questionsCorrect");
        assertEquals("testing questionsCorrect",resultModel.getQuestionsCorrect());
    }

    @Test
    public void getOutcome() {
        assertEquals("outcome",resultModel.getOutcome());
    }

    @Test
    public void setOutcome() {
        resultModel.setOutcome("testing outcome");
        assertEquals("testing outcome",resultModel.getOutcome());
    }

    @Test
    public void getDate() {
        assertEquals("date",resultModel.getDate());
    }

    @Test
    public void setDate() {
        resultModel.setDate("testing date");
        assertEquals("testing date",resultModel.getDate());
    }

}