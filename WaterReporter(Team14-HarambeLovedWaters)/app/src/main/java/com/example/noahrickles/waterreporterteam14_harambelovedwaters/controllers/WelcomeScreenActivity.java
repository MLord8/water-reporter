package com.example.noahrickles.waterreporterteam14_harambelovedwaters.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.noahrickles.waterreporterteam14_harambelovedwaters.R;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.Singleton;

public class WelcomeScreenActivity extends AppCompatActivity {

    private final Singleton instance = Singleton.getInstance();

    /**
     * Actions that occur when WelcomeScreenActivity is prompted.
     * @param savedInstanceState     the previously saved state of an instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        instance.setupDatabaseReferences();
    }

    /**
     * Brings the user to the login screen
     * @param view the login button
     */
    public void goLogin(View view) {
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Brings the user to the registration screen
     * @param view the registration button
     */
    public void goRegister(View view) {
        Intent intent = new Intent(getBaseContext(), RegistrationActivity.class);
        startActivity(intent);
    }
}
