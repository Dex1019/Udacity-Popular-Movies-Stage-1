package com.example.dex.popularmovies.api;

import com.example.dex.popularmovies.model.Movie;
import com.example.dex.popularmovies.model.MovieResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieDBRestAPI {

    //http://api.themoviedb.org/3/movie/popular?api_key=999999999999999999
    @GET("movie/{sort_by}")
    Call<MovieResults> getPopluarMovies(@Path("sort_by") String sortBy);

    //http://api.themoviedb.org/3/movie/123?api_key=99999999999999999999
    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetails(@Path("movie_id") long movieId);
}
