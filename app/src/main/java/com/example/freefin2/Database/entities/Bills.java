package com.example.freefin2.Database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.freefin2.Database.FreeFinDatabase;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = FreeFinDatabase.BillsTable)
public class Bills {

    @PrimaryKey(autoGenerate = true)
    int billId;

    private int userId;
    private double amount;
    private LocalDateTime dueDate;

    private boolean isActive;
    private LocalDateTime lastPaidDate;

    public Bills(int billId, double amount, LocalDateTime dueDate, boolean isActive, LocalDateTime lastPaidDate) {
        this.billId = billId;
        this.amount = amount;
        this.dueDate = dueDate;
        this.isActive = isActive;
        this.lastPaidDate = lastPaidDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bills)) return false;
        Bills bills = (Bills) o;
        return billId == bills.billId && userId == bills.userId && Double.compare(amount, bills.amount) == 0 && isActive == bills.isActive && Objects.equals(dueDate, bills.dueDate) && Objects.equals(lastPaidDate, bills.lastPaidDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(billId, userId, amount, dueDate, isActive, lastPaidDate);
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getLastPaidDate() {
        return lastPaidDate;
    }

    public void setLastPaidDate(LocalDateTime lastPaidDate) {
        this.lastPaidDate = lastPaidDate;
    }
}
