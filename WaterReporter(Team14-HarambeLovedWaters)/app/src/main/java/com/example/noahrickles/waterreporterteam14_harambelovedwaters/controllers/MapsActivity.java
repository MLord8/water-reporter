package com.example.noahrickles.waterreporterteam14_harambelovedwaters.controllers;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.noahrickles.waterreporterteam14_harambelovedwaters.R;

import java.util.LinkedList;
import android.util.Log;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Called when the map is ready.
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     * @param googleMap the given GoogleMap object
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
//            /**
//             * Called when marker is ready
//             * @param marker
//             * @return false
//             */
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                /*
//                Log.d("potatoes", marker.getPosition().toString());
//                return true;
//                */
//            }
//        });
        mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            /**
             * Called when marker is ready
             * @param marker
             * @return false
             */
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d("oranges are yummy", marker.getPosition().toString());
                /*
                Toast.makeText(getApplicationContext(), "Title: " + marker.getTitle()
                        +  "Position: " + marker.getPosition()
                        + "Tag: " + marker.getTag(), Toast.LENGTH_SHORT).show();
                */
                return false;
            }
        });

        LinkedList < LatLng > locations = new LinkedList<LatLng>();
        for (int i = 0; i < 10; i++) {
            locations.add(new LatLng(18 * i - 90, 36 * i - 180));
        }
        for (int i = 0; i < locations.size(); i++) {
            Marker tempMarker = mMap.addMarker(new MarkerOptions()
                    .position(locations.get(i))
                    .title("Marker #" + i));
            tempMarker.setTag(locations.get(i).hashCode());
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(locations.get(2), locations.get(7)), 5));
        /*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
    }


//    public boolean onMarkerClick(Marker marker) {
//        Toast.makeText(this, "Title: " + marker.getTitle()
//                            +  "Position: " + marker.getPosition()
//                            + "Tag: " + marker.getTag(), Toast.LENGTH_SHORT).show();
//        // Return false to indicate that we have not consumed the event and that
//        // we wish for the default behavior to occur (which is for the camera to
//        // move such that the marker is centered and for the marker's info window
//        // to open, if it has one)
//
//        return false;
//    }
}
