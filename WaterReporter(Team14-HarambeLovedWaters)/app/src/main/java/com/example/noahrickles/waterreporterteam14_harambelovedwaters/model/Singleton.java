package com.example.noahrickles.waterreporterteam14_harambelovedwaters.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.util.List;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Noah Rickles on 2/27/2017.
 */

@SuppressWarnings({"DefaultFileTemplate", "BooleanMethodIsAlwaysInverted"})
public class Singleton {
    private static final Singleton instance = new Singleton();

    //a hash set to hold the registered User objects
    private final Set<User> registeredUserSet = new HashSet<>();

    //a hash map to hold the registered users
    //each username and email is mapped to its corresponding value
    private final HashMap<String, String> registeredUserMap = new HashMap<>();

    //a list to hold the submitted water reports
    private final ArrayList<WaterReport> reportList = new ArrayList<>();
    private final ArrayList<WaterPurityReport> purityReportList = new ArrayList<>();

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
     * Returns the instance of the Firebase Database
     * @return instance of the Firebase Database
     */
    private static FirebaseDatabase getFirebaseInstance() {
        return FirebaseDatabase.getInstance();
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
        return (email != null && email.contains("@") && email.contains(".") && email.length() >= 4);
    }

    /**
     * Determines if the username provided is acceptable. A username must be longer
     * than 5 characters
     * @param username  the user's username
     * @return true if the username is valid, false otherwise
     */
    public boolean isUsernameValid(String username) {
        return (username != null && !username.equals("") && username.length() >= 5);
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
     * Adds a WaterReport to the list of water reports
     * @param w     the water report passed in
     */
    public void addWaterReport(WaterReport w) {
        getFirebaseInstance().getReference("waterReports")
                .child(Integer.toString(reportList.size()))
                .setValue(w);
        reportList.add(w);
    }

    public void addUser(User u) {
        getFirebaseInstance().getReference("users").child(Integer.toString(u.getId())).setValue(u);
    }

    /**
     * Adds a WaterPurityReport to the list of water purity reports
     * @param w     the water report passed in
     */
    public void addWaterPurityReport(WaterPurityReport w) {
        getFirebaseInstance().getReference("waterPurityReports")
                .child(Integer.toString(purityReportList.size()))
                .setValue(w);
        purityReportList.add(w);
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
     * @param id the id to search the water reports for
     * @return the desired WaterReport (or null if not found)
     */
    public WaterReport findWaterReportById(int id) {
        if (id < 0) {
            return null;
        }

        for (WaterReport report : reportList) {
            if (report != null && report.getReportNumber() == id) {
                return report;
            }
        }
        return null;
    }

    /**
     * Returns address based on location name
     * @param address string containing address
     * @param geocoder gives address using location name
     * @return address based on location provided
     * @throws IOException throws exception if address cannot be found
     */
    public Address findAddressFromName(String address, Geocoder geocoder) throws IOException {
        List<Address> addrList = geocoder.getFromLocationName(address, 1);
        if (addrList.size() > 0) {
            return addrList.get(0);
        }
        throw new IOException();
    }

    /**
     * Returns address based on location name
     * @param latitude double representation of the latitude
     * @param longitude double representation of the longitude
     * @param geocoder gives address using location name
     * @return address based on location provided
     * @throws IOException throws exception if address cannot be found
     */
    public Address findAddressFromLatLong(double latitude, double longitude, Geocoder geocoder) throws IOException {
        List<Address> addrList = geocoder.getFromLocation(latitude, longitude, 1);
        if (addrList.size() > 0) {
            return addrList.get(0);
        }
        return null;
    }

    /**
     * Gives Contaminant PPM points to graph based on location/year
     * @param location string specifying location to check
     * @param year string specifying year to check
     * @return graph points for CPPM
     */
    public HashMap<Integer, Double> getCPPMGraphPoints(String location, String year) {
        if (location == null || year == null) {
            return null;
        }

        @SuppressLint("UseSparseArrays") HashMap<Integer, Double> graphPoints = new HashMap<>();
        int[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        for (int i = 1; i <= 12; i++) {
            int entryCount = 0;
            double monthlySum = 0;
            {
                for (WaterPurityReport w : getWaterPurityReports()) {
                    String reportYear = w.getDateAndTime().substring(6, 10);
                    String loc = w.getAddress();
                    Integer reportMonth = Integer.parseInt(w.getDateAndTime().substring(0, 2));
                    if (loc.equals(location) && reportYear.equals(year) && reportMonth == i) {
                        monthlySum += w.getContaminantPPM();
                        entryCount++;
                    }
                }
                double monthlyAvg = (monthlySum / entryCount);
                if (entryCount != 0) {
                    graphPoints.put(months[i-1], monthlyAvg);
                }
            }
        }

        return graphPoints;
    }

    /**
     * Gives Virus PPM points to graph based on location/year
     * @param location string specifying location to check
     * @param year string specifying year to check
     * @return graph points for VPPM
     */
    public HashMap<Integer, Double> getVPPMGraphPoints(String location, String year) {
        if (location == null || year == null) {
            return null;
        }

        @SuppressLint("UseSparseArrays") HashMap<Integer, Double> graphPoints = new HashMap<>();
        int[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        for (int i = 1; i <= 12; i++) {
            int entryCount = 0;
            double monthlySum = 0;
            {
                for (WaterPurityReport w : getWaterPurityReports()) {
                    String reportYear = w.getDateAndTime().substring(6, 10);
                    String loc = w.getAddress();
                    Integer reportMonth = Integer.parseInt(w.getDateAndTime().substring(0, 2));
                    if (loc.equals(location) && reportYear.equals(year) && reportMonth == i) {
                        monthlySum += w.getVirusPPM();
                        entryCount++;
                    }
                }
                double monthlyAvg = (monthlySum / entryCount);
                if (entryCount != 0) {
                    graphPoints.put(months[i-1], monthlyAvg);
                }
            }
        }

        return graphPoints;
    }

    /**
     * Refreshes instance variables storing app information from database.
     */
    public void setupDatabaseReferences() {
        FirebaseDatabase db = getFirebaseInstance();
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
                }
                Log.d("Success", "Value is: " + reportListDB);
                Log.d("asdf", "size: " + reportList.size());
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
