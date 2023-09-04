package com.example.receiptmanager.recyclerviewcontrol;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.receiptmanager.model.Receipt;
import com.example.receiptmanager.recyclerviewcontrol.ReceiptViewHolder;

public class ReceiptListAdapter extends ListAdapter<Receipt, ReceiptViewHolder> {

    private ClickListener cListener;

    public ReceiptListAdapter(@NonNull DiffUtil.ItemCallback<Receipt> diffCallback, ClickListener clickListener) {
        super(diffCallback);
        this.cListener = clickListener;
    }

    @Override
    public ReceiptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ReceiptViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(ReceiptViewHolder holder, int position) {
        Receipt current = getItem(position);
        holder.bind(current.getStore(), current.getDateInString(), String.valueOf(current.getCost()));
        holder.itemView.setOnClickListener(view -> {
            cListener.clickedItem(current);
        });
    }

    public interface ClickListener {
        void clickedItem(Receipt r);
    }

    static public class ReceiptDiff extends DiffUtil.ItemCallback<Receipt> {

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
