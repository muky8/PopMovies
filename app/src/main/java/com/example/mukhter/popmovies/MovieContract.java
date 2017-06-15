package com.example.mukhter.popmovies;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by MUKHTER on 14/05/2017.
 */

public class MovieContract {
    public static final String AUTHORITY = "com.example.mukhter.popmovie$s";
    public static final Uri BASE_URL = Uri.parse("content://" + AUTHORITY);
    public static final String PATH = "movie";
    private MovieContract(){

    }

    public static class MovieEntry implements BaseColumns {
        public  final static Uri CONTENT_URI = BASE_URL.buildUpon().appendPath(PATH).build();
        public final static String TABLE_NAME = "movie";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_MOVIE_NAME = "name";
        public final static String COLUMN_MOVIE_GENRE = "genre";
        public final static String COLUMN_MOVIE_TRAILER = "trailer";
        public final static String COLUMN_MOVIE_POSTER = "posterImage";
        public final static String COLUMN_MOVIE_VIDEO_POSTER = "videoImage";
        public final static String COLUMN_MOVIE_RELEASE_DATE = "releaseDate";
        public final static String COLUMN_MOVIE_RATING = "rating";
        public final static String COLUMN_MOVIE_PLOT = "plot";
        public final static String COLUMN_MOVIE_VOTE_COUNT = "voteCount";

    }
}
