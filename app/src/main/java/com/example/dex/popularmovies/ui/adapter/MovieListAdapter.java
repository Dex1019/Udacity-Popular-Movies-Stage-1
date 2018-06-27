package com.example.dex.popularmovies.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dex.popularmovies.R;
import com.example.dex.popularmovies.api.MovieDBServiceAPI;
import com.example.dex.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    final private IMovieListListener mOnClickListener;
    private Context mContext;
    private List<Movie> moviesList;

    public MovieListAdapter(Context mContext, List<Movie> moviesList, IMovieListListener mOnClickListener) {
        this.mContext = mContext;
        this.moviesList = moviesList;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context mContext = parent.getContext();

        int layoutForMovieItem = R.layout.activity_movie_list;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutForMovieItem, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Movie mMovie = moviesList.get(position);

//        Log.d(Utils.TAG, "poster =  " + mMovie.getPoster());
        Picasso.with(mContext)
                .load(MovieDBServiceAPI.API_POSTER_HEADER_LARGE + mMovie.getPoster())
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_error)
                .into(holder.ivPoser);
        holder.itemView.setTag(mMovie);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    /**
     * The interface that receives onClick messages
     */
    public interface IMovieListListener {
        void onMovieListClick(int clickMovieIndex);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_poster)
        ImageView ivPoser;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onMovieListClick(clickedPosition);

        }
    }
}
