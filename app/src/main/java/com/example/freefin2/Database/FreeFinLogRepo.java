package com.example.freefin2.Database;

import android.app.Application;

import com.example.freefin2.Database.entities.Bills;
import com.example.freefin2.Database.entities.FreeFinUser;

import java.util.List;

import androidx.lifecycle.LiveData;

public class FreeFinLogRepo {
    private final FreeFinDao freefinDAO;
    private final BillsDAO billsDAO;
    private static volatile FreeFinLogRepo repository; // Use volatile for thread-safe singleton
    private final LiveData<List<FreeFinUser>> allUsers;
    private final LiveData<List<Bills>> allBills;

    private FreeFinLogRepo(Application application){
        FreeFinDatabase db = FreeFinDatabase.getDatabase(application);
        freefinDAO = db.freefinDAO();
        billsDAO = db.billsDAO();
        allUsers = freefinDAO.getAllUsers(); // This should be LiveData
        allBills = billsDAO.getAllBillsOrderedByDueDate();
    }

    public static FreeFinLogRepo getRepository(Application application){
        if (repository == null) {
            synchronized (FreeFinLogRepo.class) {
                if (repository == null) {
                    repository = new FreeFinLogRepo(application);
                }
            }
        }
        return repository;
    }
    public LiveData<List<FreeFinUser>> getAllUsers() {
        return allUsers;
    }

    public void insertUser(FreeFinUser... users){
        FreeFinDatabase.databaseWriteExecutor.execute(() -> {
            freefinDAO.insertUser(users);
        });
    }

    public LiveData<FreeFinUser> getUserByUsername(String username) {
        return freefinDAO.getUserByUsername(username); // Assuming DAO method returns LiveData
    }
    public LiveData<FreeFinUser> getUserById(int userId) {
        return freefinDAO.getUserById(userId); // Assuming DAO method returns LiveData
    }
    public LiveData<List<Bills>> getAllBills() {
        return allBills;
    }

    public void insert(Bills bill) {
        FreeFinDatabase.databaseWriteExecutor.execute(() -> {
            billsDAO.insert(bill);
        });
    }

    public LiveData<List<FreeFinUser>> getAllUsersbyId(int loggedInUserId) {
        return freefinDAO.getRecordsetUserId(loggedInUserId);
    }
}
