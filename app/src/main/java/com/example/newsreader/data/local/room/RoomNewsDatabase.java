package com.example.newsreader.data.local.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.newsreader.data.local.room.converters.DateConverter;
import com.example.newsreader.data.local.room.models.RoArticle;
import com.example.newsreader.data.local.room.models.RoNewsSource;

@Database(
        entities = {
                RoNewsSource.class,
                RoArticle.class
        },
        version = 1 )
@TypeConverters( { DateConverter.class } )
public abstract class RoomNewsDatabase extends RoomDatabase {
    
    /**
     * Returns Dao for accessing Room database.
     *
     * @return Dao for accessing Room database.
     */
    public abstract RoNewsDao getNewsDao();
    
}
