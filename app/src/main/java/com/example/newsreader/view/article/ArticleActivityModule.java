package com.example.newsreader.view.article;

import androidx.lifecycle.ViewModel;

import com.example.newsreader.di.ViewModelKey;
import com.example.newsreader.view.article.adapter.PagerArticleAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public abstract class ArticleActivityModule {
    
    @Provides
    static PagerArticleAdapter providePagerArticleAdapter( ArticleActivity articleActivity ) {
        return new PagerArticleAdapter( articleActivity );
    }
    
    @Binds
    @IntoMap
    @ViewModelKey( ArticleViewModel.class )
    public abstract ViewModel bindMainViewModel( ArticleViewModel articleViewModel );
    
}
