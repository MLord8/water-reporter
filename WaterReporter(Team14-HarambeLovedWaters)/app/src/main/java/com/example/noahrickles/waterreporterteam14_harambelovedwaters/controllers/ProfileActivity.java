package com.example.noahrickles.waterreporterteam14_harambelovedwaters.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.noahrickles.waterreporterteam14_harambelovedwaters.R;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.User;

import java.util.HashMap;
import java.util.Set;

public class ProfileActivity extends AppCompatActivity {
    private User currUser;
    private EditText mUsernameView;
    private TextView mUserTypeView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mHomeAddressView;

    private TextView mErrorView;
    private String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        currUser = LoginActivity.getCurrUser();
        displayProfile(currUser);
    }

    /**
     *
     * @param user
     */
    private void displayProfile(User user) {
        mUsernameView = (EditText) findViewById(R.id.username_text);
        mUserTypeView = (TextView) findViewById(R.id.user_type_text);
        mEmailView = (EditText) findViewById(R.id.email_text);
        mPasswordView = (EditText) findViewById(R.id.password_text);
        mHomeAddressView = (EditText) findViewById(R.id.home_address_text);
        mErrorView = (TextView) findViewById(R.id.error_text);

        mUsernameView.setText(user.getUsername());
        mUserTypeView.setText(user.getUserType());
        mEmailView.setText(user.getEmail());
        mPasswordView.setText(user.getPassword());
        mHomeAddressView.setText(user.getHomeAddress());
        mErrorView.setText(error);
    }

    /**
     *
     * @param view
     */
    public void saveChanges(View view) {
        User user = currUser;
        error = "";

        if (!mUsernameView.getText().toString().equals(user.getUsername())) {
            changeUsername(user, mUsernameView.getText().toString());
        }

        if (!mEmailView.getText().toString().equals(user.getEmail())) {
            changeEmail(user, mEmailView.getText().toString());
        }

        if (!mPasswordView.getText().toString().equals(user.getPassword())) {
            changePassword(user, mPasswordView.getText().toString());
        }

        if (!mHomeAddressView.getText().toString().equals(user.getHomeAddress())) {
            changeHomeAddress(user, mHomeAddressView.getText().toString());
        }

        displayProfile(user);
    }

    /**
     *
     * @param user
     * @param newUsername
     */
    private void changeUsername(User user, String newUsername) {
        HashMap<String, String> registeredUserMap = RegistrationActivity.getRegisteredUserMap();
        Set<User> registeredUserSet = RegistrationActivity.getRegisteredUserSet();
        if (!registeredUserMap.containsKey(newUsername)) {
            if (!RegistrationActivity.isUsernameValid(newUsername)) {
                error = "This username is invalid.";
            } else {
                registeredUserMap.remove(user.getUsername(), user.getPassword());
                registeredUserSet.remove(user);
                user.setUsername(newUsername);
                registeredUserMap.put(newUsername, user.getPassword());
                registeredUserSet.add(user);
            }
        } else {
            error = "This username is currently in use.";
        }
    }

    /**
     *
     * @param user
     * @param newEmail
     */
    private void changeEmail(User user, String newEmail) {
        HashMap<String, String> registeredUserMap = RegistrationActivity.getRegisteredUserMap();
        Set<User> registeredUserSet = RegistrationActivity.getRegisteredUserSet();
        if (!registeredUserMap.containsKey(newEmail)) {
            if (!RegistrationActivity.isEmailValid(newEmail)) {
                error = "This email is invalid.";
            } else {
                registeredUserMap.remove(user.getUsername(), user.getEmail());
                registeredUserSet.remove(user);
                user.setEmail(newEmail);
                registeredUserMap.put(newEmail, user.getPassword());
                registeredUserSet.add(user);
            }
        } else {
            error = "This email is currently in use.";
        }
    }

    /**
     *
     * @param user
     * @param newPassword
     */
    private void changePassword(User user, String newPassword) {
        HashMap<String, String> registeredUserMap = RegistrationActivity.getRegisteredUserMap();
        Set<User> registeredUserSet = RegistrationActivity.getRegisteredUserSet();
        if (RegistrationActivity.isPasswordValid(newPassword)) {
            registeredUserMap.remove(user.getUsername(), user.getPassword());
            registeredUserMap.remove(user.getEmail(), user.getPassword());
            registeredUserSet.remove(user);
            user.setPassword(newPassword);
            registeredUserMap.put(user.getUsername(), newPassword);
            registeredUserSet.add(user);
        } else {
            error = "This password is not valid.";
        }
    }

    /**
     *
     * @param user
     * @param newHomeAddress
     */
    private void changeHomeAddress(User user, String newHomeAddress) {
        user.setHomeAddress(newHomeAddress);
    }

    public void cancel(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
