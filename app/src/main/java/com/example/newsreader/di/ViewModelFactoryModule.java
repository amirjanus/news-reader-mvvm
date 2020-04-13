package com.example.newsreader.di;

import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {
    
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory( ViewModelProviderFactory viewModelProviderFactory );
    
}
