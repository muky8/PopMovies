package com.example.mukhter.popmovies;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by MUKHTER on 14/05/2017.
 */

public class FavouriteMovies extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView recyclerView;
    private TextView errorText;
    private ProgressBar progressBar;
    private FavouriteMoviesAdapter adapter;
    private GridLayoutManager gridLayoutManager;

    private static final int CURSOR_LOADER_ID = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);


        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        gridLayoutManager = new GridLayoutManager(this, 3);


        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new FavouriteMoviesAdapter(this);
        recyclerView.setAdapter(adapter);

        getSupportLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return false;
    }

    public void onError(){

        recyclerView.setVisibility(View.INVISIBLE);
    }

    public void ShowProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

    }

    public void displayRecyclerView(){

        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(this,
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data != null) {

            displayRecyclerView();
            adapter.swapCursor(data);
        }else {

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
