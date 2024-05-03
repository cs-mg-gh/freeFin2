package com.example.freefin2.Database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.freefin2.Database.FreeFinDatabase;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = FreeFinDatabase.NotificationsTable)
public class Notifications {
    @PrimaryKey(autoGenerate = true)
    private int notificationId;
    private int userId;
    private String type;
    private String message;
    private Date date;

    // Constructor, Getters, and Setters
    public Notifications(int userId, String type, String message, Date date) {
        this.userId = userId;
        this.type = type;
        this.message = message;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notifications)) return false;
        Notifications that = (Notifications) o;
        return notificationId == that.notificationId && userId == that.userId && Objects.equals(type, that.type) && Objects.equals(message, that.message) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationId, userId, type, message, date);
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
