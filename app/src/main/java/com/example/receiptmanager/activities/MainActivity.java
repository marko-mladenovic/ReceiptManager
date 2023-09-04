package com.example.receiptmanager.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.receiptmanager.R;
import com.example.receiptmanager.recyclerviewcontrol.ReceiptListAdapter;
import com.example.receiptmanager.dbcontrol.ReceiptViewModel;
import com.example.receiptmanager.model.Receipt;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements ReceiptListAdapter.ClickListener {

    final int RECEIPT_REQUEST_CODE = 101;
    final int NEW_RECEIPT_REQUEST_CODE = 201;

    private String scannedUrl;
    private String scrapedData;

    private ReceiptViewModel mReceiptViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton mCameraButton = findViewById(R.id.cameraButton);
        mCameraButton.setOnClickListener(view -> startActivityForResult(new Intent(MainActivity.this, QRScannerActivity.class), RECEIPT_REQUEST_CODE));

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ReceiptListAdapter adapter = new ReceiptListAdapter(new ReceiptListAdapter.ReceiptDiff(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mReceiptViewModel = new ViewModelProvider(this).get(ReceiptViewModel.class);
        mReceiptViewModel.getAllReceipts().observe(this, adapter::submitList);
    }

    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECEIPT_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            scannedUrl = data.getStringExtra("RESULT_STRING");
            executor.execute(scrapeData);
        }
        if (requestCode == NEW_RECEIPT_REQUEST_CODE && resultCode == RESULT_OK) {
            executor.execute(insertReceipt);
        }
    }

    Runnable insertReceipt = () -> {
        if(mReceiptViewModel.doesExist(scannedUrl)) {
            this.runOnUiThread(() -> {
                Toast.makeText(this, "Receipt is already scanned and saved.", Toast.LENGTH_LONG).show();
            });
        } else {
            Receipt newReceipt = new Receipt(scannedUrl);

            //Setting the whole formatted Receipt
            newReceipt.setData(scrapedData);

            //Getting the date out and setting it
            String dateString = scrapedData.substring(scrapedData.indexOf("ПФР време:") + 10, scrapedData.lastIndexOf("ПФР број рачуна:") - 1).trim();
            try {
                long date = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss").parse(dateString).getTime();
                newReceipt.setDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //Getting the price out and setting it
            String priceOnwards = scrapedData.substring(scrapedData.indexOf("Укупан износ:") + 18);
            String price = priceOnwards.substring(0, priceOnwards.indexOf("\n")).trim();
            newReceipt.setCost(Double.parseDouble((price.replace(".", "")).replace(",", ".")));

            //Getting the store name out and setting it
            String storeName = scrapedData;
            for(int i = 0; i < 2; i++)
                storeName = storeName.substring(storeName.indexOf("\n") + 1);
            storeName = storeName.substring(0, storeName.indexOf("\n")).trim();
            newReceipt.setStore(storeName);

            //Getting the address of the store out and setting it
            String address = scrapedData;
            for(int i = 0; i < 3; i++)
                address = address.substring(address.indexOf("\n") + 1);
            String city = address.substring(address.indexOf("\n") + 1);
            city = city.substring(0, city.indexOf("\n")).trim();
            address = address.substring(0, address.indexOf("\n")).trim();
            newReceipt.setLocation(address + ", " + city);

            mReceiptViewModel.insert(newReceipt);
            this.runOnUiThread(() -> {
                Toast.makeText(this, "Receipt saved.", Toast.LENGTH_LONG).show();
            });
        }
    };

    Runnable scrapeData = () -> {
        try {
            Document doc = Jsoup.connect(scannedUrl).get();
            scrapedData = doc.text();
        } catch (IOException e) {
            e.printStackTrace();
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                scrapedData = scrapedData.substring(scrapedData.indexOf("============"), scrapedData.lastIndexOf("=========") + 9);

                Intent intent = new Intent(MainActivity.this, AddReceiptActivity.class);
                intent.putExtra("receiptData", scrapedData);
                startActivityForResult(intent, NEW_RECEIPT_REQUEST_CODE);
            }
        });
    };

    @Override
    public void clickedItem(Receipt r) {
        Intent intent = new Intent(MainActivity.this, ReceiptDataActivity.class);
        intent.putExtra("receiptData", r.getData());
        intent.putExtra("storeLocation", r.getLocation());
        startActivity(intent);
    }
}