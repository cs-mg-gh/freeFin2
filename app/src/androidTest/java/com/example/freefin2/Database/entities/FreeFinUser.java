package com.example.freefin2.Database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.freefin2.Database.FreeFinDao;
import com.example.freefin2.Database.FreeFinDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

@Entity(tableName = FreeFinDatabase.FreeFinTable)
public class FreeFinUser implements FreeFinDao {
    @PrimaryKey(autoGenerate = true)

    private String username;
    private String password;
    private boolean admin;

    public FreeFinUser(boolean admin) {
        this.admin = admin;
        LocalDate date = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FreeFinUser)) return false;
        FreeFinUser that = (FreeFinUser) o;
        return admin == that.admin && Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, admin);
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
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

    @Override
    public void insert(FreeFinDao log) {

    }

    @Override
    public ArrayList<FreeFinUser> getAllRecords() {
        return null;
    }
}
