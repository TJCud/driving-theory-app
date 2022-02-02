package com.example.drivingtheoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ActivityGraphView extends AppCompatActivity {


    LineGraphSeries<DataPoint> series;
    TestDbHelper mDatabaseHelper;
    GraphView graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);
        mDatabaseHelper = new TestDbHelper(this);


        // Getting the intent which started this activity
        Intent intent = getIntent();
        // Get the data of the activity providing the same key value
        String username = intent.getStringExtra("username_key");



        Cursor data = mDatabaseHelper.getAllResults(username);

        graphView = (GraphView) findViewById(R.id.ID_graphView);
        series = new LineGraphSeries<DataPoint>();

       // while(data.moveToNext()) {

        double x,y;
        x=0;


            for(int i=0;i<500;i++){


            x = x +0.1;
            y = Math.sin(x);

            /*int score = data.getInt(2);
            int questions = data.getInt(3);*/


            series.appendData(new DataPoint(x, y), true, 100);
            graphView.addSeries(series);}

      //  }

    }

}