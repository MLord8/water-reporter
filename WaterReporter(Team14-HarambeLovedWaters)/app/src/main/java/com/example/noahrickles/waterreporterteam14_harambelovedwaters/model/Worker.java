package com.example.noahrickles.waterreporterteam14_harambelovedwaters.model;

/**
 * Created by Noah Rickles on 2/21/2017.
 */

public class Worker extends User {

    /**
     * Creates a new Worker object
     * @param email the Worker's email
     * @param username the Worker's username
     * @param password the Worker's password
     * @param id the Worker's id, corresponds to the user list
     */
    public Worker(String email, String username, String password, int id) {
        super(email, username, password, id);
    }

    /**
     * Gets the user type from this class
     * @return the user type from this class, WORKER
     */
    public String getUserType() {
        return "WORKER";
    }
}
