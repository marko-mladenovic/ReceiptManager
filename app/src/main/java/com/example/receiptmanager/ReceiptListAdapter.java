package com.example.receiptmanager;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.receiptmanager.model.Receipt;

public class ReceiptListAdapter extends ListAdapter<Receipt, ReceiptViewHolder> {

    public ReceiptListAdapter(@NonNull DiffUtil.ItemCallback<Receipt> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ReceiptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ReceiptViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(ReceiptViewHolder holder, int position) {
        Receipt current = getItem(position);
        holder.bind(current.getStore(), current.getDateInString(), String.valueOf(current.getCost()));
    }

    static class ReceiptDiff extends DiffUtil.ItemCallback<Receipt> {

        @Override
        public boolean areItemsTheSame(@NonNull Receipt oldItem, @NonNull Receipt newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Receipt oldItem, @NonNull Receipt newItem) {
            return oldItem.toString().equals(newItem.toString());
        }
    }

}
