package com.example.newsreader.data.remote.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsreader.R;
import com.example.newsreader.data.remote.newsapi.NewsApi;
import com.example.newsreader.utils.models.NewsSource;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.TimeZone;

import javax.inject.Inject;

import io.reactivex.Single;

public class VolleyNewsApi implements NewsApi {
    
    private final String TAG_GET_NEWS = "GET_NEWS";
    
    private RequestQueue mRequestQueue;
    
    private String mApiKey;
    private String mBaseUrl = "https://newsapi.org/";
    private String mPath = "v1/articles";
    
    @Inject
    public VolleyNewsApi( Context context ) {
        mRequestQueue = Volley.newRequestQueue( context );
        
        mApiKey = context.getResources().getString( R.string.news_api_key );
    }
    
    /**
     * Get news articles from NewsApi.
     *
     * @param source Article source.
     * @param sortBy How to sort articles.
     * @return NewsSourceNames object with timestamp and articles list.
     */
    @Override
    public Single<NewsSource> getNews( String source, String sortBy ) {
        return Single.create( emitter -> {
            String params = String.format( "?source=%1$s&sortBy=%2$s&apiKey=%3$s", source, sortBy, mApiKey );
            
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    mBaseUrl + mPath + params,
                    response -> emitter.onSuccess( responseToNewsSource( response, source ) ),
                    error -> emitter.onError( error )
            );
            
            request.setTag( TAG_GET_NEWS );
            
            emitter.setCancellable( () -> mRequestQueue.cancelAll( TAG_GET_NEWS ) );
            
            mRequestQueue.add( request );
        } );
    }
    
    /**
     * Create NewsSourceNames object from news api response.
     *
     * @param response       NewsApi response.
     * @param newsSourceName Article source.
     * @return Created NewsSource object.
     */
    private NewsSource responseToNewsSource( String response, String newsSourceName ) {
        Gson gson = new Gson();
        
        NewsSource newsSource = gson.fromJson( response, NewsSource.class );
        newsSource.date = Calendar.getInstance( TimeZone.getTimeZone( "UTC" ) ).getTime();
        newsSource.id = newsSourceName;
        
        return newsSource;
    }
    
}
