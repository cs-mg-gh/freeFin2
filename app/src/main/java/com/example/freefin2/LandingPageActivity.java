package com.example.freefin2;
import static com.example.freefin2.MainActivity.MAIN_ACTIVITY_USER_ID;
import static com.example.freefin2.MainActivity.SAVED_INSTANCE_STATE_USERID_KEY;
import static com.example.freefin2.R.string.preference_usedId_key;

import android.app.MediaRouteButton;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.freefin2.Database.FreeFinLogRepo;
import com.example.freefin2.Database.entities.FreeFinUser;
import com.example.freefin2.viewHolder.FreeFinnViewModel;

public class LandingPageActivity extends AppCompatActivity {
    private TextView usernameTextView;
    private Button adminButton;
    private int LOGGED_OUT=-1;
    private int loggedInUserId = -1;
    private FreeFinLogRepo repository;

    private FreeFinUser user;
    private FreeFinUser newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        usernameTextView = findViewById(R.id.usernameTextView);

        loginUser(savedInstanceState);

        if (loggedInUserId == -1) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }
        updateSharedPreferences();

        repository = FreeFinLogRepo.getRepository(getApplication());
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(String.valueOf(preference_usedId_key), "DefaultUser");

        usernameTextView.setText(username);

        findViewById(R.id.set_goal_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open SetGoalActivity when "Set Goal" button is clicked
                startActivity(new Intent(LandingPageActivity.this, SetGoalActivity.class));
            }
        });

    }
    private void loginUser(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // Try to retrieve the logged-in user ID from SharedPreferences
        if (sharedPreferences.contains(getString(preference_usedId_key))) {
            loggedInUserId = sharedPreferences.getInt(getString(preference_usedId_key), LOGGED_OUT);
        }

        // If not found, try to restore it from savedInstanceState
        if (loggedInUserId == LOGGED_OUT && savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }

        // If still not found, try to get it from the Intent extras
        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }

        // If we have a valid user ID, observe the user data from the repository
        if (loggedInUserId != LOGGED_OUT) {
            LiveData<FreeFinUser> userObserver = repository.getUserById(loggedInUserId);
            userObserver.observe(this, this::updateUser);
        } else {
            // If no valid user ID is found, redirect to the login activity
            Intent intent = LoginActivity.loginIntentFactory(this);
            startActivity(intent);
            finish(); // Finish this activity to remove it from the back stack
        }
    }

    private void updateUser(FreeFinUser newUser) {
        this.user = newUser;
        if (this.user != null) {
            usernameTextView.setText(user.getUsername());
            adminButton.setVisibility(user.isAdmin() ? View.VISIBLE : View.GONE);
            invalidateOptionsMenu(); // Refresh menu, it will check user state and adjust accordingly
        }
    }
    @Override
    protected void onSaveInstanceState(@Nullable Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
        updateSharedPreferences();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logoutMenuItem) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                    Toast.makeText(LandingPageActivity.this, "No user logged in", Toast.LENGTH_SHORT).show();
                    return true;
                }
                Toast.makeText(LandingPageActivity.this, "Logout coming", Toast.LENGTH_SHORT).show();
                showLogoutDialog();
                return true;
            }
        });
        return true;
    }

    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LandingPageActivity.this);
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
        updateSharedPreferences();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); // Close this activity
    }
    private void updateSharedPreferences(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(preference_usedId_key),loggedInUserId);
        sharedPrefEditor.apply();
    }
    static Intent mainActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}