package com.example.drivingtheoryapp;

import static org.junit.Assert.*;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExamMethodsTest {

    @Test
    public void testHasUserPassed() {
        assertEquals("PASS",ExamMethods.getExamOutcome(86,100));
        assertEquals("FAIL",ExamMethods.getExamOutcome(80,100));
    }

    @Test
    public void testGetDate() {

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date = format.format(today);

        assertEquals(date,ExamMethods.getDate());

    }
}