package com.example.receiptmanager.dbcontrol;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static long stringToLong(String value) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss").parse(value).getTime();
    }

    @TypeConverter
    public static String longToString(long date) {
        Date dDate = new Date();
        dDate.setTime(date);
        return new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss").format(dDate);
    }
}
