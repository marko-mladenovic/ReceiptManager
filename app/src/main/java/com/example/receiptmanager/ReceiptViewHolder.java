package com.example.receiptmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReceiptViewHolder extends RecyclerView.ViewHolder {
    private final TextView receiptTitleTextView;
    private final TextView receiptDateTextView;
    private final TextView receiptPriceTextView;

    public ReceiptViewHolder(@NonNull View itemView) {
        super(itemView);
        receiptTitleTextView = itemView.findViewById(R.id.receiptTitleTextView);
        receiptDateTextView = itemView.findViewById(R.id.receiptDateTextView);
        receiptPriceTextView = itemView.findViewById(R.id.receiptPriceTextView);
    }

    public void bind(String title, String date, String price) {
        receiptTitleTextView.setText(title);
        receiptDateTextView.setText(date);
        receiptPriceTextView.setText(price + " RSD");
    }

    static ReceiptViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ReceiptViewHolder(view);
    }
}
