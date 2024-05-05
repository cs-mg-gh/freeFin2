package com.example.freefin2.viewHolder;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.freefin2.Database.FreeFinDatabase;
import com.example.freefin2.Database.entities.Bills;
import com.example.freefin2.Database.entities.FreeFinUser;

public class FreeFinnViewModel extends AndroidViewModel {
    private LiveData<FreeFinUser> user;
    private FreeFinDatabase db;

    public FreeFinnViewModel(Application application) {
        super(application);
        this.db = FreeFinDatabase.getInstance(application); // Get database instance from application context
       // user = db.freefinDAO().getUserByUsername(username);
    }

    public void insertUser(FreeFinUser user) {
        new Thread(() -> db.freefinDAO().insertUser(user)).start();
    }

    public void createAccount(String username, String password) {
        // Assuming FreeFinUser has a constructor that takes username and password
        FreeFinUser newUser;
            newUser = new FreeFinUser(username, password);
            // Insert the new user into the database
        insertUser(newUser);
    }

    public void insertBill(Bills bill) {
    }
}
