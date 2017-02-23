package com.example.noahrickles.waterreporterteam14_harambelovedwaters.model;

/**
 * Created by Noah Rickles on 2/21/2017.
 */

public class Manager extends User {

    /**
     * Creates a new Manager object
     * @param email the Manager's email
     * @param username the Manager's username
     * @param password the Manager's password
     * @param id the Manager's id, corresponds to the user list
     */
    public Manager(String email, String username, String password, int id) {
        super(email, username, password, id);
    }

    /**
     * Gets the user type of this class
     * @return the user type from this class, MANAGER
     */
    public String getUserType() {
        return "MANAGER";
    }
}
