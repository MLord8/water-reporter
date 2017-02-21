package com.example.noahrickles.waterreporterteam14_harambelovedwaters.model;

/**
 * Created by Noah Rickles on 2/21/2017.
 */

public class Worker extends User {

    public Worker(String email, String username, String password, int id) {
        super(email, username, password, id);
    }

    public String getUserType() {
        return "WORKER";
    }
}
