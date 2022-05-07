package com.example.drivingtheoryapp;

import static org.junit.Assert.*;

import org.junit.Test;

public class QuestionModelTest {

    QuestionModel questionModel = new QuestionModel(1, "category", "question", "option1", "option2", "option3", "option4", 1 , "imageID", "explanation");


    @Test
    public void getID() {
        assertEquals(1,questionModel.getID());
    }

    @Test
    public void setID() {
        questionModel.setID(5);
        assertEquals(5,questionModel.getID());
    }

    @Test
    public void getCategory() {
        assertEquals("category",questionModel.getCategory());
    }

    @Test
    public void setCategory() {
        questionModel.setCategory("testing");
        assertEquals("testing",questionModel.getCategory());
    }

    @Test
    public void getQuestion() {
        assertEquals("question",questionModel.getQuestion());
    }

    @Test
    public void setQuestion() {
        questionModel.setQuestion("testing");
        assertEquals("testing",questionModel.getQuestion());
    }

    @Test
    public void getOption1() {
        assertEquals("option1",questionModel.getOption1());
    }

    @Test
    public void setOption1() {
        questionModel.setOption1("testing");
        assertEquals("testing",questionModel.getOption1());
    }

    @Test
    public void getOption2() {
        assertEquals("option2",questionModel.getOption2());
    }

    @Test
    public void setOption2() {
        questionModel.setOption2("testing");
        assertEquals("testing",questionModel.getOption2());
    }

    @Test
    public void getOption3() {
        assertEquals("option3",questionModel.getOption3());
    }

    @Test
    public void setOption3() {
        questionModel.setOption3("testing");
        assertEquals("testing",questionModel.getOption3());
    }

    @Test
    public void getOption4() {
        assertEquals("option4",questionModel.getOption4());
    }

    @Test
    public void setOption4() {
        questionModel.setOption4("testing");
        assertEquals("testing",questionModel.getOption4());
    }

    @Test
    public void getImageID() {
        assertEquals("imageID",questionModel.getImageID());
    }

    @Test
    public void setImageID() {
        questionModel.setImageID("testing");
        assertEquals("testing",questionModel.getImageID());
    }

    @Test
    public void getExplanation() {
        assertEquals("explanation",questionModel.getExplanation());
    }

    @Test
    public void setExplanation() {
        questionModel.setExplanation("testing");
        assertEquals("testing",questionModel.getExplanation());
    }

    @Test
    public void getAnswerNr() {
        assertEquals(1,questionModel.getAnswerNr());
    }

    @Test
    public void setAnswerNr() {
        questionModel.setAnswerNr(4);
        assertEquals(4,questionModel.getAnswerNr());
    }
}