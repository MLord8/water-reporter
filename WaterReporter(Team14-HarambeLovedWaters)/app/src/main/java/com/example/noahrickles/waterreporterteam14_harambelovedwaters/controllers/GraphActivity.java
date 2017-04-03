package com.example.noahrickles.waterreporterteam14_harambelovedwaters.controllers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noahrickles.waterreporterteam14_harambelovedwaters.R;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.Singleton;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Arrays;
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
            HashMap<Integer, Double> CPPMGraphPoints = instance.getCPPMGraphPoints(location, year);
            HashMap<Integer, Double> VPPMGraphPoints = instance.getVPPMGraphPoints(location, year);

            ArrayList<DataPoint> CPPMValues = new ArrayList<>();
            ArrayList<DataPoint> VPPMValues = new ArrayList<>();
            Integer[] keySet = new Integer[CPPMGraphPoints.keySet().size()];
            int index = 0;
            for (Integer key : CPPMGraphPoints.keySet()) {
                keySet[index] = key;
                index++;
            }
            Arrays.sort(keySet);
            for (Integer key : keySet) {
                Double val1 = CPPMGraphPoints.get(key);
                Double val2 = VPPMGraphPoints.get(key);
                DataPoint point1 = new DataPoint(key, val1);
                DataPoint point2 = new DataPoint(key, val2);
                CPPMValues.add(point1);
                VPPMValues.add(point2);
            }
            DataPoint[] CPPMArray = new DataPoint[CPPMValues.size()];
            DataPoint[] VPPMArray = new DataPoint[VPPMValues.size()];
            for (int i = 0; i < CPPMArray.length; i++) {
                CPPMArray[i] = CPPMValues.get(i);
                VPPMArray[i] = VPPMValues.get(i);
            }

            LineGraphSeries<DataPoint> CPPMseries = new LineGraphSeries<>(CPPMArray);
            CPPMseries.setTitle("PPM over the year " + year);
            CPPMseries.setDrawDataPoints(true);
            CPPMseries.setColor(Color.GREEN);
            graph.addSeries(CPPMseries);

            LineGraphSeries<DataPoint> VPPMseries = new LineGraphSeries<>(VPPMArray);
            VPPMseries.setTitle("PPM over the year " + year);
            VPPMseries.setDrawDataPoints(true);
            VPPMseries.setColor(Color.RED);
            graph.addSeries(VPPMseries);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Enter information into fields.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    /**
     * Brings the user back to the main application
     * @param view the cancel button
     */
    public void cancel(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        finish();
        startActivity(intent);
    }

}
