package com.example.freefin2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Replace "admin" and "admin" with your actual username and password logic
            if ("admin".equals(username) && "admin".equals(password)) {
                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_LONG).show();
            }
        });
    }
