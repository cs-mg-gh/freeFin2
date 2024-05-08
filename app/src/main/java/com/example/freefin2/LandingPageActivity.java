package com.example.freefin2;
import static com.example.freefin2.MainActivity.MAIN_ACTIVITY_USER_ID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.freefin2.viewHolder.FreeFinnViewModel;

public class LandingPageActivity extends AppCompatActivity {
    private TextView usernameTextView;
    private int loggedInUserId = -1;
    private int LOGGED_OUT=-1;
    private Button adminButton;
    private boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        usernameTextView = findViewById(R.id.usernameTextView);
        adminButton = findViewById(R.id.adminButton);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(String.valueOf(R.string.preference_usedId_key), "");

        isAdmin = sharedPreferences.getBoolean("isAdmin", false);
        usernameTextView.setText(username);

        if (isAdmin) {
            adminButton.setVisibility(View.VISIBLE);
            adminButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LandingPageActivity.this, AdminActivity.class));
                }
            });
        } else {
            adminButton.setVisibility(View.GONE);
        }
        findViewById(R.id.set_goal_button).setOnClickListener(v -> startActivity(new Intent(LandingPageActivity.this, SetGoalActivity.class)));
        logout();

        findViewById(R.id.set_goal_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPageActivity.this, SetGoalActivity.class));
            }
        });
        findViewById(R.id.buttonViewGoals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPageActivity.this, ViewGoalsActivity.class));
            }
        });
        findViewById(R.id.settings_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPageActivity.this, SettingsActivity.class));
            }
        });
        findViewById(R.id.buttonUpdateBalance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPageActivity.this, UpdateBalanceActivity.class));
            }
        });
        findViewById(R.id.NewBillbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPageActivity.this, NewBillsActivity.class));
            }
        });
        logout();

    }
    private void logout() {

        loggedInUserId = LOGGED_OUT;
        updateSharedPreferences();

        getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
    }
    private void updateSharedPreferences(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_usedId_key),loggedInUserId);
        sharedPrefEditor.apply();
    }
}