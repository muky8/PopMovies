package com.example.mukhter.popmovies;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.mukhter.popmovies.MovieContract.AUTHORITY;
import static com.example.mukhter.popmovies.MovieContract.MOVIE;
import static com.example.mukhter.popmovies.MovieContract.MOVIE_ID;
import static com.example.mukhter.popmovies.MovieContract.MovieEntry.TABLE_NAME;
import static com.example.mukhter.popmovies.MovieContract.PATH;

/**
 * Created by MUKHTER on 21/06/2017.
 */

public class PopMoviesProvider extends ContentProvider {
    private MovieDbHelper movieDbHelper;
    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, PATH, MOVIE);
        sURIMatcher.addURI(AUTHORITY, PATH + "/#", MOVIE_ID);
    }
    @Override
    public boolean onCreate() {
        movieDbHelper= new MovieDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case MOVIE_ID:
                queryBuilder.appendWhere(MovieContract.MovieEntry._ID + "="
                        + uri.getLastPathSegment());
                break;
            case MOVIE:
                // no filter
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Cursor cursor = queryBuilder.query(movieDbHelper.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case MOVIE_ID:
                return "vnd.android.cursor.dir/movie";
            case MOVIE:
                // no filter
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        // Get access to the task database (to write new data to)
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();

        // Write URI matching code to identify the match for the tasks directory
        int match = sURIMatcher.match(uri);
        Uri returnUri; // URI to be returned

        switch (match) {
            case MOVIE_ID:
                // Insert new values into the database
                // Inserting values into tasks table
                long id = db.insert(TABLE_NAME, null, contentValues);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            // Set the value for the returnedUri and write the default case for unknown URI's
            // Default case throws an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
