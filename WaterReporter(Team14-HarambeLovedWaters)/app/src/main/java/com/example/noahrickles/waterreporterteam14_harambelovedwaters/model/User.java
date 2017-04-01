package com.example.noahrickles.waterreporterteam14_harambelovedwaters.model;

import com.example.noahrickles.waterreporterteam14_harambelovedwaters.controllers.RegistrationActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Noah Rickles on 2/21/2017.
 */

public class User {
    private String email;
    private String username;
    private String password;
    private String homeAddress;
    private UserType userType;
    private int id;

    public User() {

    }

    /**
     * Creates a new User object
     * @param email     the User's email
     * @param username  the User's username
     * @param password  the User's password
     * @param id        the User's id
     */
    public User(String email, String username, String password, int id, UserType userType) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.id = id;
        this.userType = userType;
    }

    /**
     * Gets the email of the User
     * @return the User's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the username of the User
     * @return the User's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the User
     * @return the User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the home address of the User
     * @return the User's home address
     */
    public String getHomeAddress() { return homeAddress; }

    /**
     * Gets the id of the User
     * @return the id of the User
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the user type from this class
     * @return the user type from this class
     */
    public String getUserType() {
        return userType.toString();
    }

    /**
     * Sets the email of the User
     * @param newEmail  the User's new email
     */
    public void setEmail(String newEmail) {
        email = newEmail;
    }

    /**
     * Sets the username of the User
     * @param newUsername   the User's new username
     */
    public void setUsername(String newUsername) {
        username = newUsername;
    }

    /**
     * Sets the password of the User
     * @param newPassword   the User's new password
     */
    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public void setUserType(UserType newUserType) {
        userType = newUserType;
    }

    /**
     * Sets the home address of the User
     * @param newHomeAddress    the User's new home address
     */
    public void setHomeAddress(String newHomeAddress) { homeAddress = newHomeAddress; }

    /**
     * Gets a String representation of the User, including important
     * information about the User
     * @return the String representation of the User
     */
    public String toString() {
        return "email: " + email + " username: " + username + " password: " + password
                + " user type: " + getUserType() + " id: " + id + " home address: " + homeAddress;
    }
}
