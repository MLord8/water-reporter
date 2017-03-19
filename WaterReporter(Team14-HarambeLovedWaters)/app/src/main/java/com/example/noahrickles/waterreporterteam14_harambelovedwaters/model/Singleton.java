package com.example.noahrickles.waterreporterteam14_harambelovedwaters.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import android.location.Address;
import android.location.Geocoder;
import android.content.Context;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
/**
 * Created by Noah Rickles on 2/27/2017.
 */

public class Singleton {
    private static final Singleton instance = new Singleton();

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
        reportList.add(w);
    }

    /**
     * Adds a WaterPurityReport to the list of water purity reports
     * @param w     the water report passed in
     */
    public void addWaterPurityReport(WaterPurityReport w) {
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
     * Returns the water report by the corresponding id
     * @param id
     * @return the desired WaterReport (or null if not found)
     */
    public WaterReport findWaterReportById(int id) {
        for (WaterReport report : reportList) {
            if (report.getReportNumber() == id) {
                return report;
            }
        }
        return null;
    }

    public Address findAddressFromName(Context context, String address) throws IOException {
        List<Address> addrList = new Geocoder(context).getFromLocationName(address, 1);
        if (addrList.size() > 0) {
            return addrList.get(0);
        }
        throw new IOException();
    }
}
