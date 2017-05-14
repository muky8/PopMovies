package com.example.mukhter.popmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.mukhter.popmovies.model.Popularmovies_model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MUKHTER on 24/04/2017.
 */

public class gridAdapter extends RecyclerView.Adapter<gridAdapter.ViewHolder> {

    private ArrayList<Popularmovies_model> mgridData = new ArrayList<>();
    private Context mContext;

    public gridAdapter(Context mContext, ArrayList<Popularmovies_model> mgridData) {
        this.mContext = mContext;
        this.mgridData = mgridData;
    }

    private Context getmContext() {
        return mContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView thumbnailimage;


        public ViewHolder(View itemView) {
            super(itemView);

            thumbnailimage = (ImageView) itemView.findViewById(R.id.thumbnail);


        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForGrid = R.layout.singleitem;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;


        View view = inflater.inflate(layoutIdForGrid, parent, shouldAttachToParentImmediately);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Popularmovies_model popmovies = mgridData.get(position);
        Picasso.with(mContext).load(popmovies.getImage()).placeholder(R.drawable.placeholderimage)
                .error(R.drawable.errorimage)
                .into(holder.thumbnailimage);

    }

    @Override
    public int getItemCount() {
        return mgridData.size();
    }
}
