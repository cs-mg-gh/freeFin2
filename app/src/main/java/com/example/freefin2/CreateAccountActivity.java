package com.example.freefin2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.freefin2.Database.FreeFinDatabase;
import com.example.freefin2.Database.entities.FreeFinUser;
import com.example.freefin2.viewHolder.FreeFinnViewModel;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private FreeFinnViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        Button createButton = findViewById(R.id.CreateButton); // Corrected initialization

        // Initialize viewModel
        viewModel = new ViewModelProvider(this).get(FreeFinnViewModel.class);

        createButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(CreateAccountActivity.this, "Please enter both a username and password", Toast.LENGTH_LONG).show();
            } else {
                FreeFinUser newUser = new FreeFinUser(username, password);
                viewModel.insertUser(newUser);
                Toast.makeText(CreateAccountActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                navigateToLandingPage(); // Navigate to landing page after account creation
            }
        });
    }

    private void navigateToLandingPage() {
        Intent intent = new Intent(CreateAccountActivity.this, LandingPageActivity.class);
        startActivity(intent);
        //finish();
    }
}

   // public void createAccount(View view) {
        //String username = usernameEditText.getText().toString().trim();
        ///String password = passwordEditText.getText().toString();

        //if (username.isEmpty() || password.isEmpty()) {
            //Toast.makeText(this, "Please enter both a username and password", Toast.LENGTH_LONG).show();
       // } else {
            //FreeFinUser newUser = new FreeFinUser(username, password);
            //viewModel.insertUser(newUser);
            //Toast.makeText(this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();

        //}
    //}


