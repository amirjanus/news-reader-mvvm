package com.example.newsreader.view.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsreader.data.MainMvvmModel;
import com.example.newsreader.data.constants.NewsSourceNames;
import com.example.newsreader.utils.models.Article;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainViewModel extends ViewModel {
    
    private static final String TAG = MainViewModel.class.getSimpleName();
    
    private MainMvvmModel mModel;
    
    private CompositeDisposable mDisposable;
    
    private MutableLiveData<List<Article>> mArticleListLiveData = new MutableLiveData<>();
    private MutableLiveData<ViewState> mViewStateLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mSelectedArticleLiveData = new MutableLiveData<>();
    
    @Inject
    public MainViewModel( MainMvvmModel mainMvvmModel ) {
        mModel = mainMvvmModel;
    }
    
    /**
     * Called when user clicks on item in Article RecyclerView.
     *
     * @param articleIndex Position index of selected article item.
     */
    public void articleSelected( int articleIndex ) {
        mSelectedArticleLiveData.setValue( articleIndex );
    }
    
    /**
     * Get article list live data.
     *
     * @return Article list live data.
     */
    public LiveData<List<Article>> getArticleListLiveData() {
        return mArticleListLiveData;
    }
    
    /**
     * Get view state live data.
     *
     * @return View state live data.
     */
    public LiveData<ViewState> getViewStateLiveData() {
        return mViewStateLiveData;
    }
    
    /**
     * Get selected article live data.
     *
     * @return Selected article live data.
     */
    public LiveData<Integer> getSelectedArticleLiveData() {
        return mSelectedArticleLiveData;
    }
    
    /**
     * Get articles from database or api.
     */
    public void fetchArticles() {
        dispose();
        
        mDisposable = new CompositeDisposable();
    
        // Show loader and clean the view.
        mViewStateLiveData.setValue( ViewState.SHOW_LOADER );
        mArticleListLiveData.setValue( new ArrayList<>( 0 ) );
        
        // Try to get news data from ( local ) database.
        Disposable disposable = mModel.getLocalArticles( NewsSourceNames.BBC.getString(), getDate() )
                .subscribe(
                        articleList -> onGetLocalNewsSuccess( articleList ),
                        throwable -> getNewsFromApi() );
        
        mDisposable.add( disposable );
    }
    
    /**
     * Dispose of disposables.
     */
    private void dispose() {
        if ( mDisposable != null && !mDisposable.isDisposed() )
            mDisposable.dispose();
    }
    
    /**
     * Subtract 5 minutes from current Date.
     *
     * @return Date.
     */
    private Date getDate() {
        Calendar calendar = Calendar.getInstance( TimeZone.getTimeZone( "UTC" ) );
        calendar.add( Calendar.MINUTE, -5 );
        
        return calendar.getTime();
    }
    
    /**
     * Called when Articles are successfully returned from local database.
     */
    private void onGetLocalNewsSuccess( List<Article> articleList ) {
        if ( articleList.size() > 0 ) {
            // Articles were found in local database. Show them in view.
            mViewStateLiveData.setValue( ViewState.HIDE_LOADER );
            mArticleListLiveData.setValue( articleList );
        } else
            // No articles in local database. Try to get them from api.
            getNewsFromApi();
    }
    
    /**
     * Try to get news from NewsApi.
     */
    private void getNewsFromApi() {
        Disposable disposable = mModel.getRemoteArticles( NewsSourceNames.BBC.getString(), "top" )
                .subscribe(
                        articleList -> {
                            mViewStateLiveData.setValue( ViewState.HIDE_LOADER );
                            mArticleListLiveData.setValue( articleList );
                        },
                        throwable -> {
                            mViewStateLiveData.setValue( ViewState.HIDE_LOADER );
                            mViewStateLiveData.setValue( ViewState.SHOW_ERROR );
                        }
                );
        
        mDisposable.add( disposable );
    }
    
}
