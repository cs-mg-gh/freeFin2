package com.example.freefin2.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.freefin2.Database.entities.FreeFinUser;

import java.util.List;

@Dao
public interface FreeFinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FreeFinDao log);
    @Query("Select * from " + FreeFinDatabase.FreeFinTable)
    List<FreeFinUser> getAllRecords();
}
