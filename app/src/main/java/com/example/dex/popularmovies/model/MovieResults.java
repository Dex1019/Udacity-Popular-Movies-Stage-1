package com.example.dex.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResults {

    @SerializedName("results")
    private List<Movie> mMoviesResults;

    public List<Movie> getmMoviesResults() {
        return mMoviesResults;
    }
}
