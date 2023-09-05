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

    public int delete(Receipt r) { return receiptDao.deleteEntry(r); }

    public String getNotificationData() {
        List<Double> prices = receiptDao.prices();
        Double sum = prices.stream().reduce(0.0, (acc, curr) -> acc + curr);
        int size = prices.size();

        return size == 1 ? "There is currently one receipt with the cost of " + sum : "There are currently " + size + " receipts with a total cost of " + String.format("%.2f", sum) + " RSD.";
    }

}
