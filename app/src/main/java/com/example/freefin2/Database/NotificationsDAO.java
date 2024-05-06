package com.example.freefin2.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.freefin2.Database.entities.Notifications;

import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface NotificationsDAO {
    @Insert
    long insertGoal(Notifications goal);

    @Update
    void updateGoal(Notifications goal);

    @Delete
    void deleteGoal(Notifications goal);

    @Query("SELECT * FROM "+  FreeFinDatabase.NotificationsTable + " WHERE id = :goalId")
    LiveData<Notifications> getGoalById(int goalId);

    @Query("SELECT * FROM " + FreeFinDatabase.NotificationsTable)
    LiveData<List<Notifications>> getAllGoals();

    @Query("SELECT * FROM "+  FreeFinDatabase.NotificationsTable + " WHERE date <= :date")
    LiveData<List<Notifications>> getGoalsByDate(LocalDateTime date);
    @Insert
    void insert(Notifications notification);
}
