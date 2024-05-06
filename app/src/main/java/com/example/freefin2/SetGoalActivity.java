package com.example.freefin2;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.freefin2.Database.entities.Goals;
import com.example.freefin2.viewHolder.FreeFinnViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SetGoalActivity extends AppCompatActivity {
    private EditText editTextGoalTitle, editTextGoalAmount, editTextGoalDeadline;
    private FreeFinnViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goal);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(FreeFinnViewModel.class);

        // Initialize EditText fields
        editTextGoalTitle = findViewById(R.id.editTextGoalTitle);
        editTextGoalAmount = findViewById(R.id.editTextGoalAmount);
        editTextGoalDeadline = findViewById(R.id.editTextGoalDeadline);
        editTextGoalDeadline.setOnClickListener(v -> showDatePickerDialog());

        Button buttonSaveGoal = findViewById(R.id.buttonSaveGoal);
        buttonSaveGoal.setOnClickListener(v -> saveGoal());

        Button buttonCancelGoal = findViewById(R.id.buttonCancelGoal);
        buttonCancelGoal.setOnClickListener(v -> finish());
    }

    private void showDatePickerDialog() {
        // Get the current date
        LocalDate now = LocalDate.now();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Adjust month indexing from DatePicker
                    LocalDate date = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                    // Format the date and set to EditText
                    editTextGoalDeadline.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                },
                now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());

        datePickerDialog.show();
    }

    private void saveGoal() {
        String goalTitle = editTextGoalTitle.getText().toString().trim();
        String goalAmountStr = editTextGoalAmount.getText().toString().trim();
        String goalDeadlineStr = editTextGoalDeadline.getText().toString().trim();

        if (goalTitle.isEmpty() || goalAmountStr.isEmpty() || goalDeadlineStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double goalAmount;
        try {
            goalAmount = Double.parseDouble(goalAmountStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount format", Toast.LENGTH_SHORT).show();
            return;
        }

        LocalDateTime goalDeadline;
        try {
            goalDeadline = LocalDate.parse(goalDeadlineStr, DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
        } catch (DateTimeParseException e) {
            Toast.makeText(this, "Invalid date format. Please use dd/MM/yyyy", Toast.LENGTH_SHORT).show();
            return;
        }

        Goals goal = new Goals(goalTitle, goalAmount, goalDeadline);
        viewModel.insertGoal(goal);

        Toast.makeText(this, "Goal saved successfully", Toast.LENGTH_LONG).show();
        finish();
    }
}
