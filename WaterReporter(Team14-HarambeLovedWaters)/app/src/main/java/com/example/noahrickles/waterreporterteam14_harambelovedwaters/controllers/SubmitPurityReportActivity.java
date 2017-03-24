package com.example.noahrickles.waterreporterteam14_harambelovedwaters.controllers;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.example.noahrickles.waterreporterteam14_harambelovedwaters.R;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.Singleton;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.WaterPurityReport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SubmitPurityReportActivity extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a (z)");
    private EditText locationField;
    private EditText virusPPMField;
    private EditText contaminantPPMField;
    private ListView purityReportScroll;
    private Singleton instance;

    /**
     * Actions that occur when SubmitReportActivity is prompted.
     * @param savedInstanceState     the previously saved state of an instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_purity_report);
        instance = Singleton.getInstance();
        locationField = (EditText) findViewById(R.id.editLoc);
        virusPPMField = (EditText) findViewById(R.id.editVirusPPM);
        contaminantPPMField = (EditText) findViewById(R.id.editContamination);
        if (instance.getCurrUser().getUserType().equals("MANAGER")) {
            ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, (List) instance.getWaterPurityReports());

            ListView listView = (ListView) findViewById(R.id.purityReportScroll);
            listView.setAdapter(adapter);
        }
    }

    /**
     * Creates a report with inputted and auto-generated parameters and adds it to the list of
     * reports.
     * @param view The view of the button
     */
    public void submitReport(View view) {
        String currentUser = instance.getCurrUser().getUsername();
        boolean cancel = false;
        View focusView = null;
        Address address = null;
        double contaminantPPM = 0.0;
        double virusPPM = 0.0;
        try {
            contaminantPPM = Double.parseDouble(contaminantPPMField.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            contaminantPPMField.setError(getString(R.string.error_invalid_double));
            focusView = contaminantPPMField;
            cancel = true;
        }

        try {
            virusPPM = Double.parseDouble(virusPPMField.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            virusPPMField.setError(getString(R.string.error_invalid_double));
            focusView = virusPPMField;
            cancel = true;
        }

        try {
            address = instance.findAddressFromName(getBaseContext(), locationField.getText().toString());
        } catch (IOException e) {
            e.printStackTrace();
            locationField.setError(getString(R.string.error_invalid_location));
            focusView = locationField;
            cancel = true;
        }

        RadioGroup conditionGroup = (RadioGroup) findViewById(R.id.conditionGroup);
        int buttonChecked = conditionGroup.getCheckedRadioButtonId();
        String condition = "N/A";
        boolean checked = false;
        if (buttonChecked != -1) {
            checked = true;
        } else {
            focusView = locationField;
            locationField.setError(getString(R.string.error_condition_not_selected));
        }
        if (checked) {
            switch(buttonChecked) {
                case R.id.safe:
                    condition = "Safe";
                    break;
                case R.id.treatable:
                    condition = "Treatable";
                    break;
                case R.id.unsafe:
                    condition = "Unsafe";
                    break;
            }
        }

        //add the water purity report if requirements are met
        if (cancel || !checked) {
            focusView.requestFocus();
        } else if (address != null) {
            instance.addWaterPurityReport(
                    new WaterPurityReport(sdf.format(new Date()),
                            address, currentUser,
                            instance.getWaterPurityReports().size() + 1,
                            contaminantPPM, virusPPM, condition));
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            finish();
            startActivity(intent);
        }
    }

    /**
     * Cancels the report submission and returns the user to the main screen when the
     * cancel button is pressed
     * @param view The current view of the button
     */
    public void cancelReport(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        finish();
        startActivity(intent);
    }

}
