package com.example.freefin2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private static final String TAG = "LoginActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if ("admin".equals(username) && "admin".equals(password)) {
            loginButton.setBackgroundColor(Color.GREEN);
            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
        } else {
            loginButton.setBackgroundColor(Color.WHITE);
            Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "Login button clicked");
    }
}