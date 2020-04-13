package com.example.newsreader.data.local.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.newsreader.data.local.room.models.RoArticle;
import com.example.newsreader.data.local.room.models.RoNewsSource;
import com.example.newsreader.utils.models.Article;

import java.util.Date;
import java.util.List;

@Dao
public abstract class RoNewsDao {
    
    /**
     * Inserts news data together with list of articles in database.
     *
     * @param roNewsSource    News data to insert.
     * @param articleList List of articles to insert.
     */
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    public abstract void insertNewsDataWithArticles( RoNewsSource roNewsSource, List<RoArticle> articleList );
    
    /**
     * Inserts news data together with list of articles in database while delete old articles.
     *
     * @param roNewsSource    News data to insert.
     * @param articleList List of articles to insert.
     */
    @Transaction
    public void insertOrReplaceNewsDataWithArticles( RoNewsSource roNewsSource, List<RoArticle> articleList ) {
        // Delete old articles.
        deleteArticles( roNewsSource.id );
        
        // Insert ( or replace ) news data and insert new articles.
        insertNewsDataWithArticles( roNewsSource, articleList );
    }
    
    /**
     * Returns list of articles from database.
     *
     * @param newsSourceId Articles's parent object ID.
     * @return List of articles or empty list if none exist that satisfy search query.
     */
    @Query( "SELECT * FROM roarticle " +
            "WHERE newsSourceId = :newsSourceId" )
    public abstract List<Article> getArticles( String newsSourceId );
    
    /**
     * Returns list of articles from database.
     *
     * @param newsSourceId Articles's parent object ID.
     * @param date       Searches for articles newer than this Date.
     * @return List of articles or empty list if none exist that satisfy search query.
     */
    @Query( "SELECT * FROM roarticle " +
            "INNER JOIN RoNewsSource ON RoNewsSource.id = roarticle.newsSourceId " +
            "WHERE RoNewsSource.id = :newsSourceId AND RoNewsSource.date >= :date " +
            "ORDER BY roarticle.sortOrder ASC" )
    public abstract List<Article> getArticles( String newsSourceId, Date date );
    
    /**
     * Delete articles from database.
     *
     * @param newsSourceId Articles's parent object ID.
     * @return Number of rows removed from the database
     */
    @Query( "DELETE FROM roarticle " +
            "WHERE newsSourceId = :newsSourceId" )
    public abstract int deleteArticles( String newsSourceId );
    
}
