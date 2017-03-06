package com.example.noahrickles.waterreporterteam14_harambelovedwaters.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.noahrickles.waterreporterteam14_harambelovedwaters.R;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.Singleton;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.WaterReport;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView reports;
    private Singleton instance;
    private ArrayAdapter<String> listAdapter;

    /**
    * Actions that occur when MainActivity is prompted.
    * @param savedInstanceState     the previously saved state of an instance
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = Singleton.getInstance();

        reports = (ListView) findViewById(R.id.reportView);
        ArrayList<WaterReport> reportList = instance.getWaterReports();

        ArrayList<String> reportStrings = new ArrayList<String>();
        for (int i = 0; i < reportList.size(); i++) {
            reportStrings.add(reportList.get(i).toString());
        }

        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                reportStrings);
        reports.setAdapter(listAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * Logs the user out of the application and returns to the login screen
     * @param view the logout button
     */
    public void logout(View view) {
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Brings the user to a personalized profile page
     * @param view the profile button
     */
    public void goProfile(View view) {
        Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
        startActivity(intent);
    }

    /**
     * Brings the user to a personalized profile page
     * @param view the map button
     */
    public void goToMap(View view) {
        Intent intent = new Intent(getBaseContext(), MapsActivity.class);
        startActivity(intent);
    }

    /**
     * Submits a report for the user
     * @param view the report button
     */
    public void report(View view) {
        Intent intent = new Intent(getBaseContext(), SubmitReportActivity.class);
        startActivity(intent);
    }

}
