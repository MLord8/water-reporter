package com.example.noahrickles.waterreporterteam14_harambelovedwaters.controllers;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.noahrickles.waterreporterteam14_harambelovedwaters.R;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.Singleton;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.WaterReport;

import java.util.ArrayList;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

//    private ListView reports;
    private Singleton instance;
    private ArrayAdapter<String> listAdapter;
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

//        reports = (ListView) findViewById(R.id.reportView);
        ArrayList<WaterReport> reportList = instance.getWaterReports();
        for (int i = 0; i < 10; i++) {
            reportList.add(new WaterReport(
                    "dateAndTime",
                    "address",
                    new LatLng(90 - 18 * i, 36 * i - 180),
                    "username",
                    i,
                    "typeOfWater",
                    "conditionOfWater"));
        }


//        ArrayList<String> reportStrings = new ArrayList<String>();
//        for (int i = 0; i < reportList.size(); i++) {
//            reportStrings.add(reportList.get(i).toString());
//        }

//        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
//                reportStrings);
//        reports.setAdapter(listAdapter);

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
//        instance.setMap(googleMap);
        mMap = googleMap;
//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            /**
//             * Called when marker is ready
//             * @param marker
//             * @return false
//             */
//            @Override
//            public boolean onMarkerClick(Marker marker) {
////                Log.d("potatoes", marker.getPosition().toString());
////                return true;
//                Toast.makeText(getApplicationContext(), "You've clicked a marker!",
//                        Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });


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
                LinearLayout infoView = new LinearLayout(MainActivity.this);
                LinearLayout.LayoutParams infoViewParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                infoView.setOrientation(LinearLayout.HORIZONTAL);
                infoView.setLayoutParams(infoViewParams);

                ImageView infoImageView = new ImageView(MainActivity.this);
                int icon = android.R.drawable.ic_dialog_map;
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

                TextView subInfoLat = new TextView(MainActivity.this);
                subInfoLat.setText(instance.getWaterReportById((Integer) marker.getTag()).toString());
                TextView subInfoLng = new TextView(MainActivity.this);
                //subInfoLng.setText("Lng: " + marker.getPosition().longitude);
                subInfoView.addView(subInfoLat);
                //subInfoView.addView(subInfoLng);
                infoView.addView(subInfoView);

                return infoView;
            }
        });

//        LinkedList<LatLng> locations = new LinkedList<LatLng>();

        for (WaterReport report : instance.getWaterReports()) {
            Marker temp = mMap.addMarker(new MarkerOptions().position(report.getLocation()));
            temp.setTag(report.getReportNumber());
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(new LatLng(-45,-90), new LatLng(45, 90)), 5));
        /*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
    }
}
