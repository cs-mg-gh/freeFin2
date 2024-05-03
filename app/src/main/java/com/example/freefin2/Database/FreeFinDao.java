package com.example.freefin2.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.freefin2.Database.entities.FreeFinUser;
import java.util.List;
@Dao
public interface FreeFinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FreeFinUser... user);
    @Delete
    void delete(FreeFinUser user);

    @Query("Select * from " + FreeFinDatabase.FreeFinTable + " ORDER BY username ")
    LiveData<List<FreeFinUser>> getAllUsers();
    @Query("DELETE FROM " + FreeFinDatabase.FreeFinTable)
    void deleteAll();
    @Query("SELECT * FROM " + FreeFinDatabase.FreeFinTable+" WHERE username == :username ORDER BY date DESC")
    LiveData<FreeFinUser> getUserByUsername(String username);
    @Query("SELECT * FROM " + FreeFinDatabase.FreeFinTable+" WHERE id == :userId")
    LiveData<FreeFinUser> getUserById(int userId);
    @Query("SELECT * FROM " + FreeFinDatabase.FreeFinTable+" WHERE id == :loggedInUserId")
    LiveData<List<FreeFinUser>> getRecordsetUserId(int loggedInUserId);
}
