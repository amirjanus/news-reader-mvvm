package com.example.newsreader.view.article;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.newsreader.databinding.ActivityArticleBinding;
import com.example.newsreader.di.ViewModelProviderFactory;
import com.example.newsreader.utils.containers.Pair;
import com.example.newsreader.utils.dialogs.ErrorDialog;
import com.example.newsreader.utils.models.Article;
import com.example.newsreader.view.article.adapter.PagerArticleAdapter;
import com.example.newsreader.view.main.MainActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class ArticleActivity extends DaggerAppCompatActivity {
    
    @Inject
    PagerArticleAdapter mPagerArticleAdapter;
    
    @Inject
    ViewModelProviderFactory mViewModelProviderFactory;
    
    private ArticleViewModel mViewModel;
    
    private ViewPager2.OnPageChangeCallback mOnPageChangeCallback;
    
    private ActivityArticleBinding mBinding;
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        
        // Set view binding.
        mBinding = ActivityArticleBinding.inflate( getLayoutInflater() );
        setContentView( mBinding.getRoot() );
        
        // Make view's content to appear behind the navigation bar.
        mBinding.getRoot().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION );
        
        initPagerArticles();
        
        initViewModel();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        mBinding.pagerArticles.unregisterOnPageChangeCallback( mOnPageChangeCallback );
        mBinding = null;
    }
    
    /**
     * Display Articles list in view.
     *
     * @param listIntegerPair Pair of Article List and position index of Article to show as current item.
     */
    private void showArticles( Pair<List<Article>, Integer> listIntegerPair ) {
        mPagerArticleAdapter.setDataset( listIntegerPair.first );
        
        mBinding.pagerArticles.setCurrentItem( listIntegerPair.second, false );
    }
    
    /**
     * Called when views gets notified that the view state has changed.
     *
     * @param viewState New view state.
     */
    private void onViewStateChange( ViewState viewState ) {
        switch ( viewState ) {
            case SHOW_ERROR:
                showErrorMessage();
                break;
        }
    }
    
    /**
     * Show error message to user.
     */
    private void showErrorMessage() {
        DialogFragment dialogFragment = ErrorDialog.newInstance();
        dialogFragment.show( getSupportFragmentManager(), "ErrorDialog" );
    }
    
    /**
     * Display title in app's toolbar.
     *
     * @param title String to show in toolbar.
     */
    private void showToolbarTitle( String title ) {
        ActionBar actionBar = getSupportActionBar();
        
        if ( actionBar != null )
            actionBar.setTitle( title );
    }
    
    /**
     * Initialize ViewModel.
     */
    private void initViewModel() {
        mViewModel = new ViewModelProvider( this, mViewModelProviderFactory ).get( ArticleViewModel.class );
        
        // Get Article index from Intent that started this Activity.
        if ( getIntent().hasExtra( MainActivity.EXTRA_ARTICLE_INDEX ) ) {
            mViewModel.setInitialSlide( getIntent().getIntExtra( MainActivity.EXTRA_ARTICLE_INDEX, 0 ) );
    
            getIntent().removeExtra( MainActivity.EXTRA_ARTICLE_INDEX );
        }
        
        // Set view model observers.
        mViewModel.getArticleListLiveData().observe( this, listIntegerPair -> showArticles( listIntegerPair ) );
        mViewModel.getArticleTitleLiveData().observe( this, s -> showToolbarTitle( s ) );
        mViewModel.getViewStateLiveData().observe( this, viewState -> onViewStateChange( viewState ) );
    }
    
    /**
     * Initialize articles ViewPager.
     */
    private void initPagerArticles() {
        mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            /**
             * This method will be invoked when a new page becomes selected.
             *
             * @param position Position index of the new selected page.
             */
            @Override
            public void onPageSelected( int position ) {
                mViewModel.articlePageChanged( position );
            }
        };
        
        mBinding.pagerArticles.registerOnPageChangeCallback( mOnPageChangeCallback );
        
        mBinding.pagerArticles.setAdapter( mPagerArticleAdapter );
    }
    
}
