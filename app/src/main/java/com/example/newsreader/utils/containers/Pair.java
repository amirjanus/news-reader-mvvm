package com.example.newsreader.utils.containers;

import androidx.annotation.NonNull;

/**
 * Data structure that holds two values.
 *
 * @param <F> First object.
 * @param <S> Second object.
 */
public class Pair<F, S> {
    
    public F first;
    public S second;
    
    /**
     * Constructor for a Pair.
     *
     * @param first  First object in the Pair.
     * @param second Second object in the Pair.
     */
    public Pair( @NonNull F first, @NonNull S second ) {
        this.first = first;
        this.second = second;
    }
    
    /**
     * Returns a string representation of the object.
     *
     * @return String representation of the object.
     */
    @NonNull
    @Override
    public String toString() {
        return this + ": " + first + ", " + second;
    }
    
}
