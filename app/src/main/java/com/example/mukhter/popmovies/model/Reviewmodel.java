package com.example.mukhter.popmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MUKHTER on 01/05/2017.
 */

public class Reviewmodel implements Parcelable{
    public String author;
    public String content;

    public Reviewmodel() {
        this.author = author;
        this.content = content;
    }

    protected Reviewmodel(Parcel in) {

        author=in.readString();
        content=in.readString();
    }

    public static final Creator<Reviewmodel> CREATOR = new Creator<Reviewmodel>() {
        @Override
        public Reviewmodel createFromParcel(Parcel in) {
            return new Reviewmodel(in);
        }

        @Override
        public Reviewmodel[] newArray(int size) {
            return new Reviewmodel[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);

    }
}
