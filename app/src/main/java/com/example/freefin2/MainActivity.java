package com.example.freefin2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.freefin2.Database.FreeFinLogRepo;
import com.example.freefin2.Database.entities.FreeFinUser;

public class MainActivity extends AppCompatActivity {
com.example.freefin2.databinding.ActivityMainBinding binding;

    public static String MAIN_ACTIVITY_USER_ID="com.example.freefin2.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.freefin2.SHARED_PREFERENCE_USERID_KEY";
    static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.freefin2.SHARED_PREFERENCE_USERID_VALUE";
    public static final String TAG= "FreeFin";
    private FreeFinLogRepo repository;
    private Button createAccountButton;
    private int loggedInUserId = -1;
    private FreeFinUser user;
    private int LOGGED_OUT=-1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.freefin2.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginUser();

        if (loggedInUserId == -1) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }
        repository = FreeFinLogRepo.getRepository(getApplication());
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
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY,Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_VALUE,LOGGED_OUT);

        if (loggedInUserId != LOGGED_OUT) {
            LiveData<FreeFinUser> userObserver = repository.getUserById(loggedInUserId);
            userObserver.observe(this, this::updateUser); // Handling user update in a method
        }

    }

    private void updateUser(FreeFinUser newUser) {
        user = newUser;
        if (user != null) {
            invalidateOptionsMenu(); // This will trigger onPrepareOptionsMenu
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        if (user != null) {
            item.setVisible(true);
            item.setTitle(user.getUsername());
        } else {
            item.setVisible(false);
        }
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                if (user == null) {
                    Toast.makeText(MainActivity.this, "No user logged in", Toast.LENGTH_SHORT).show();
                    return true;
                }
                Toast.makeText(MainActivity.this, "Logout coming", Toast.LENGTH_SHORT).show();
                showLogoutDialog();
                return true;
            }
        });
        return true;
    }

    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertDialog.setMessage("Logout?");

        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertBuilder.create().show();
    }

    private void logout() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY,Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(SHARED_PREFERENCE_USERID_KEY,LOGGED_OUT);
        sharedPrefEditor.apply();

        getIntent().putExtra(MAIN_ACTIVITY_USER_ID,LOGGED_OUT);
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    static Intent mainActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}