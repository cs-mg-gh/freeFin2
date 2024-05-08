package com.example.freefin2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    public static String MAIN_ACTIVITY_USER_ID="com.example.freefin2.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.freefin2.SHARED_PREFERENCE_USERID_KEY";
    public static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.freefin2.SAVED_INSTANCE_STATE_USERID_KEY";

    public static final String TAG= "FreeFin";
    private FreeFinLogRepo repository;
    com.example.freefin2.databinding.ActivityMainBinding binding;
    private Button createAccountButton;
    private int loggedInUserId = -1;
    private FreeFinUser user;
    private int LOGGED_OUT=-1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.freefin2.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginUser(savedInstanceState);

        if (loggedInUserId == -1) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }
        updateSharedPreferences();

        repository = FreeFinLogRepo.getRepository(getApplication());
        Button loginButton = findViewById(R.id.buttonLogin);
        createAccountButton = findViewById(R.id.buttonCreateAccount);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, CreateAccountActivity.class));
            }
        });
        logout();
    }

    private void loginUser(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_usedId_key), LOGGED_OUT);

        if (loggedInUserId == LOGGED_OUT && savedInstanceState != null) {
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }

        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }

        if (loggedInUserId != LOGGED_OUT) {
            LiveData<FreeFinUser> userObserver = repository.getUserById(loggedInUserId);
            userObserver.removeObservers(this); // Remove previous observers to prevent multiple triggers
            userObserver.observe(this, user -> {
                this.user = user;
                if (this.user != null) {
                    invalidateOptionsMenu();
                }
            });
        } else {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
            finish();
        }
    }
    private void updateUser(FreeFinUser newUser) {
        user = newUser;
        if (user != null) {
            invalidateOptionsMenu(); // This will trigger onPrepareOptionsMenu
        }
    }

    @Override
    protected void onSaveInstanceState(@Nullable Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
        updateSharedPreferences();

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

    public void logout() {

        loggedInUserId = LOGGED_OUT;
        updateSharedPreferences();

        getIntent().putExtra(MAIN_ACTIVITY_USER_ID,LOGGED_OUT);
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }
private void updateSharedPreferences(){
    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
    SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
    sharedPrefEditor.putInt(getString(R.string.preference_usedId_key),loggedInUserId);
    sharedPrefEditor.apply();
}
    static Intent mainActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}