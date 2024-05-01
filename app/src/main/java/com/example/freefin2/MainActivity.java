package com.example.freefin2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.example.freefin2.Database.FreeFinLogRepo;
import com.example.freefin2.Database.entities.FreeFinUser;

public class MainActivity extends AppCompatActivity {
com.example.freefin2.databinding.ActivityMainBinding binding;

    public String MAIN_ACTIVITY_USER_ID;
    public static final String TAG= "FreeFin";
    private FreeFinLogRepo repository;
    private Button createAccountButton;
    private int loggedInUserId = -1;
    private FreeFinUser user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.freefin2.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
            loginUser();
            invalidateOptionsMenu();

        repository = FreeFinLogRepo.getRepository(getApplication());

        if (isLoggedIn()) {

            Intent intent = new Intent(MainActivity.this, LandingPageActivity.class);
            startActivity(intent);
            finish();
        }


        Button loginButton = findViewById(R.id.buttonLogin);
        createAccountButton = findViewById(R.id.buttonCreateAccount);

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

    private void loginUser() {
         loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu,menu);
        return true;
    }

    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserLogin", MODE_PRIVATE);
        return sharedPreferences.getBoolean("LoggedIn", false);
    }
}