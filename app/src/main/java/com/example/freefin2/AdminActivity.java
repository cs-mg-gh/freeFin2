package com.example.freefin2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freefin2.Database.FreeFinDatabase;
import com.example.freefin2.Database.entities.FreeFinUser;
import com.example.freefin2.MainActivity;
import com.example.freefin2.viewHolder.FreeFinnViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private RecyclerView usersRecyclerView;
    private UsersAdapter adapter;
    private FreeFinnViewModel viewModel;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        usersRecyclerView = findViewById(R.id.usersRecyclerView);
        logoutButton = findViewById(R.id.logoutButtonn);
        adapter = new UsersAdapter(new ArrayList<>(), this) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        };
        usersRecyclerView.setAdapter(adapter);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(FreeFinnViewModel.class);
        viewModel.getUsers().observe(this, users -> {
            adapter.updateUsers((List<FreeFinUser>) users);
        });

        logoutButton.setOnClickListener(v -> logout());
    }

    public void deleteUser() {
        viewModel.deleteUser();
    }

    private void logout() {
        finish();
    }

}