package com.example.freefin2.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.freefin2.Database.FreeFinDatabase;

import java.time.LocalDateTime;

@Entity(tableName = FreeFinDatabase.GoalsTable)
public class Goals {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Double amount;
    private LocalDateTime date;


    public Goals(String title, Double amount, LocalDateTime date) {
        this.title = title;
        this.amount = amount;
        this.date = date;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
