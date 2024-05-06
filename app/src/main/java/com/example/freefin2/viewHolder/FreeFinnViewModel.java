package com.example.freefin2.viewHolder;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.freefin2.Database.FreeFinDatabase;
import com.example.freefin2.Database.FreeFinLogRepo;
import com.example.freefin2.Database.entities.Bills;
import com.example.freefin2.Database.entities.FreeFinUser;
import com.example.freefin2.Database.entities.Notifications;

import java.util.List;

public class FreeFinnViewModel extends AndroidViewModel {
    private LiveData<FreeFinUser> user;
    private LiveData<List<Bills>> allBills;
    private FreeFinDatabase db;
    private FreeFinLogRepo repository;


    public FreeFinnViewModel(Application application) {
        super(application);
        this.db = FreeFinDatabase.getInstance(application); // Ensure this method correctly sets up the database
        this.repository = FreeFinLogRepo.getRepository(application); // Correct initialization
        allBills = repository.getAllBills(); // Now safe to call
    }
    public void insertUser(FreeFinUser user) {
        new Thread(() -> {
            try {
                repository.insertUser(user);
            } catch (Exception e) {
                Log.e("ViewModel", "Error inserting user", e);
            }
        }).start();
    }

    public void createAccount(String username, String password) {
        FreeFinUser newUser = new FreeFinUser(username, password);
        insertUser(newUser);
    }

    public void insertBill(Bills bill) {
        repository.insert(bill);
    }

    public void insertNotification(Notifications notification) {
        repository.insert(notification);
    }
    public LiveData<FreeFinUser> getUserByUsername(String username) {
        return db.freefinDAO().getUserByUsername(username);
    }

    public LiveData<List<Bills>> getAllBills() {
        return allBills;
    }


    public void insert(Notifications notification) {
        repository.insert(notification);
    }
}
