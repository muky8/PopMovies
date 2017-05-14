package com.example.mukhter.popmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MUKHTER on 01/05/2017.
 */

public class trailermodel implements Parcelable{
    public String movieid;
    public String key;
    public String moviename;

    public trailermodel() {
        this.movieid = movieid;
        this.key = key;
        this.moviename = moviename;
    }

    protected trailermodel(Parcel in) {
        movieid = in.readString();
        key = in.readString();
        moviename = in.readString();
    }

    public static final Creator<trailermodel> CREATOR = new Creator<trailermodel>() {
        @Override
        public trailermodel createFromParcel(Parcel in) {
            return new trailermodel(in);
        }

        @Override
        public trailermodel[] newArray(int size) {
            return new trailermodel[size];
        }
    };

    public String getMovieid() {
        return movieid;
    }

    public void setMovieid(String movieid) {
        this.movieid = movieid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieid);
        dest.writeString(key);
        dest.writeString(moviename);
    }
}
