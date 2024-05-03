package com.example.freefin2.Database;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.freefin2.Database.entities.Notifications;

import java.util.List;

@Dao
public interface NotificationsDAO {
    @Insert
    void insertNotification(Notifications notification);

    @Query("SELECT * FROM notifications WHERE userId = :userId")
    List<Notifications> getNotificationsForUser(int userId);
}