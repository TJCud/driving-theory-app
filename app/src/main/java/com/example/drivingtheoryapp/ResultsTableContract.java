package com.example.drivingtheoryapp;
import android.provider.BaseColumns;

public class ResultsTableContract {

    //Constructor
    private ResultsTableContract(){ }

    public static class ResultsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "saved_results";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_QUESTIONS_CORRECT = "questions_correct";
        public static final String COLUMN_QUESTIONS_TOTAL = "questions_total";
        public static final String COLUMN_TEST_DATE = "test_date";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_SAVED_QUESTION = "saved_question";


    }
}
