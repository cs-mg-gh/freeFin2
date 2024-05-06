package com.example.freefin2.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.freefin2.Database.entities.Goals;

import java.util.List;

@Dao
public interface GoalsDAO {
    @Insert
    void insert(Goals goal);

    @Update
    void update(Goals goal);

    @Delete
    void delete(Goals goal);

    @Query("SELECT * FROM " + FreeFinDatabase.GoalsTable+ " WHERE id = :id")
    LiveData<Goals> getGoalById(int id);

    @Query("SELECT * FROM " + FreeFinDatabase.GoalsTable)
    LiveData<List<Goals>> getAllGoals();
}
