package com.example.noahrickles.waterreporterteam14_harambelovedwaters.model;

/**
 * Created by Noah Rickles on 2/21/2017.
 */

public class Manager extends User {

    public Manager(String email, String username, String password, int id) {
        super(email, username, password, id);
    }

    public String getUserType() {
        return "MANAGER";
    }
}
