package com.example.freefin2.Database;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import com.example.freefin2.Database.entities.Bills;
import com.example.freefin2.Database.typeConverters.LocalDateTypeConverter;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface BillsDAO {
    @Insert
    void insert(Bills bill);
    @Update
    void update(Bills bill);
    @Delete
    void delete(Bills bill);
    @Query("SELECT * FROM "+FreeFinDatabase.BillsTable +" WHERE billid = :billId")
    LiveData<Bills> getBillById(int billId);

    @Query("SELECT * FROM " +FreeFinDatabase.BillsTable +" WHERE userId = :userId")
    LiveData<List<Bills>> getBillsByUserId(int userId);
    @Query("SELECT * FROM " + FreeFinDatabase.BillsTable +" WHERE isActive = 1")
    LiveData<List<Bills>> getActiveBills();
    @Query("SELECT * FROM " + FreeFinDatabase.BillsTable + " WHERE dueDate = :dueDate")
    LiveData<List<Bills>> getBillsDueOn(String dueDate);
    @Query("SELECT * FROM " + FreeFinDatabase.BillsTable + " ORDER BY dueDate ASC")
    LiveData<List<Bills>> getAllBillsOrderedByDueDate();
}
