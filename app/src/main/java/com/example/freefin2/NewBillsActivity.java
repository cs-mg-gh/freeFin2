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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

public class NewBillsActivity extends AppCompatActivity {
    private EditText amountInput, dueDateInput;
    private Switch isActiveSwitch;
    private Button saveButton;
    private DatePickerDialog datePickerDialog;
    private FreeFinnViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bills);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(FreeFinnViewModel.class);

        amountInput = findViewById(R.id.amountInput);
        dueDateInput = findViewById(R.id.dueDateInput);
        isActiveSwitch = findViewById(R.id.isActiveSwitch);
        saveButton = findViewById(R.id.saveButton);

        initDatePicker();

        dueDateInput.setOnClickListener(view -> showDatePickerDialog());

        saveButton.setOnClickListener(view -> saveBill());
    }

    private void saveBill() {
        try {
            double amount = Double.parseDouble(amountInput.getText().toString());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dueDate = LocalDate.parse(dueDateInput.getText().toString(), formatter);
            boolean isActive = isActiveSwitch.isChecked();
            LocalDateTime lastPaidDate = null; // Assuming not used at creation

            Bills bill = new Bills(amount, dueDate.atStartOfDay(), isActive);
            viewModel.insertBill(bill);

            Toast.makeText(this, "Bill saved successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount entered", Toast.LENGTH_SHORT).show();
        } catch (DateTimeParseException e) {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
        }
    }

    private void initDatePicker() {
        LocalDate now = LocalDate.now();
        datePickerDialog = new DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    LocalDate date = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                    dueDateInput.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                },
                now.getYear(),
                now.getMonthValue() - 1,
                now.getDayOfMonth()
        );
    }

    private void showDatePickerDialog() {
        if (datePickerDialog == null) {
            initDatePicker();
        }
        datePickerDialog.show();
    }
}
