package com.example.noahrickles.waterreporterteam14_harambelovedwaters.controllers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.noahrickles.waterreporterteam14_harambelovedwaters.R;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.Singleton;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.HashMap;

public class GraphActivity extends AppCompatActivity {

    private EditText locationField;
    private EditText yearField;
    private Singleton instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void graphCreate(View v) {
        String location = locationField.getText().toString();
        String year = yearField.getText().toString();

        GraphView graph = (GraphView) findViewById(R.id.graph);
        HashMap<Integer, Double> graphPoints = instance.getGraphPoints(location, year);
        DataPoint[] values = new DataPoint[12];
        Integer[] keys = (Integer[]) graphPoints.keySet().toArray();
        for (int i = 0; i < graphPoints.size(); i++) {
            DataPoint point = new DataPoint((double) keys[i], (double) graphPoints.get(i));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(values);
        series.setTitle("PPM over the year " + year);
        graph.addSeries(series);
    }

}
