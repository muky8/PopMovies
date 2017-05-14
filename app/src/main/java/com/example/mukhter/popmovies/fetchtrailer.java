package com.example.mukhter.popmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mukhter.popmovies.model.Popularmovies_model;
import com.example.mukhter.popmovies.model.trailermodel;
import com.example.mukhter.popmovies.network.Networkutils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by MUKHTER on 02/05/2017.
 */

public class fetchtrailer {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String API_KEY = "0e067356a27385b1d3ffd877d6773f75"; //API key required
    public static final String BASE_URL = "http://api.themoviedb.org/3/movie";
    public static final String VIDEO ="videos";
    public static final String queryparam = "api_key";



}
