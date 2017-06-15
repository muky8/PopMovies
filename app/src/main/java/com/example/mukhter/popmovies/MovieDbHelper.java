package com.example.mukhter.popmovies;

/**
 * Created by MUKHTER on 14/05/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;

import com.example.mukhter.popmovies.model.Popularmovies_model;

import java.util.ArrayList;
import java.util.List;


public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " ("
                + MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY,"
                + MovieContract.MovieEntry.COLUMN_MOVIE_NAME + " TEXT,"
                + MovieContract.MovieEntry.COLUMN_MOVIE_POSTER + " TEXT,"
                + MovieContract.MovieEntry.COLUMN_MOVIE_VIDEO_POSTER + " TEXT,"
                + MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT,"
                + MovieContract.MovieEntry.COLUMN_MOVIE_RATING + " TEXT,"
                + MovieContract.MovieEntry.COLUMN_MOVIE_PLOT + " TEXT)";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
 public boolean insertvalues(String id,String name, String movieposter,String backdrop,
                             String releasedate, String rating, String plotsynopsis){

     SQLiteDatabase db = this.getWritableDatabase();
     ContentValues contentvalues = new ContentValues();
     contentvalues.put(MovieContract.MovieEntry._ID,id);
     contentvalues.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME,name);
     contentvalues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER,movieposter);
     contentvalues.put(MovieContract.MovieEntry.COLUMN_MOVIE_VIDEO_POSTER,backdrop);
     contentvalues.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE,releasedate);
     contentvalues.put(MovieContract.MovieEntry.COLUMN_MOVIE_RATING,rating);
     contentvalues.put(MovieContract.MovieEntry.COLUMN_MOVIE_PLOT,plotsynopsis);

long result = db.insert(MovieContract.MovieEntry.TABLE_NAME,null,contentvalues);
     if(result==-1){
         return false;
     }else
         return true;
 }
    public void deleteFavorite(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MovieContract.MovieEntry.TABLE_NAME, MovieContract.MovieEntry._ID+ "=" + id, null);
    }


    public List<Popularmovies_model> getAllfavourites(){

        String sortOrder = MovieContract.MovieEntry._ID;
        List<Popularmovies_model>favlist = new ArrayList<>();
String []column={
        MovieContract.MovieEntry._ID,
        MovieContract.MovieEntry.COLUMN_MOVIE_NAME,
        MovieContract.MovieEntry.COLUMN_MOVIE_POSTER,
        MovieContract.MovieEntry.COLUMN_MOVIE_VIDEO_POSTER,
        MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE,
        MovieContract.MovieEntry.COLUMN_MOVIE_RATING,
        MovieContract.MovieEntry.COLUMN_MOVIE_PLOT
};
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(MovieContract.MovieEntry.TABLE_NAME,
                column,
                null,
                null,
                null,
                null,
                sortOrder);
        if (cursor.moveToFirst()){
            do {
                Popularmovies_model movie = new Popularmovies_model();
                movie.setId(String.valueOf(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry._ID)))));
                movie.setOriginaltitle(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_NAME)));
                movie.setUserrating((cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_RATING))));
                movie.setImage(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER)));
                movie.setPlotsynopsis(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_PLOT)));

                favlist.add(movie);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favlist;
    }
}