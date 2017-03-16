package com.example.noahrickles.waterreporterteam14_harambelovedwaters.controllers;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.R;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.Singleton;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.WaterReport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SubmitPurityReportActivity extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a (z)");
    private EditText locationField;
    private EditText virusPPMField;
    private EditText contaminantPPMField;
    private int reportNum;
    private Singleton instance;

    /**
     * Actions that occur when SubmitReportActivity is prompted.
     * @param savedInstanceState     the previously saved state of an instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        reportNum = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_purity_report);
        instance = Singleton.getInstance();
        locationField = (EditText) findViewById(R.id.locEdit);
        virusPPMField = (EditText) findViewById(R.id.locEdit);
        contaminantPPMField = (EditText) findViewById(R.id.locEdit);


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
        String virusPPM = virusPPMField.getText();


        try {
            address = instance.findAddressFromName(getBaseContext(), locationField.getText().toString());
        } catch (IOException e) {
            e.printStackTrace();
            locationField.setError(getString(R.string.error_invalid_location));
            focusView = locationField;
            cancel = true;
        }

//        if (!instance.isLocationValid(location)) {
//            locationField.setError(getString(R.string.error_invalid_location));
//            focusView = locationField;
//            cancel = true;
//        }


        RadioGroup conditionGroup = (RadioGroup) findViewById(R.id.conditionGroup);
        int buttonChecked2 = conditionGroup.getCheckedRadioButtonId();
        String condition = "N/A";
        boolean checked2 = false;
        if (buttonChecked2 != -1) {
            checked2 = true;
        } else {
            focusView = locationField;
            locationField.setError(getString(R.string.error_condition_not_selected));
        }
        if (checked2) {
            switch(buttonChecked2) {
                case R.id.treatableclear:
                    condition = "Treatable-Clear";
                    break;
                case R.id.treatablemuddy:
                    condition = "Treatable-Muddy";
                    break;
                case R.id.potable:
                    condition = "Potable";
                    break;
                case R.id.waste:
                    condition = "Waste";
                    break;
            }
        }
        //add the water report if requirements are met
        if (cancel || !checked1 || !checked2) {
            focusView.requestFocus();
        } else if (address != null) {
            instance.addWaterPurityReport(
                    new WaterReport(sdf.format(new Date()),
                            address, currentUser,
                            instance.getWaterReports().size() + 1,
                            con, condition));
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
