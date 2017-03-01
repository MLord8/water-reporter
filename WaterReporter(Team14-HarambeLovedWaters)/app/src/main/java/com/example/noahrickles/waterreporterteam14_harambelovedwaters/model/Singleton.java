package com.example.noahrickles.waterreporterteam14_harambelovedwaters.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Noah Rickles on 2/27/2017.
 */

public class Singleton {
    private static final Singleton instance = new Singleton();

    //a hash set to hold the registered User objects
    private static Set<User> registeredUserSet = new HashSet<User>();

    //a hash map to hold the registered users
    //each username and email is mapped to its corresponding value
    private static HashMap<String, String> registeredUserMap = new HashMap<String, String>();

    private static ArrayList<WaterReport> reportList = new ArrayList<>();

    private static User currUser;

    /**
     * Gets the current user that is logged in
     * @return the user that is currently logged in
     */
    public static User getCurrUser() {
        return currUser;
    }

    public static void setCurrUser(User user) {
        currUser = user;
    }

    private Singleton() {

    }

    public static Singleton getInstance() {
        return instance;
    }

    /**
     * Gets the registered users hash map
     * @return the HashMap of the registered users
     */
    public static HashMap<String, String> getRegisteredUserMap() {
        return registeredUserMap;
    }

    /**
     * Gets the registered users hash set
     * @return the HashSet of the registered users
     */
    public static Set<User> getRegisteredUserSet() {
        return registeredUserSet;
    }

    /**
     * Determines if the email provided is acceptable. An email must be longer than 4 characters
     * and contain the @ symbol and a ".".
     * @param email the user's email address
     * @return true if the email is valid, false otherwise
     */
    public static boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".") && email.length() >= 4;
    }

    /**
     * Determines if the username provided is acceptable. A username must be longer
     * than 5 characters
     * @param username the user's username
     * @return true if the username is valid, false otherwise
     */
    public static boolean isUsernameValid(String username) {
        return !username.equals("") && username.length() >= 5;
    }

    /**
     * Determines if the username provided is acceptable. A password must be longer than 4
     * characters and contain at least 1 uppercase letter, and at least 1 number.
     * @param password the user's password
     * @return true if the password is valid, false otherwise.
     */
    public static boolean isPasswordValid(String password) {
        return password.length() >= 4 && password.matches(".*\\d+.*")
                && !password.equals(password.toLowerCase());
    }

    public static boolean isLocationValid(String location) {
        return !location.equals("") && location.length() > 5;
    }

    public static void addWaterReport(WaterReport w) {
        reportList.add(w);
    }

    public static ArrayList<WaterReport> getWaterReports() {
        return reportList;
    }
}
