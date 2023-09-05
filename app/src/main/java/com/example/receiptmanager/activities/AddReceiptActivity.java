package com.example.receiptmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.receiptmanager.R;

public class AddReceiptActivity extends AppCompatActivity {

    private Button mAddButton;
    private Button mCancelButton;

    private String receiptData;
    private TextView mReceiptData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receipt);

        receiptData = getIntent().getStringExtra("receiptData");

        mReceiptData = findViewById(R.id.receiptData);
        mReceiptData.setText(receiptData);

        mAddButton = findViewById(R.id.addButton);
        mAddButton.setOnClickListener(view -> {
            setResult(RESULT_OK);
            finish();
        });

        mCancelButton = findViewById(R.id.backButton);
        mCancelButton.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}