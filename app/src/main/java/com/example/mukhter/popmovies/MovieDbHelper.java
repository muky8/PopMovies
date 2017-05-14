package com.example.mukhter.popmovies;

/**
 * Created by MUKHTER on 14/05/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " ("
                + MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_NAME + " TEXT NOT NULL, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_GENRE + " TEXT, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_TRAILER + " TEXT, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_POSTER + " BLOB, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_VIDEO_POSTER + " BLOB, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_RATING + " TEXT, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_PLOT + " TEXT, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_COUNT + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP IF EXISTS" + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }


}