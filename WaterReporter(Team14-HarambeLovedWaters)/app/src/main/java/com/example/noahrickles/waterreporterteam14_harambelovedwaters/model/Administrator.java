package com.example.noahrickles.waterreporterteam14_harambelovedwaters.model;

/**
 * Created by Noah Rickles on 2/21/2017.
 */

public class Administrator extends User {

    /**
     * Creates a new Administrator object
     * @param email the Administrator's email
     * @param username the Administrator's username
     * @param password the Administrator's password
     * @param id the Administrator's id, corresponds to the user list
     */
    public Administrator(String email, String username, String password, int id) {
        super(email, username, password, id);
    }

    /**
     * Gets the user type from this class
     * @return the user type from this class, ADMINISTRATOR
     */
    public String getUserType() {
        return "ADMINISTRATOR";
    }
}
