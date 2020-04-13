package com.example.newsreader.view.main;

import androidx.lifecycle.ViewModel;

import com.example.newsreader.di.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainActivityModule {
    
    @Binds
    @IntoMap
    @ViewModelKey( MainViewModel.class )
    public abstract ViewModel bindMainViewModel( MainViewModel mainViewModel );
    
}
