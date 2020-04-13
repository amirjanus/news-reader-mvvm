package com.example.newsreader.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class ViewModelProviderFactory implements ViewModelProvider.Factory {
    
    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;
    
    @Inject
    public ViewModelProviderFactory( Map<Class<? extends ViewModel>, Provider<ViewModel>> creators ) {
        this.creators = creators;
    }
    
    @Override
    public <T extends ViewModel> T create( Class<T> modelClass ) {
        Provider<? extends ViewModel> creator = creators.get( modelClass );
    
        // Check if the viewmodel has not been created.
        if ( creator == null ) {
            // Loop through the allowable keys ( allowed classes with the @ViewModelKey )
            for ( Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : creators.entrySet() ) {
                
                // If it's allowed, set the Provider<ViewModel>
                if ( modelClass.isAssignableFrom( entry.getKey() ) ) {
                    creator = entry.getValue();
                    
                    break;
                }
            }
        }
        
        // If this is not one of the allowed keys, throw exception.
        if ( creator == null ) {
            throw new IllegalArgumentException( "Unknown model class: " + modelClass );
        }
        
        // Return the Provider.
        try {
            return ( T ) creator.get();
        } catch ( Exception e ) {
            throw new RuntimeException( e );
        }
    }
}