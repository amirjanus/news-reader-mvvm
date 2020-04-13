package com.example.newsreader.data.local.room.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.newsreader.utils.models.NewsSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class RoNewsSource {
    
    @PrimaryKey
    @NonNull
    public String id;
    
    /* Timestamp showing when was http response received. */
    public Date date;
    
    public RoNewsSource() {
        id = "";
    }
    
    @Ignore
    public RoNewsSource( NewsSource newsSource ) {
        id = newsSource.id;
        date = newsSource.date;
    }
    
    /**
     * Returns RoArticle list from NewsSourceNames.
     *
     * @param newsSource NewsSourceNames from which to create RoArticle list.
     * @return RoArticle list.
     */
    public List<RoArticle> toRoArticleList( NewsSource newsSource ) {
        List<RoArticle> roArticleList = new ArrayList<>( newsSource.articles.size() );
        
        for ( int i = 0; i < newsSource.articles.size(); ++i )
            roArticleList.add( new RoArticle( newsSource, i ) );
        
        return roArticleList;
    }
    
}
