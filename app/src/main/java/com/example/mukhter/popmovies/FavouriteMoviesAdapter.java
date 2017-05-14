package com.example.mukhter.popmovies;

/**
 * Created by MUKHTER on 14/05/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;



public class FavouriteMoviesAdapter extends RecyclerView.Adapter<FavouriteMoviesAdapter.FavouriteMoviesAdapterViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private String posterPath;

    public FavouriteMoviesAdapter(Context context){

        mContext = context;
    }

    @Override
    public FavouriteMoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutID = R.layout.activity_favourite;
        boolean attachToScreenImmediately = false;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutID, parent,attachToScreenImmediately);

        FavouriteMoviesAdapterViewHolder adapterviewHolder = new FavouriteMoviesAdapterViewHolder(view);

        return adapterviewHolder;
    }

    @Override
    public void onBindViewHolder(FavouriteMoviesAdapterViewHolder holder, int position) {

        mCursor.moveToPosition(position);
        int nameIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_NAME);
        int posterIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER);

        String data = mCursor.getString(posterIndex);
        String name = mCursor.getString(nameIndex);


        posterPath = "http://image.tmdb.org/t/p/w185" + data;
        Picasso.with(mContext).load(posterPath).placeholder(R.mipmap.ic_launcher).into(holder.adapterImage);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null)return 0;
        return mCursor.getCount();
    }

    public class FavouriteMoviesAdapterViewHolder extends RecyclerView.ViewHolder {

        public final ImageView adapterImage;

        public FavouriteMoviesAdapterViewHolder(View itemView) {
            super(itemView);

            adapterImage = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }

    public void swapCursor(Cursor cursor){
        mCursor = cursor;
        notifyDataSetChanged();
    }
}