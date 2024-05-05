package com.example.freefin2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Update;

public class UpdateBalanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_balance);

        Button homeButton = findViewById(R.id.home_button);
        Button setGoalButton = findViewById(R.id.set_goal_button);
        Button settingsButton = findViewById(R.id.settings_button);

        // Set click listeners for each button
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action for the Home button
                startActivity(new Intent(UpdateBalanceActivity.this, LandingPageActivity.class));
            }
        });

        setGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateBalanceActivity.this, SetGoalActivity.class));
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateBalanceActivity.this, SettingsActivity.class));
            }
        });
    }
}

