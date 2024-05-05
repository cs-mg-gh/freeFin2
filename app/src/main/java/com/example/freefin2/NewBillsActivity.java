package com.example.freefin2;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.freefin2.Database.FreeFinDao;
import com.example.freefin2.Database.entities.Bills;
import com.example.freefin2.Database.entities.FreeFinUser;
import com.example.freefin2.viewHolder.FreeFinnViewModel;

import java.text.BreakIterator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

public class NewBillsActivity extends AppCompatActivity {
    private EditText amountInput, dueDateInput;
    private Switch isActiveSwitch;
    private Button saveButton;
    private DatePickerDialog datePickerDialog; // Declare as a class member

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bills);

        amountInput = findViewById(R.id.amountInput);
        dueDateInput = findViewById(R.id.dueDateInput);
        isActiveSwitch = findViewById(R.id.isActiveSwitch);
        saveButton = findViewById(R.id.saveButton);

        initDatePicker(); // Initialize the DatePickerDialog here

        // Setting the click listener to the dueDateInput to show DatePickerDialog
        dueDateInput.setOnClickListener(view -> datePickerDialog.show());

        saveButton.setOnClickListener(view -> saveBill());
    }

    private void saveBill() {
        try {
            // Parse the amount and ensure it's a valid double
            double amount = Double.parseDouble(amountInput.getText().toString());

            // Parse the due date, ensuring the input matches expected format (handled by the date picker)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDateTime dueDate = LocalDateTime.parse(dueDateInput.getText().toString(), formatter);

            // Read the active status from the switch
            boolean isActive = isActiveSwitch.isChecked();

            // Assuming that billId is auto-generated and lastPaidDate is null initially
            int billId = 0; // Set to 0 or another mechanism if your DB auto-generates IDs
            LocalDateTime lastPaidDate = null; // Set this as needed

            // Create new bill object with all properties;
            Bills bill = new Bills(billId, amount, dueDate, isActive, lastPaidDate);

            // Use ViewModel to insert the bill into the database
            FreeFinnViewModel viewModel = new ViewModelProvider(this).get(FreeFinnViewModel.class);
            viewModel.insertBill(bill);

            finish(); // Close the activity after saving
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount entered", Toast.LENGTH_SHORT).show();
        } catch (DateTimeParseException e) {
            Toast.makeText(this, "Invalid date entered", Toast.LENGTH_SHORT).show();
        }
    }


    private void initDatePicker() {
        // Get today's date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Initialize DatePickerDialog
        datePickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
            // Set dueDateInput to show the selected date
            String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
            dueDateInput.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.setCancelable(false); // Make dialog non-cancelable if desired
    }
}
