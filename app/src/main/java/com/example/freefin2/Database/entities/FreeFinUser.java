package com.example.freefin2.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.freefin2.Database.FreeFinDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

@Entity(tableName = FreeFinDatabase.FreeFinTable)
public  class FreeFinUser {
 //implements FreeFinDao {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;
    private boolean isAdmin;
    private LocalDateTime date;

    private void insert() {

    }

    public FreeFinUser(String username, String password) {
        this.username = username;
        this.password = password;
        isAdmin =false;
        date= LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FreeFinUser)) return false;
        FreeFinUser that = (FreeFinUser) o;
        return id == that.id && isAdmin == that.isAdmin && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, isAdmin, date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //@Override
    public ArrayList<FreeFinUser> getAllRecords() {
        return null;
    }


}
