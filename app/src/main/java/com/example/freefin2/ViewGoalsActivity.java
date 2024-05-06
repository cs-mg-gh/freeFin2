package com.example.freefin2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.freefin2.viewHolder.FreeFinnViewModel;

public class ViewGoalsActivity extends AppCompatActivity {
    private RecyclerView goalsRecyclerView;
    private GoalsAdapter adapter;
    private FreeFinnViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_goals);

        goalsRecyclerView = findViewById(R.id.goalsRecycler);
        goalsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GoalsAdapter();
        goalsRecyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(FreeFinnViewModel.class);
        viewModel.getAllGoals().observe(this, goals -> {
            adapter.setGoalsList(goals);
        });

        setupNavigationButtons();
    }

    private void setupNavigationButtons() {
        Button homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(v -> startActivity(new Intent(this, LandingPageActivity.class)));

        Button setGoalButton = findViewById(R.id.set_goal_button);
        setGoalButton.setOnClickListener(v -> startActivity(new Intent(this, SetGoalActivity.class)));

        Button settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
    }
}

