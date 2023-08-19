package com.example.receiptmanager.dbcontrol;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.receiptmanager.model.Receipt;

import java.util.List;

public class ReceiptRepository {

    private ReceiptDao receiptDao;
    private LiveData<List<Receipt>> receipts;

    public ReceiptRepository(Application application) {
        ReceiptDatabase db = ReceiptDatabase.getDatabase(application);
        receiptDao = db.receiptDao();
        receipts = receiptDao.getAllReceiptsByDate();
    }

    public void insert(Receipt receipt) {
        ReceiptDatabase.databaseWriteExecutor.execute(() -> {
            receiptDao.insert(receipt);
        });
    }

    public LiveData<List<Receipt>> getAllReceiptsByDate() { return receipts; }

    public boolean doesExist(String receiptUrl) {
        return receiptDao.findReceipt(receiptUrl) == 1;
    }

}
