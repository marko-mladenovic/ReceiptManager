package com.example.receiptmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    final int RECEIPT_REQUEST_CODE = 101;

    private FloatingActionButton mCameraButton;
    private TextView mReceiptURL;

    private String scannedUrl;
    private String scrapedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReceiptURL = findViewById(R.id.receipt_url);
        mCameraButton = findViewById(R.id.cameraButton);
        mCameraButton.setOnClickListener(view -> {
            startActivityForResult(new Intent(MainActivity.this, QRScannerActivity.class), RECEIPT_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECEIPT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                scannedUrl = data.getStringExtra("RESULT_STRING");
                (new Scraper()).execute();
            }
        }
    }

    public class Scraper extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect(scannedUrl).get();
                scrapedData = doc.text();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Pattern pattern = Pattern.compile("============ ФИСКАЛНИ РАЧУН ============" +
                                                    "([\\s\\S]+)" +
                                                    "======== КРАЈ ФИСКАЛНОГ РАЧУНА ========");

            Matcher matcher = pattern.matcher(scrapedData);
            mReceiptURL.setText(matcher.group());
        }
    }
}