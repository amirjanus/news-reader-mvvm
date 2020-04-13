package com.example.newsreader.view.article.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsreader.R;
import com.example.newsreader.databinding.PagerArticleItemBinding;
import com.example.newsreader.utils.models.Article;
import com.squareup.picasso.Picasso;

public class PagerArticleFragment extends Fragment {
    
    private static final String KEY_ARTICLE = "KEY_ARTICLE";
    
    private PagerArticleItemBinding mBinding;
    
    public PagerArticleFragment() {}
    
    public static PagerArticleFragment newInstance( Article article ) {
        PagerArticleFragment fragment = new PagerArticleFragment();
        
        Bundle args = new Bundle();
        args.putParcelable( KEY_ARTICLE, article );
        
        fragment.setArguments( args );
        
        return fragment;
    }
    
    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container          Parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        mBinding = PagerArticleItemBinding.inflate( inflater, container, false );
        
        return mBinding.getRoot();
    }
    
    /**
     * Called immediately after #onCreateView().
     *
     * @param view               The View returned by #onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
        
        // Get the Article that was passed to this Fragment.
        Bundle bundle = getArguments();
        
        if ( bundle != null ) {
            Article article = bundle.getParcelable( KEY_ARTICLE );
            
            if ( article != null )
                showArticleData( article );
        }
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        
        mBinding = null;
    }
    
    /**
     * Show Article data in View.
     *
     * @param article Article to show in Fragment's view.
     */
    private void showArticleData( Article article ) {
        // Set article title.
        mBinding.textArticleTitle.setText( article.title );
        
        // Set article description.
        mBinding.textArticleDescription.setText( article.description );
        
        // Set article image.
        Picasso.get()
                .load( article.urlToImage )
                .placeholder( R.drawable.ic_image_black_24dp )
                .error( R.drawable.ic_broken_image_black_24dp )
                .into( mBinding.imageArticleImage );
    }
    
}
