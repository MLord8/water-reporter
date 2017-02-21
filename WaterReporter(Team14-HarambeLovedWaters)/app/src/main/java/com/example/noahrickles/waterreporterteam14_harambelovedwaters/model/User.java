package com.example.noahrickles.waterreporterteam14_harambelovedwaters.model;

/**
 * Created by Noah Rickles on 2/21/2017.
 */

public class User {
    private String email;
    private String username;
    private String password;
    private int id;

    public User(String email, String username, String password, int id) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getUserType() {
        return "USER";
    }

    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public void setUsername(String newUsername) {
        username = newUsername;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public String toString() {
        return "email: " + email + " username: " + username + " password: " + password
                + " user type: " + getUserType() + " id: " + id;
    }
}
