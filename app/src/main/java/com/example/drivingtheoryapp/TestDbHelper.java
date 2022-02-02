package com.example.drivingtheoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.drivingtheoryapp.MockTestContract.*;
import com.example.drivingtheoryapp.ResultsTableContract.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class TestDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DrivingTestSuccess.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    private Context context;

    public TestDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }





    //CREATES AND EXECUTES DATABASE
    @Override
    public void onCreate(SQLiteDatabase db) {
        TestDbHelper context = this;
        this.db = db;

        //CREATE RESULTS TABLE
        final String SQL_CREATE_RESULTS_TABLE = "CREATE TABLE " +
                ResultsTableContract.ResultsTable.TABLE_NAME + " ( " +
                ResultsTableContract.ResultsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ResultsTableContract.ResultsTable.COLUMN_USERNAME + " TEXT, " +
                ResultsTableContract.ResultsTable.COLUMN_QUESTIONS_CORRECT + " INTEGER, " +
                ResultsTableContract.ResultsTable.COLUMN_QUESTIONS_TOTAL + " INTEGER, " +
                ResultsTableContract.ResultsTable.COLUMN_TEST_DATE + " TEXT, " +
                ResultsTableContract.ResultsTable.COLUMN_ASKED_QUESTION + " TEXT, " +
                ResultsTableContract.ResultsTable.COLUMN_USER_ANSWER + " TEXT, " +
                ResultsTableContract.ResultsTable.COLUMN_CORRECT_ANSWER + " TEXT" +
                ")";
        db.execSQL(SQL_CREATE_RESULTS_TABLE);




        //CREATE QUESTIONS TABLE
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_CATEGORY + " TEXT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_IMAGE_ID + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);


        //LOAD DATA FROM CSV FILE AND STORE INTO RESULTS TABLE
        String mCSVfile = "data.csv";
        AssetManager manager = TestDbHelper.this.context.getAssets();
        InputStream inStream = null;
        try {
            inStream = manager.open(mCSVfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line = "";
        try {
            while ((line = buffer.readLine()) != null) {
                String[] columns = line.split((Pattern.quote("|")));

                ContentValues cv = new ContentValues();
                cv.put(QuestionsTable.COLUMN_CATEGORY, columns[0].trim());
                cv.put(QuestionsTable.COLUMN_QUESTION, columns[1].trim());
                cv.put(QuestionsTable.COLUMN_OPTION1, columns[2].trim());
                cv.put(QuestionsTable.COLUMN_OPTION2, columns[3].trim());
                cv.put(QuestionsTable.COLUMN_OPTION3, columns[4].trim());
                cv.put(QuestionsTable.COLUMN_OPTION4, columns[5].trim());
                cv.put(QuestionsTable.COLUMN_ANSWER_NR, columns[6].trim());
                cv.put(QuestionsTable.COLUMN_IMAGE_ID, columns[7].trim());

                db.insert(QuestionsTable.TABLE_NAME, null, cv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //UPGRADES DATABASE
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ResultsTable.TABLE_NAME);
        onCreate(db);
    }



    //RETRIEVES QUESTIONS FROM DB, ADDS TO LIST, RETURNS LIST
    public List<QuestionModel> getAllQuestions() {

        List<QuestionModel> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);


        //CREATES QUESTION OBJECT FOR EACH ENTRY IN DATABASE
        if (c.moveToFirst()) { //moveToFirst will display question ONLY if there is an entry in database
            do {
                QuestionModel question = new QuestionModel();
                question.setCategory(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setImageID(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_IMAGE_ID)));
                questionList.add(question);
            } while (c.moveToNext()); //Keeps adding questions while entries in database exists.
        }

        c.close();
        return questionList;
    }


    public List<QuestionModel> getCategoryQuestions(String category) {

        List<QuestionModel> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String query = "SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_CATEGORY + " = '" + category + "'";

        Cursor c = db.rawQuery(query, null);


        //CREATES QUESTION OBJECT FOR EACH ENTRY IN DATABASE
        if (c.moveToFirst()) { //moveToFirst will display question ONLY if there is an entry in database
            do {
                QuestionModel question = new QuestionModel();
                question.setCategory(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setImageID(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_IMAGE_ID)));
                questionList.add(question);
            } while (c.moveToNext()); //Keeps adding questions while entries in database exists.
        }

        c.close();
        return questionList;
    }






    //SAVES RESULTS TO DB
    public boolean saveResults(String username, int qCorrect, int qTotal, String date, String askedQuestion, String userAnswer, String correctAnswer) {
        //String username, int questionsCorrect, int questionsTotal
        // Gets the data repository in write mode
        db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues contentValues = new ContentValues();
        contentValues.put(ResultsTable.COLUMN_USERNAME, username);
        contentValues.put(ResultsTable.COLUMN_QUESTIONS_CORRECT, qCorrect);
        contentValues.put(ResultsTable.COLUMN_QUESTIONS_TOTAL, qTotal);
        contentValues.put(ResultsTable.COLUMN_TEST_DATE, date);
        contentValues.put(ResultsTable.COLUMN_ASKED_QUESTION, askedQuestion);
        contentValues.put(ResultsTable.COLUMN_USER_ANSWER, userAnswer);
        contentValues.put(ResultsTable.COLUMN_CORRECT_ANSWER, correctAnswer);


        // Insert the new row, returning the primary key value of the new row
        long result = db.insert(ResultsTable.TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }



    //RETRIEVES RESULTS FROM DB, ADDS TO LIST, RETURNS LIST
    public Cursor getAllResults(String username) {

        db = this.getWritableDatabase();
        String query = "SELECT * FROM " + ResultsTable.TABLE_NAME +
                " WHERE " + ResultsTable.COLUMN_USERNAME + " = '" + username + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
