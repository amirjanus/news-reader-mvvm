package com.example.newsreader.view.article;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsreader.data.MainMvvmModel;
import com.example.newsreader.data.constants.NewsSourceNames;
import com.example.newsreader.utils.containers.Pair;
import com.example.newsreader.utils.models.Article;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class ArticleViewModel extends ViewModel {
    
    private MainMvvmModel mModel;
    
    private Disposable mDisposable;
    
    private MutableLiveData<Pair<List<Article>, Integer>> mArticleListLiveData = new MutableLiveData<>();
    private MutableLiveData<String> mArticleTitleLiveData = new MutableLiveData<>();
    private MutableLiveData<ViewState> mViewStateLiveData = new MutableLiveData<>();
    
    private Integer mInitialSlide = 0;
    
    @Inject
    public ArticleViewModel( MainMvvmModel mainMvpModel ) {
        mModel = mainMvpModel;
        
        fetchArticles();
    }
    
    /**
     * Called when new page is set in ViewPager.
     *
     * @param index Position index of the new ViewPager page.
     */
    public void articlePageChanged( int index ) {
        if ( mArticleListLiveData.getValue() != null ) {
            mArticleTitleLiveData.setValue( mArticleListLiveData.getValue().first.get( index ).title );
            
            // Save the value of current ViewPager slide.
            mArticleListLiveData.getValue().second = index;
        }
    }
    
    /**
     * Set which ViewPager slide show to when Articles are shown for the first time in ViewPager.
     *
     * @param initialSlide Position index of ViewPager slide.
     */
    public void setInitialSlide( int initialSlide ) {
        mInitialSlide = initialSlide;
    }
    
    /**
     * Get article list live data.
     *
     * @return Article list live data.
     */
    public LiveData<Pair<List<Article>, Integer>> getArticleListLiveData() {
        return mArticleListLiveData;
    }
    
    /**
     * Get article title live data.
     *
     * @return Article title live data.
     */
    public LiveData<String> getArticleTitleLiveData() {
        return mArticleTitleLiveData;
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
     * Get articles from database.
     */
    private void fetchArticles() {
        mDisposable = mModel.getLocalArticles( NewsSourceNames.BBC.getString() )
                .subscribe(
                        articleList -> mArticleListLiveData.setValue( new Pair<>( articleList, mInitialSlide ) ),
                        throwable -> mViewStateLiveData.setValue( ViewState.SHOW_ERROR ) );
    }
    
}
