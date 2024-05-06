package com.example.freefin2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Update;

public class SettingsActivity extends AppCompatActivity {
    private Switch switchNotifications;
    private Spinner spinnerTheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button homeButton = findViewById(R.id.home_button);
        Button setGoalButton = findViewById(R.id.set_goal_button);
        Button settingsButton = findViewById(R.id.settings_button);

        switchNotifications = findViewById(R.id.switch_notifications);
        spinnerTheme = findViewById(R.id.spinner_theme);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.theme_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTheme.setAdapter(adapter);

        homeButton.setOnClickListener(v -> {
            // Intent to navigate to the Home activity
            startActivity(new Intent(SettingsActivity.this, LandingPageActivity.class));
        });

        setGoalButton.setOnClickListener(v -> {
            // Intent to navigate to the Set Goal activity
            startActivity(new Intent(SettingsActivity.this, SetGoalActivity.class));
        });

        settingsButton.setOnClickListener(v -> {
            // Refresh the Settings activity or use finish() if no need to reload
            startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
            finish();
        });
        loadSettings();

    }
    private void loadSettings() {
        // Load settings from SharedPreferences or another persistent storage
        // For example, you might check if notifications are enabled:
        boolean notificationsEnabled = true; // This should come from SharedPreferences
        switchNotifications.setChecked(notificationsEnabled);

        // Similarly, set the selected theme in the spinner
        int themePosition = 0; // This should be loaded from SharedPreferences
        spinnerTheme.setSelection(themePosition);
    }
}
