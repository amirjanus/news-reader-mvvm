package com.example.newsreader.di;

import android.content.Context;

import androidx.room.Room;

import com.example.newsreader.NewsReaderApplication;
import com.example.newsreader.data.MainMvvmModel;
import com.example.newsreader.data.MainModel;
import com.example.newsreader.data.local.interfaces.NewsLocalDb;
import com.example.newsreader.data.local.room.RoNewsDao;
import com.example.newsreader.data.local.room.RoomNewsDatabase;
import com.example.newsreader.data.local.room.RoomNewsLocalDb;
import com.example.newsreader.data.remote.newsapi.NewsApi;
import com.example.newsreader.data.remote.volley.VolleyNewsApi;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {
    
    /**
     * Provide android context.
     *
     * @param application Android application.
     * @return Android context.
     */
    @Singleton
    @Binds
    abstract Context bindContext( NewsReaderApplication application );
    
    /**
     * Provide NewsApi interface for fetching news from remote api.
     *
     * @param context Android context.
     * @return NewsApi interface.
     */
    @Singleton
    @Provides
    static NewsApi provideNewsApi( Context context ) {
        return new VolleyNewsApi( context );
    }
    
    /**
     * Provide NewsLocalDb interface from accessing local database.
     *
     * @param context Android context.
     * @return NewsLocalDb interface.
     */
    @Singleton
    @Provides
    static NewsLocalDb provideLocalDb( Context context ) {
        RoNewsDao newsDao = Room.databaseBuilder( context.getApplicationContext(), RoomNewsDatabase.class, "newsDatabase" )
                .build()
                .getNewsDao();
        
        return new RoomNewsLocalDb( newsDao );
    }
    
    /**
     * Provide MainMvvmModel interface for accessing local and remote data sources ( repository ).
     *
     * @param newsLocalDb NewsLocalDb interface.
     * @param newsApi     NewsApi interface.
     * @return MainMvvmModel interface.
     */
    @Singleton
    @Provides
    static MainMvvmModel provideDatabaseModel( NewsLocalDb newsLocalDb, NewsApi newsApi ) {
        return new MainModel( newsLocalDb, newsApi );
    }
    
}
