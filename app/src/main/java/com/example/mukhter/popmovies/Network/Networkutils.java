package com.example.mukhter.popmovies.network;

import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by MUKHTER on 02/04/2017.
 */

public final class Networkutils {
    private static final String TAG = Networkutils.class.getSimpleName();
   public static final String API_KEY = ""; //API key required

    public static final String BASE_URL = "http://api.themoviedb.org/3/movie";
    public static final String MOVIE ="movie";
    private static final String POP_MOVIE = "popular";
    private static final String TOP_RATED = "top_rated";


    public static final String queryparam = "api_key";


    public static URL buildUrl(String Query) {

        Uri builtUri = Uri.parse(BASE_URL).buildUpon().
                appendPath(Query)
                .appendQueryParameter(queryparam, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "Built URI " + url);

        return url;

    }



    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
