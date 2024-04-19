package com.example.freefin2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;

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

        // Replace "admin" and "admin" with your actual username and password logic
        if ("admin".equals(username) && "admin".equals(password)) {
            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_LONG).show();
        }
    }
}
