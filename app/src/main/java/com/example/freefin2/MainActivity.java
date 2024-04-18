package com.example.freefin2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if User is Already Logged In
        if (isLoggedIn()) {
            // User is already logged in. Redirect to LandingPageActivity
            Intent intent = new Intent(MainActivity.this, LandingPageActivity.class);
            startActivity(intent);
            finish(); // Finish MainActivity so user can't go back to it
        }

        // Setup UI Elements
        loginButton = findViewById(R.id.buttonLogin);
        createAccountButton = findViewById(R.id.buttonCreateAccount);

        // Set OnClickListeners for buttons
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start LoginActivity
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CreateAccountActivity
                startActivity(new Intent(MainActivity.this, CreateAccountActivity.class));
            }
        });
    }

    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserLogin", MODE_PRIVATE);
        return sharedPreferences.getBoolean("LoggedIn", false);
    }
}
}