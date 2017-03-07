package com.example.noahrickles.waterreporterteam14_harambelovedwaters.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.R;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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
import android.util.Log;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ListView reports;
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
        mMap = googleMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            /**
             * Called when marker is ready
             * @param marker
             * @return false
             */
            @Override
            public boolean onMarkerClick(Marker marker) {
//                Log.d("potatoes", marker.getPosition().toString());
//                return true;
                Toast.makeText(getApplicationContext(), "Title: " + marker.getTitle()
                        +  "Position: " + marker.getPosition()
                        + "Tag: " + marker.getTag(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            /**
             * Called when window is ready
             * @param marker
             */
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        LinkedList<LatLng> locations = new LinkedList<LatLng>();
        for (int i = 0; i < 10; i++) {
            locations.add(new LatLng(18 * i - 90, 36 * i - 180));
        }
        for (int i = 0; i < locations.size(); i++) {
            String title = "Marker #" + i + "\nLocation: " + locations.get(i);
            mMap.addMarker(new MarkerOptions().position(locations.get(i)).title(title));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(locations.get(2), locations.get(7)), 5));
        /*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
    }
}
