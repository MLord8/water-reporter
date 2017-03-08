package com.example.noahrickles.waterreporterteam14_harambelovedwaters.controllers;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.R;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.Singleton;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.WaterReport;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Singleton instance;
    private GoogleMap mMap;

    /**
    * Actions that occur when MainActivity is prompted.
    * @param savedInstanceState     the previously saved state of an instance
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = Singleton.getInstance();

        ArrayList<WaterReport> reportList = instance.getWaterReports();
        /*
        String[] addresses = {"120 North Avenue NW", "Tel Aviv",
                "101 Carrer de Sardenya, 08013 Barcelona, Spain"};
        for (int i = 0; i < addresses.length; i++) {
            try {
                reportList.add(new WaterReport(
                        "dateAndTime",
                        instance.getAddressFromName(getApplicationContext(), addresses[i]),
                        "username",
                        i,
                        "typeOfWater",
                        "conditionOfWater"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
     * Submits a report for the user
     * @param view the report button
     */
    public void report(View view) {
        Intent intent = new Intent(getBaseContext(), SubmitReportActivity.class);
        finish();
        startActivity(intent);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            /**
//             * Called when window is ready
//             * @param marker
//             */
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//                startActivity(intent);
//            }
//        });

//        instance.getMap().setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return prepareInfoView(marker);
            }

            private View prepareInfoView(Marker marker) {
                WaterReport report = instance.getWaterReportById((Integer) marker.getTag());
                String condition = report.getConditionOfWater();

                LinearLayout infoView = new LinearLayout(MainActivity.this);
                LinearLayout.LayoutParams infoViewParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                infoView.setOrientation(LinearLayout.HORIZONTAL);
                infoView.setLayoutParams(infoViewParams);

                ImageView infoImageView = new ImageView(MainActivity.this);

                int icon;
                if (condition == "Treatable-Clear") {
                    icon = android.R.drawable.presence_online;
                } else if (condition == "Treatable-Muddy") {
                    icon = android.R.drawable.presence_away;
                } else if (condition == "Potable") {
                    icon = android.R.drawable.presence_offline;
                } else if (condition == "Waste") {
                    icon = android.R.drawable.presence_busy;
                } else {
                    icon = android.R.drawable.ic_dialog_map;
                }
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),
                        icon);
                infoImageView.setImageDrawable(drawable);
                infoView.addView(infoImageView);

                LinearLayout subInfoView = new LinearLayout(MainActivity.this);
                LinearLayout.LayoutParams subInfoViewParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                subInfoView.setOrientation(LinearLayout.VERTICAL);
                subInfoView.setLayoutParams(subInfoViewParams);

                TextView subInfo = new TextView(MainActivity.this);
                subInfo.setText(report.toString());
                subInfoView.addView(subInfo);
                infoView.addView(subInfoView);

                return infoView;
            }
        });

        for (WaterReport report : instance.getWaterReports()) {
            Address adr = report.getAddress();
            Marker temp = mMap.addMarker(new MarkerOptions().position(report.getLocation()
            ));
            temp.setTag(report.getReportNumber());
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(new LatLng(-45,-90), new LatLng(45, 90)), 5));
    }
}
