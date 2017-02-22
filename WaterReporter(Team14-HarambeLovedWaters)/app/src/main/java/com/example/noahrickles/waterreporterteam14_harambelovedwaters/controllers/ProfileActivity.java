package com.example.noahrickles.waterreporterteam14_harambelovedwaters.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.noahrickles.waterreporterteam14_harambelovedwaters.R;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.User;

public class ProfileActivity extends AppCompatActivity {
    private User currUser;
    private TextView mUsernameView;
    private TextView mUserTypeView;
    private TextView mEmailView;
    private TextView mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        currUser = LoginActivity.getCurrUser();
        displayProfile(currUser);
    }

    /**
     * Displays the user profile
     * @param user the user whose profile is to be displayed
     */
    private void displayProfile(User user) {
        mUsernameView = (TextView) findViewById(R.id.username_text);
        mUserTypeView = (TextView) findViewById(R.id.user_type_text);
        mEmailView = (TextView) findViewById(R.id.email_text);
        mPasswordView = (TextView) findViewById(R.id.password_text);

        mUsernameView.setText(user.getUsername());
        mUserTypeView.setText(user.getUserType());
        mEmailView.setText(user.getEmail());
        mPasswordView.setText(user.getPassword());
    }

    public void cancel(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
