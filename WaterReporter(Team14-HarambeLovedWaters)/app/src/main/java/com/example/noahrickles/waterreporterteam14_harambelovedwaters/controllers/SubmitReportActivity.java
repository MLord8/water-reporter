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
    private EditText virusPPMField;
    private EditText contaminantPPMField;
    private int reportNum;
    private Singleton instance;
    private RadioGroup purityGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        reportNum = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_report);
        instance = Singleton.getInstance();
        locationField = (EditText) findViewById(R.id.locEdit);
        virusPPMField = (EditText) findViewById(R.id.virusEdit);
        contaminantPPMField = (EditText) findViewById(R.id.contamEdit);
    }

    public void submitReport(View view) {
        String location = locationField.getText().toString();
        double virusPPM = Double.parseDouble(virusPPMField.getText().toString());
        double contaminantPPM = Double.parseDouble(contaminantPPMField.getText().toString());
        String currentDateAndTime = sdf.format(new Date());
        String currentUser = instance.getCurrUser().getUsername();
        ++reportNum;

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.purityGroup);
        int buttonChecked = radioGroup.getCheckedRadioButtonId();
        String purity = "N/A";
        boolean checked = false;
        if (buttonChecked != -1) {
            checked = true;
        }
        if (checked) {
            switch(buttonChecked) {
                case R.id.safe:
                    purity = "Safe";
                    break;
                case R.id.treatable:
                    purity = "Treatable";
                    break;
                case R.id.unsafe:
                    purity = "Unsafe";
                    break;
            }
        }
        //add the water report
        instance.addWaterReport(new WaterReport(currentDateAndTime, location, currentUser,
                reportNum, purity, virusPPM, contaminantPPM));
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    public void cancelReport(View view) {
        locationField.setText("");
        virusPPMField.setText("");
        contaminantPPMField.setText("");
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

}
