package com.example.dex.popularmovies.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.dex.popularmovies.R;
import com.example.dex.popularmovies.api.MovieDBServiceAPI;
import com.example.dex.popularmovies.model.Movie;
import com.example.dex.popularmovies.model.MovieResults;
import com.example.dex.popularmovies.ui.adapter.MovieListAdapter;
import com.example.dex.popularmovies.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends MainActivity
        implements MovieListAdapter.IMovieListListener {
    private static final String TAG = MovieListActivity.class.getSimpleName();

    //    private final static int NB_CELL = 2;
    private static final int TITLE_MOVIE_DEFAULT = R.string.toolbar_pop_movies;
    public static String EXTRA_MOVIE_ID = "extra_movie_id";
    public static String EXTRA_MOVIE_TITLE = "extra_movie_title";

    @BindView(R.id.rv_movies_list)
    RecyclerView rvMovieList;
    @BindView(R.id.content_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.iv_error_loading)
    ImageView mErrorImage;

    private List<Movie> mMoviesList;
    private String SORT_BY = MovieDBServiceAPI.SORT_BY_DEFAULT;


    public static void startActivity(Context context) {
        Intent mainActivityIntent = new Intent(context, MovieListActivity.class);
        context.startActivity(mainActivityIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolBar(getString(TITLE_MOVIE_DEFAULT));
        setLayoutManager();

        CheckNetworking();

    }

    private void CheckNetworking() {
        /* Check network and api_key */
        if (Utils.isOnline(mContext)) {
            if (Utils.isValidApiKey()) {
                Log.d(Utils.TAG, "Valid API Key");
                httpGetMovies(SORT_BY);
            }
            // invalid API_KEY
            else {
                mProgressBar.setVisibility(View.GONE);
                Log.d(Utils.TAG, "Invalid API Key!!!");
                Utils.showDialog(MovieListActivity.this, getString(R.string.dialog_error_api_key_title), getString(R.string.dialog_error_api_key_message));
                mErrorImage.setVisibility(View.VISIBLE);

            }
        }
        // No network
        else {
            Utils.showDialog(MovieListActivity.this, getString(R.string.dialog_error_network_title), getString(R.string.dialog_error_network_message));
            mProgressBar.setVisibility(View.GONE);
            mErrorImage.setVisibility(View.VISIBLE);
        }

    }

    public void setLayoutManager() {
        int nbCell = Utils.calculateNoOfColumns(mContext);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, nbCell);
        rvMovieList.setLayoutManager(gridLayoutManager);
        rvMovieList.setHasFixedSize(true);
    }


    private void setRecyclerAdapter(RecyclerView recyclerView) {
        MovieListAdapter mAdapter = new MovieListAdapter(mContext, mMoviesList, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onMovieListClick(int clickMovieIndex) {
        Movie mMovieClicked = mMoviesList.get(clickMovieIndex);

        Intent movieDetailsIntent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
        movieDetailsIntent.putExtra(EXTRA_MOVIE_ID, mMovieClicked.getId());
        movieDetailsIntent.putExtra(EXTRA_MOVIE_TITLE, mMovieClicked.getTitle());
        startActivity(movieDetailsIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_sort_by_popularity:
                SORT_BY = MovieDBServiceAPI.SORT_BY_POPULARITY;
                item.setChecked(true);
                setToolBar(getString(R.string.toolbar_pop_movies));
                httpGetMovies(SORT_BY);
                return true;

            case R.id.item_sort_by_top_rated:
                SORT_BY = MovieDBServiceAPI.SORT_BY_TOP_RATED;
                item.setChecked(true);
                setToolBar(getString(R.string.toolbar_top_movies));
                httpGetMovies(SORT_BY);
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }

    }

    /**************************************************************************************************
     *                                            HTTP calls
     ******************************************************************/

    public void httpGetMovies(String sortBy) {

        Call<MovieResults> call = mdbAPI.getPopluarMovies(sortBy);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {

                if (response.isSuccessful()) {
                    mMoviesList = response.body().getmMoviesResults();
                    if (mMoviesList.size() != 0) {
                        mProgressBar.setVisibility(View.GONE);
                        setRecyclerAdapter(rvMovieList);
                    } else {

                        Log.d(TAG, "onResponse: Empty List Error");
                    }
                } else {
                    Log.d(TAG, "onResponse: Response error");
                }
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

                Utils.showLongToastMessage(mContext, "Error Fetching movies: " + t.getMessage());

            }
        });
    }


}
