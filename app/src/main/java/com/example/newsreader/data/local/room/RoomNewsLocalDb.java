package com.example.newsreader.data.local.room;

import com.example.newsreader.data.local.interfaces.NewsLocalDb;
import com.example.newsreader.data.local.room.models.RoArticle;
import com.example.newsreader.data.local.room.models.RoNewsSource;
import com.example.newsreader.utils.models.Article;
import com.example.newsreader.utils.models.NewsSource;

import java.util.Date;
import java.util.List;

public class RoomNewsLocalDb implements NewsLocalDb {
    
    private RoNewsDao mDao;
    
    public RoomNewsLocalDb( RoNewsDao roNewsDao ) {
        mDao = roNewsDao;
    }
    
    /**
     * Get articles from database.
     *
     * @param newsSourceId NewsSourceNames ID.
     * @param date         Articles are returned if the are newer than passed Date.
     */
    @Override
    public List<Article> getArticles( String newsSourceId, Date date ) {
        return mDao.getArticles( newsSourceId, date );
    }
    
    /**
     * Get articles from database.
     *
     * @param newsSourceId Articles's parent object ID.
     */
    @Override
    public List<Article> getArticles( String newsSourceId ) {
        return mDao.getArticles( newsSourceId );
    }
    
    /**
     * Inserts articles in database ( or updates if it already exist ).
     *
     * @param newsSource NewsSourceNames data to save in database.
     */
    @Override
    public void insertNews( NewsSource newsSource ) {
        RoNewsSource roNewsSource = new RoNewsSource( newsSource );
        List<RoArticle> roArticleList = roNewsSource.toRoArticleList( newsSource );
        
        mDao.insertOrReplaceNewsDataWithArticles( roNewsSource, roArticleList );
    }
    
}
