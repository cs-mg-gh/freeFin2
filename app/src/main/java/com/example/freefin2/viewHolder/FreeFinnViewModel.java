package com.example.freefin2.viewHolder;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.freefin2.Database.FreeFinDatabase;
import com.example.freefin2.Database.entities.FreeFinUser;

public class FreeFinnViewModel extends AndroidViewModel {
    private LiveData<FreeFinUser> user;
    private FreeFinDatabase db;

    public FreeFinnViewModel(FreeFinDatabase db, String username) {
        super();
        this.db = db;
        user = db.freefinDAO().getUserByUsername(username);
    }

    public LiveData<FreeFinUser> getUser() {
        return user;
    }

    public void insertUser(FreeFinUser user) {
        new Thread(() -> db.freefinDAO().insertUser(user)).start();
    }
}
