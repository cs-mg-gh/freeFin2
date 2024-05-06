package com.example.freefin2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.freefin2.Database.entities.Notifications;
import com.example.freefin2.viewHolder.FreeFinnViewModel;

public class NotificationsActivity extends AppCompatActivity {
    private EditText titleInput, targetAmountInput, currentAmountInput;
    private Button saveButton;
    private FreeFinnViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        titleInput = findViewById(R.id.titleInput);
        targetAmountInput = findViewById(R.id.targetAmountInput);
        currentAmountInput = findViewById(R.id.currentAmountInput);
        saveButton = findViewById(R.id.saveButton);

        viewModel = new ViewModelProvider(this).get(FreeFinnViewModel.class);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotification();
            }
        });
    }

    private void saveNotification() {
        String title = titleInput.getText().toString();
        double targetAmount = Double.parseDouble(targetAmountInput.getText().toString());
        double currentAmount = Double.parseDouble(currentAmountInput.getText().toString());

        Notifications notification = new Notifications(title, targetAmount, currentAmount);
        viewModel.insert(notification);
    }
}