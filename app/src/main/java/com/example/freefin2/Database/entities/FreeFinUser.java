package com.example.freefin2.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.*;
import com.example.freefin2.Database.FreeFinDao;
import com.example.freefin2.Database.FreeFinDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

@Entity(tableName = FreeFinDatabase.FreeFinTable)
public  class FreeFinUser implements FreeFinDao{

 //implements FreeFinDao {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;
    private boolean admin;
    private LocalDateTime date;

    private void insert() {

    }

    public FreeFinUser(String username, String password, boolean admin, int id) {
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.id = id;
        date= LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FreeFinUser)) return false;
        FreeFinUser that = (FreeFinUser) o;
        return id == that.id && admin == that.admin && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, admin, date);
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
    public void insert(FreeFinUser log) {

    }

    //@Override
    public ArrayList<FreeFinUser> getAllRecords() {
        return null;
    }


}
