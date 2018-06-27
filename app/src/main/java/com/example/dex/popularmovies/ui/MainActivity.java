package com.example.dex.popularmovies.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.dex.popularmovies.R;
import com.example.dex.popularmovies.api.MovieDBRestAPI;
import com.example.dex.popularmovies.api.MovieDBServiceAPI;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Context mContext;
    MovieDBRestAPI mdbAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getBaseContext();
        mdbAPI = MovieDBServiceAPI.createService(MovieDBRestAPI.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }

    }


    //Toolbar with title and home button
    protected void setToolBar(String title, boolean homeUp, boolean showHomeUp) {

        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeUp);
        getSupportActionBar().setDisplayShowHomeEnabled(showHomeUp);

    }

    // Toolbar without home button
    protected void setToolBar(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }
}
