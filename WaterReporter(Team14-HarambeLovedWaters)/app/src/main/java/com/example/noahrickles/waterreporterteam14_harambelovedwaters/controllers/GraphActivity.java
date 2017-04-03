package com.example.noahrickles.waterreporterteam14_harambelovedwaters.controllers;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    private GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationField = (EditText) findViewById(R.id.locationField);
        yearField = (EditText) findViewById(R.id.yearField);
        graph = (GraphView) findViewById(R.id.graph);
        instance = Singleton.getInstance();
    }

    public void graphCreate(View v) {
        String location = locationField.getText().toString();
        String year = yearField.getText().toString();
        if (!year.equals("") && !location.equals("")) {
            HashMap<Integer, Double> graphPoints = instance.getGraphPoints(location, year);
            DataPoint[] values = new DataPoint[12];
            Integer[] keys = (Integer[]) graphPoints.keySet().toArray();
            for (int i = 0; i < graphPoints.size(); i++) {
                DataPoint point = new DataPoint((double) keys[i], (double) graphPoints.get(i));
            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(values);
            series.setTitle("PPM over the year " + year);
            graph.addSeries(series);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Enter information into fields.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

}
