package com.example.receiptmanager.dbcontrol;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.receiptmanager.model.Receipt;

import java.util.List;

@Dao
public interface ReceiptDao {
    @Insert
    void insertAll(Receipt... receipts);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Receipt receipt);

    @Query("SELECT * FROM receipt")
    LiveData<List<Receipt>> getAllReceipts();

    @Query("SELECT * FROM receipt ORDER BY receipt_date ASC")
    LiveData<List<Receipt>> getAllReceiptsByDate();

    @Query("SELECT COUNT(*) FROM receipt WHERE url IS :url")
    int findReceipt(String url); //1 if found, 0 if not found
}
