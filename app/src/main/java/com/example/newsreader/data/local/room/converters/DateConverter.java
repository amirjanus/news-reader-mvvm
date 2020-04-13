package com.example.newsreader.data.local.room.converters;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Converter for Room database.
 */
public class DateConverter {
    
    /**
     * Converts time in milliseconds to Date object.
     *
     * @param millis Time in milliseconds since epoch.
     * @return Date object.
     */
    @TypeConverter
    public static Date fromMillis( Long millis ) {
        return millis != null ? new Date( millis ) : null;
    }
    
    /**
     * Converts Date object to time in milliseconds.
     *
     * @param date Date object.
     * @return Time in milliseconds since epoch.
     */
    @TypeConverter
    public static Long toMillis( Date date ) {
        return date != null ? date.getTime() : null;
    }
    
}
