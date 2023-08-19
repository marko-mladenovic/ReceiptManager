package com.example.receiptmanager.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.receiptmanager.dbcontrol.DateConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "receipt")
public class Receipt {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "receipt_cost")
    private double cost;

    @ColumnInfo(name = "store_name")
    private String store;

    @ColumnInfo(name = "store_location")
    private String location;

    @ColumnInfo(name = "receipt_date")
    @TypeConverters({DateConverter.class})
    private long date;

    @ColumnInfo(name = "receipt_data")
    private String data;

    public Receipt(String url, String storeName, String storeLocation, double receiptCost, long receiptDate, String receiptData) {
        this.url = url;
        this.store = storeName;
        this.location = storeLocation;
        this.cost = receiptCost;
        this.date = receiptDate;
        this.data = receiptData;
    }

    public Receipt(String url) {
        this.url = url;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDateInString() {
        return (new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss").format(date));
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "url='" + url + '\'' +
                ", cost=" + cost +
                ", store='" + store + '\'' +
                ", location='" + location + '\'' +
                ", date=" + date +
                ", data='" + data + '\'' +
                '}';
    }
}
