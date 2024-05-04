package com.example.freefin2;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.freefin2.viewHolder.FreeFinnViewModel;

public class LandingPageActivity extends AppCompatActivity {
    private TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        usernameTextView = findViewById(R.id.usernameTextView);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(String.valueOf(R.string.preference_usedId_key), "");

        usernameTextView.setText(username);

        findViewById(R.id.set_goal_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open SetGoalActivity when "Set Goal" button is clicked
                startActivity(new Intent(LandingPageActivity.this, SetGoalActivity.class));
            }
        });
    }
}