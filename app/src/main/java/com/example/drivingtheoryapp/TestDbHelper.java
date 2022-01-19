package com.example.drivingtheoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.drivingtheoryapp.MockTestContract.*;
import java.util.ArrayList;
import java.util.List;


public class TestDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DrivingTestSuccess.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public TestDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    //CREATES AND EXECUTES DATABASE
    @Override
    public void onCreate(SQLiteDatabase db) {

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
        fillQuestionsTable();
    }


    //UPGRADES DATABASE
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
    onCreate(db);
    }


    // PASSES QUESTIONS INTO QUESTION MODEL CONSTRUCTOR
    private void fillQuestionsTable() {
       QuestionModel q1 = new QuestionModel("A is correct", "A", "B", "C", "D", 1);
        addQuestion(q1);

        QuestionModel q2 = new QuestionModel("B is correct", "A", "B", "C", "D", 2);
        addQuestion(q2);

        QuestionModel q3 = new QuestionModel("C is correct", "A", "B", "C", "D", 3);
        addQuestion(q3);

        QuestionModel q4 = new QuestionModel("D is correct", "A", "B", "C", "D", 4);
        addQuestion(q4);
    }






    //GETS QUESTIONS AND ANSWER FROM QUESTION MODEL, ADDS THEM TO DATABASE
    private void addQuestion(QuestionModel question) {
      ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
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



