package com.example.drivingtheoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.drivingtheoryapp.MockTestContract.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


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

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);


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
                String[] colums = line.split(",");

                ContentValues cv = new ContentValues();
                cv.put(QuestionsTable.COLUMN_QUESTION, colums[0].trim());
                cv.put(QuestionsTable.COLUMN_OPTION1, colums[1].trim());
                cv.put(QuestionsTable.COLUMN_OPTION2, colums[2].trim());
                cv.put(QuestionsTable.COLUMN_OPTION3, colums[3].trim());
                cv.put(QuestionsTable.COLUMN_OPTION4, colums[4].trim());
                cv.put(QuestionsTable.COLUMN_ANSWER_NR, colums[5].trim());
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
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext()); //Keeps adding questions while entries in database exists.
        }

        c.close();
        return questionList;
    }


}



