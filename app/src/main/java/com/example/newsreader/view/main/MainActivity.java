package com.example.newsreader.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.newsreader.databinding.ActivityMainBinding;
import com.example.newsreader.di.ViewModelProviderFactory;
import com.example.newsreader.utils.dialogs.ErrorDialog;
import com.example.newsreader.utils.models.Article;
import com.example.newsreader.view.article.ArticleActivity;
import com.example.newsreader.view.main.adapter.RecyclerArticleAdapter;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements RecyclerArticleAdapter.OnArticleClickListener {
    
    public static final String EXTRA_ARTICLE_INDEX = "EXTRA_ARTICLE_INDEX";
    
    @Inject
    RecyclerArticleAdapter mAdapter;
    
    @Inject
    ViewModelProviderFactory mViewModelProviderFactory;
    
    private MainViewModel mViewModel;
    
    private ActivityMainBinding mBinding;
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        
        // Set view binding.
        mBinding = ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView( mBinding.getRoot() );
        
        // Make view's content to appear behind the navigation bar.
        mBinding.getRoot().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION );
        
        initRecyclerArticle();
        
        initViewModel();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        mViewModel.fetchArticles();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        mBinding = null;
    }
    
    /**
     * Called when user clicks on Article's RecyclerView item.
     *
     * @param articleIndex Position index of selected article item.
     */
    @Override
    public void onArticleClick( int articleIndex ) {
        mViewModel.articleSelected( articleIndex );
    }
    
    /**
     * Starts ArticleActivity.
     *
     * @param articleIndex Position index of selected article item.
     */
    private void startArticleActivity( int articleIndex ) {
        Intent intent = new Intent( MainActivity.this, ArticleActivity.class );
        intent.putExtra( EXTRA_ARTICLE_INDEX, articleIndex );
        
        startActivity( intent );
    }
    
    /**
     * Display Articles list in view.
     *
     * @param articleList List of Articles to show.
     */
    private void showArticles( List<Article> articleList ) {
        mAdapter.setDataset( articleList );
    }
    
    /**
     * Called when views gets notified that the view state has changed.
     *
     * @param viewState New view state.
     */
    private void onViewStateChange( ViewState viewState ) {
        switch ( viewState ) {
            case SHOW_LOADER:
                showProgressLoader( true );
                break;
                
            case HIDE_LOADER:
                showProgressLoader( false );
                break;
                
            case SHOW_ERROR:
                showErrorMessage();
                break;
        }
    }
    
    /**
     * Shows ProgressBar in view.
     *
     * @param show True to show ProgressBar, false to hide it.
     */
    private void showProgressLoader( boolean show ) {
        if ( show )
            mBinding.progressLoader.setVisibility( View.VISIBLE );
        else
            mBinding.progressLoader.setVisibility( View.GONE );
    }
    
    /**
     * Show error message to user.
     */
    private void showErrorMessage() {
        DialogFragment dialogFragment = ErrorDialog.newInstance();
        dialogFragment.show( getSupportFragmentManager(), "ErrorDialog" );
    }
    
    /**
     * Initialize ViewModel.
     */
    private void initViewModel() {
        mViewModel = new ViewModelProvider( this, mViewModelProviderFactory ).get( MainViewModel.class );
        
        mViewModel.getArticleListLiveData().observe( this, articleList -> showArticles( articleList ) );
        mViewModel.getSelectedArticleLiveData().observe( this, integer -> startArticleActivity( integer ) );
        mViewModel.getViewStateLiveData().observe( this, viewState -> onViewStateChange( viewState ) );
    }
    
    /**
     * Initialize RecyclerView to show Articles list.
     */
    private void initRecyclerArticle() {
        mBinding.recyclerArticle.setLayoutManager( new LinearLayoutManager( this ) );
        mBinding.recyclerArticle.setAdapter( mAdapter );
        
        mAdapter.setOnArticleClickListener( this );
    }
    
}
