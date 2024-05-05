package com.example.freefin2.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.freefin2.Database.FreeFinDatabase;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = FreeFinDatabase.NotificationsTable)
public class Notifications {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private double targetAmount;
    private double currentAmount;
    private LocalDateTime date;


    public Notifications(String title, double targetAmount, double currentAmount) {
        this.title = title;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notifications)) return false;
        Notifications that = (Notifications) o;
        return id == that.id && Double.compare(targetAmount, that.targetAmount) == 0 && Double.compare(currentAmount, that.currentAmount) == 0 && Objects.equals(title, that.title) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, targetAmount, currentAmount, date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
