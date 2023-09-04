package com.example.receiptmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.receiptmanager.R;

public class ReceiptDataActivity extends AppCompatActivity {

    private Button mCancelButton;
    private TextView mReceiptData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_data);

        mReceiptData = findViewById(R.id.receiptData);
        mReceiptData.setText(getIntent().getStringExtra("receiptData"));

        mCancelButton = findViewById(R.id.cancelButton);
        mCancelButton.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}