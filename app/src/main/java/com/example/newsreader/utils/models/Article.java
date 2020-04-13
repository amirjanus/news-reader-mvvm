package com.example.newsreader.utils.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Ignore;

/**
 * Class holds NewsApi article data.
 */
public class Article implements Parcelable {
    
    /* Object ID. */
    public long id;
    
    /* Article's title. */
    public String title;
    
    /* Article's text. */
    public String description;
    
    /* Url to article's image. */
    public String urlToImage;
    
    public Article() {}
    
    protected Article( Parcel in ) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        urlToImage = in.readString();
    }
    
    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel( Parcel in ) {
            return new Article( in );
        }
        
        @Override
        public Article[] newArray( int size ) {
            return new Article[size];
        }
    };
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel( Parcel dest, int flags ) {
        dest.writeLong( id );
        dest.writeString( title );
        dest.writeString( description );
        dest.writeString( urlToImage );
    }
    
}
