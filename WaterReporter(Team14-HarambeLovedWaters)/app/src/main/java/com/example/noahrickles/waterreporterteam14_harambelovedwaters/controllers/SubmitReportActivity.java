package com.example.noahrickles.waterreporterteam14_harambelovedwaters.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.noahrickles.waterreporterteam14_harambelovedwaters.R;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.Singleton;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.WaterReport;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SubmitReportActivity extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");

    private EditText locationField;
    private int reportNum;
    private Singleton instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        reportNum = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_report);
        instance = Singleton.getInstance();
        locationField = (EditText) findViewById(R.id.locEdit);
    }

    public void submitReport(View view) {
        String location = locationField.getText().toString();
        String currentDateAndTime = sdf.format(new Date());
        String currentUser = instance.getCurrUser().getUsername();
        ++reportNum;

        boolean cancel = false;
        View focusView = null;

        if (!instance.isLocationValid(location)) {
            locationField.setError(getString(R.string.error_invalid_location));
            focusView = locationField;
            cancel = true;
        }

        RadioGroup typeGroup = (RadioGroup) findViewById(R.id.typeGroup);
        int buttonChecked1 = typeGroup.getCheckedRadioButtonId();
        String type = "N/A";
        boolean checked1 = false;
        if (buttonChecked1 != -1) {
            checked1 = true;
        } else {
            focusView = locationField;
            locationField.setError(getString(R.string.error_type_not_selected));
        }
        if (checked1) {
            switch(buttonChecked1) {
                case R.id.bottled:
                    type = "Bottled";
                    break;
                case R.id.well:
                    type = "Well";
                    break;
                case R.id.stream:
                    type = "Stream";
                    break;
                case R.id.lake:
                    type = "Lake";
                    break;
                case R.id.spring:
                    type = "Spring";
                    break;
                case R.id.other:
                    type = "Other";
                    break;
            }
        }

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
        //add the water report
        if (cancel || !checked1 || !checked2) {
            focusView.requestFocus();
        } else {
            instance.addWaterReport(new WaterReport(currentDateAndTime, location, currentUser,
                    reportNum, type, condition));
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    public void cancelReport(View view) {
        locationField.setText("");
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

}
