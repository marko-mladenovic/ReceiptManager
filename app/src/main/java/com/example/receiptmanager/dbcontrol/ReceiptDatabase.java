package com.example.receiptmanager.dbcontrol;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.receiptmanager.model.Receipt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Receipt.class}, version = 2, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class ReceiptDatabase extends RoomDatabase {

    public abstract ReceiptDao receiptDao();

    public static ReceiptDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ReceiptDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (ReceiptDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ReceiptDatabase.class, "receipt-database")
                                   .fallbackToDestructiveMigration()
                                   .build();
                }
            }
        }

        return INSTANCE;
    }
}
