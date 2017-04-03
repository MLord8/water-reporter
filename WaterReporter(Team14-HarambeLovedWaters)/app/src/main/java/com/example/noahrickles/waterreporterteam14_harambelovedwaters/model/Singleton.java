package com.example.noahrickles.waterreporterteam14_harambelovedwaters.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.content.Context;
import android.provider.ContactsContract;
import android.test.mock.MockContext;
import android.util.Log;

import java.util.List;

import com.example.noahrickles.waterreporterteam14_harambelovedwaters.controllers.MainActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Noah Rickles on 2/27/2017.
 */

public class Singleton {
    private static final Singleton instance = new Singleton();

    FirebaseDatabase db = FirebaseDatabase.getInstance();

    //a hash set to hold the registered User objects
    private Set<User> registeredUserSet = new HashSet<User>();

    //a hash map to hold the registered users
    //each username and email is mapped to its corresponding value
    private HashMap<String, String> registeredUserMap = new HashMap<String, String>();

    //a list to hold the submitted water reports
    private ArrayList<WaterReport> reportList = new ArrayList<>();
    private ArrayList<WaterPurityReport> purityReportList = new ArrayList<>();

    //keeps track of the user currently logged in
    private User currUser;

    /**
     * Gets the current user that is logged in
     * @return the user that is currently logged in
     */
    public User getCurrUser() {
        return currUser;
    }

    /**
     * Sets the current user to the user passed in
     * @param user  the user passed in
     */
    public void setCurrUser(User user) {
        currUser = user;
    }

    /**
     * Private constructor to prevent multiple instances of the class being created
     */
    private Singleton() {

    }

    /**
     * Returns the one instance of the Singleton
     * @return the instance of the Singleton
     */
    public static Singleton getInstance() {
        return instance;
    }

    /**
     * Gets the registered users hash map
     * @return the HashMap of the registered users
     */
    public HashMap<String, String> getRegisteredUserMap() {
        return registeredUserMap;
    }

    /**
     * Gets the registered users hash set
     * @return the HashSet of the registered users
     */
    public Set<User> getRegisteredUserSet() {
        return registeredUserSet;
    }

    /**
     * Determines if the email provided is acceptable. An email must be longer than 4 characters
     * and contain the @ symbol and a ".".
     * @param email     the user's email address
     * @return true if the email is valid, false otherwise
     */
    public boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".") && email.length() >= 4;
    }

    /**
     * Determines if the username provided is acceptable. A username must be longer
     * than 5 characters
     * @param username  the user's username
     * @return true if the username is valid, false otherwise
     */
    public boolean isUsernameValid(String username) {
        return !username.equals("") && username.length() >= 5;
    }

    /**
     * Determines if the username provided is acceptable. A password must be longer than 4
     * characters and contain at least 1 uppercase letter, and at least 1 number.
     * @param password  the user's password
     * @return true if the password is valid, false otherwise.
     */
    public boolean isPasswordValid(String password) {
        return password.length() >= 4 && password.matches(".*\\d+.*")
                && !password.equals(password.toLowerCase());
    }

    /**
     * Checks to see if the location passed in is valid
     * @param location  the location passed in to be checked
     */
    public boolean isLocationValid(LatLng location) {
        return !location.equals(null);
    }

    /**
     * Adds a WaterReport to the list of water reports
     * @param w     the water report passed in
     */
    public void addWaterReport(WaterReport w) {
        db.getReference("waterReports")
                .child(Integer.toString(reportList.size()))
                .setValue(w);
        //reportList.add(w);
    }

    public void addUser(User u) {
        db.getReference("users").child(Integer.toString(u.getId())).setValue(u);
    }

    /**
     * Adds a WaterPurityReport to the list of water purity reports
     * @param w     the water report passed in
     */
    public void addWaterPurityReport(WaterPurityReport w) {
        db.getReference("waterPurityReports")
                .child(Integer.toString(purityReportList.size()))
                .setValue(w);
        //purityReportList.add(w);
    }

    /**
     * Gets the list of water reports
     * @return the ArrayList reportList of type WaterReport
     */
    public ArrayList<WaterReport> getWaterReports() {
        return reportList;
    }

    /**
     * Gets the list of water purity reports
     * @return the ArrayList purityReportList of type WaterPurityReport
     */
    public ArrayList<WaterPurityReport> getWaterPurityReports() {
        return purityReportList;
    }

    /**
     * Returns the water report by the corresponding id
     * @param id
     * @return the desired WaterReport (or null if not found)
     */
    public WaterReport findWaterReportById(int id) {
        for (WaterReport report : reportList) {
            if (report != null && report.getReportNumber() == id) {
                return report;
            }
        }
        return null;
    }

    public Address findAddressFromName(String address, Geocoder geocoder) throws IOException {
        List<Address> addrList = geocoder.getFromLocationName(address, 1);
        if (addrList.size() > 0) {
            return addrList.get(0);
        }
        throw new IOException();
    }

    public HashMap<Integer, Double> getGraphPoints(String location, String year) {
        HashMap<Integer, Double> graphPoints = new HashMap<>();
        int[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        for (int i = 1; i <= 12; i++) {
            int entryCount = 0;
            double monthlySum = 0;
            {
                for (WaterPurityReport w : getWaterPurityReports()) {
                    String reportYear = w.getDateAndTime().substring(6, 10);
                    String loc = w.getAddress();

                    if (loc.equals(location) && reportYear.equals(year)) {
                        monthlySum += w.getContaminantPPM();
                        entryCount++;
                    }
                }
                double monthlyAvg = (monthlySum / entryCount);
                graphPoints.put(months[i-1], monthlyAvg);
            }
        }

        return graphPoints;
    }

    public void setupDatabaseReferences() {
        DatabaseReference users = db.getReference().child("users");
        DatabaseReference waterReports = db.getReference().child("waterReports");
        DatabaseReference waterPurityReports = db.getReference().child("waterPurityReports");
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<List<User>> l = new GenericTypeIndicator<List<User>>() {};
                List<User> userList = dataSnapshot.getValue(l);
                if (registeredUserSet.size() == 0) {
                    for (User u : userList) {
                        registeredUserMap.put(u.getEmail(), u.getPassword());
                        registeredUserMap.put(u.getUsername(), u.getPassword());
                        registeredUserSet.add(u);
                    }
                } else {
                    User u = userList.get(userList.size() - 1);
                    registeredUserMap.put(u.getEmail(), u.getPassword());
                    registeredUserMap.put(u.getUsername(), u.getPassword());
                    registeredUserSet.add(u);
                }
                Log.d("Success", "Value is: " + userList);
                Log.d("Set", registeredUserSet.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failure", "Failed to read value.", error.toException());
            }
        });

        waterReports.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<List<WaterReport>> l =
                        new GenericTypeIndicator<List<WaterReport>>() {};
                List<WaterReport> reportListDB = dataSnapshot.getValue(l);
                if (reportList.size() == 0) {
                    for (WaterReport r : reportListDB) {
                        reportList.add(r);
                    }
                } else {
                    for (WaterReport r : reportListDB) {
                        if (!reportList.contains(r)) {
                            reportList.add(r);
                        }
                    }
                }
                Log.d("Success", "Value is: " + reportListDB);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failure", "Failed to read value.", error.toException());
            }
        });

        waterPurityReports.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<List<WaterPurityReport>> l =
                        new GenericTypeIndicator<List<WaterPurityReport>>() {};
                List<WaterPurityReport> purityReportListDB = dataSnapshot.getValue(l);
                if (purityReportList.size() == 0) {
                    for (WaterPurityReport r : purityReportListDB) {
                        purityReportList.add(r);
                    }
                } else {
                    for (WaterPurityReport r : purityReportListDB) {
                        if (!purityReportList.contains(r)) {
                            purityReportList.add(r);
                        }
                    }
                }
                Log.d("Success", "Value is: " + purityReportListDB);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failure", "Failed to read value.", error.toException());
            }
        });
    }
}
