package com.example.newsreader.data.local.room.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.newsreader.utils.models.Article;
import com.example.newsreader.utils.models.NewsSource;

@Entity
public class RoArticle {
    
    @PrimaryKey( autoGenerate = true )
    public long id;
    
    /* Parent's primary key. */
    public String newsSourceId;
    
    /* Article's title. */
    public String title;
    
    /* Article's text. */
    public String description;
    
    /* Url to article's image. */
    public String urlToImage;
    
    /* Order in which articles were received from NewsApi. */
    public int sortOrder;
    
    public RoArticle() {}
    
    @Ignore
    public RoArticle( NewsSource newsSource, int index ) {
        newsSourceId = newsSource.id;
        
        Article article = newsSource.articles.get( index );
        
        title = article.title;
        description = article.description;
        urlToImage = article.urlToImage;
        sortOrder = index;
    }
    
}
