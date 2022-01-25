package com.example.drivingtheoryapp;

import android.provider.BaseColumns;

public class MockTestContract {


    //Constructor
    private MockTestContract(){ }


    public static class QuestionsTable implements BaseColumns
    {
        public static final String TABLE_NAME = "test_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER_NR = "answer";
        public static final String COLUMN_IMAGE_ID = "image";

    }

}