package com.example.mukhter.popmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.mukhter.popmovies.model.Popularmovies_model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MUKHTER on 04/04/2017.
 */

public class Imageadapter extends ArrayAdapter<Popularmovies_model> {

        private Context context;
       private int layoutresource;

        private ArrayList<Popularmovies_model> mgridData= new ArrayList<>();


    public Imageadapter(Context context,int layoutresource, ArrayList<Popularmovies_model> mgridData) {
        super(context, layoutresource, mgridData);
        this.layoutresource = layoutresource;
        this.context=context;
        this.mgridData = mgridData;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutresource, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.thumbnail);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Popularmovies_model item = mgridData.get(position);

        Picasso.with(context).load(item.getImage()).into(holder.imageView);
        return row;
    }

    static class ViewHolder {
        ImageView imageView;
    }

    public void setGridData(ArrayList<Popularmovies_model> mGridData) {
        this.mgridData = mGridData;
        notifyDataSetChanged();
    }
}
