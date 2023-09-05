package com.example.receiptmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.receiptmanager.R;

public class ReceiptDataActivity extends AppCompatActivity {

    private Button mBackButton;
    private Button mLocationButton;
    private Button mDeleteButton;

    private TextView mReceiptData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_data);

        mReceiptData = findViewById(R.id.receiptData);
        mReceiptData.setText(getIntent().getStringExtra("receiptData"));

        mBackButton = findViewById(R.id.backButton);
        mBackButton.setOnClickListener(view -> {
            onBackPressed();
        });

        mLocationButton = findViewById(R.id.locationButton);
        mLocationButton.setOnClickListener(view -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + getIntent().getStringExtra("storeLocation"));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        mDeleteButton = findViewById(R.id.deleteButton);
        mDeleteButton.setOnClickListener(view -> {
            setResult(RESULT_OK);
            finish();
        });
    }
}