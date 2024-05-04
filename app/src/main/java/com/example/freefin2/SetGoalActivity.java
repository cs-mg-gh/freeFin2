package com.example.freefin2;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SetGoalActivity extends AppCompatActivity {

    private EditText editTextGoalTitle, editTextGoalAmount, editTextGoalDeadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goal);

        // Initialize EditText fields
        editTextGoalTitle = findViewById(R.id.editTextGoalTitle);
        editTextGoalAmount = findViewById(R.id.editTextGoalAmount);
        editTextGoalDeadline = findViewById(R.id.editTextGoalDeadline);

        // Save Button
        Button buttonSaveGoal = findViewById(R.id.buttonSaveGoal);
        buttonSaveGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGoal();
            }
        });

        // Cancel Button
        Button buttonCancelGoal = findViewById(R.id.buttonCancelGoal);
        buttonCancelGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelGoal();
            }
        });
    }

    // Method to handle saving the goal
    private void saveGoal() {
        String goalTitle = editTextGoalTitle.getText().toString().trim();
        String goalAmount = editTextGoalAmount.getText().toString().trim();
        String goalDeadline = editTextGoalDeadline.getText().toString().trim();

        // Perform validation
        if (goalTitle.isEmpty() || goalAmount.isEmpty() || goalDeadline.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert amount to double
        double amount = Double.parseDouble(goalAmount);

        // Save goal to database or perform necessary action
        // For demonstration, we just display a toast message
        Toast.makeText(this, "Goal saved: " + goalTitle + " - $" + amount + " by " + goalDeadline, Toast.LENGTH_LONG).show();

        // Optionally, navigate back to landing page or any other activity
        // Here we navigate back to the landing page
        Intent intent = new Intent(this, LandingPageActivity.class);
        startActivity(intent);
        finish(); // Finish current activity to prevent going back to it using back button
    }

    // Method to handle canceling the goal action
    private void cancelGoal() {
        // Finish the activity to go back to the previous screen
        finish();
    }
}
