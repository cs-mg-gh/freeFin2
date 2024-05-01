package com.example.freefin2.Database;

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
    List<FreeFinUser> getAllUsers();
    @Query("DELETE FROM " + FreeFinDatabase.FreeFinTable)
    void deleteAll();

}
