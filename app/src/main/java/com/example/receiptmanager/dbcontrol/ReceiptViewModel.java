package com.example.receiptmanager.dbcontrol;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.receiptmanager.model.Receipt;

import java.util.List;

public class ReceiptViewModel extends AndroidViewModel {

    private ReceiptRepository repository;
    private final LiveData<List<Receipt>> receiptList;

    public ReceiptViewModel(@NonNull Application application) {
        super(application);
        repository = new ReceiptRepository(application);
        receiptList = repository.getAllReceiptsByDate();
    }

    public LiveData<List<Receipt>> getAllReceipts() {
        return receiptList;
    }

    public void insert(Receipt receipt) { repository.insert(receipt); }

    public boolean doesExist(String receiptUrl) { return repository.doesExist(receiptUrl); }
}
