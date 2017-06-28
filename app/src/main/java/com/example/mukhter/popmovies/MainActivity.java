package com.example.mukhter.popmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.evernote.android.state.StateSaver;
import com.example.mukhter.popmovies.model.trailermodel;
import com.example.mukhter.popmovies.network.InternetConnection;
import com.example.mukhter.popmovies.network.Networkutils;
import com.example.mukhter.popmovies.model.Popularmovies_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.mukhter.popmovies.MovieContract.MovieEntry.CONTENT_URI;

public class MainActivity extends AppCompatActivity {
    private static final String MOVIE_KEY = "key";
    String items;
    private static final String POP_MOVIE = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String BASE_IMAGE = "http://image.tmdb.org/t/p/w185/";
    private static final String BASE_DROPIMAGE = "http://image.tmdb.org/t/p/w342/";
    private GridView gridView;

    JSONArray array;
    JSONObject part;
    static List<String> urls;
    public ArrayList<Popularmovies_model> arrayList, secarray;
    static ArrayList<trailermodel> secarrayList;
    static int mcurrentposition;
    Imageadapter imageadapter;
    Imageadapter moviesAdapter;
    ProgressDialog mprogressbar;
    Context context = this;
    ActionBar actionBar;
    MovieDbHelper moviedbh;
    RecyclerView recyclerView;
    private List<Popularmovies_model> List;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviedbh = new MovieDbHelper(this);
        gridView = (GridView) findViewById(R.id.grid);
        if (InternetConnection.checkConnection(context)) {
            //internet available
        } else {
            Toast.makeText(context, "Check internet connection", Toast.LENGTH_SHORT).show();

        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        actionBar = getSupportActionBar();
        arrayList = new ArrayList<>(); // initializing the arraylist
        secarrayList = new ArrayList<>();
        secarray = new ArrayList<>();
        mprogressbar = new ProgressDialog(this);
        SearchQuery(POP_MOVIE);


        // by default populates the screen with popular movies
        imageadapter = new Imageadapter(this, R.layout.singleitem, arrayList);
        gridView.setAdapter(imageadapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Popularmovies_model popposition = (Popularmovies_model) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, Detailactivity.class);
                intent.putExtra("Popmovies", popposition);

                startActivity(intent);
            }
        });

    }


    public class DownloadTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            mprogressbar.setMessage("Loading...");
            mprogressbar.setCancelable(true);
            mprogressbar.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String Results = null;
            urls = new ArrayList<>();
            Popularmovies_model movie;
            try {
                Results = Networkutils.getResponseFromHttpUrl(searchUrl);
                JSONObject jsonObject = new JSONObject(Results);
                items = jsonObject.getString("results");
                array = new JSONArray(items);

                for (int i = 0; i < array.length(); i++) {
                    movie = new Popularmovies_model();
                    part = array.getJSONObject(i);
                    if (null != part && part.length() > 0) {
                        if (part != null) {
                            String path = part.getString("poster_path");
                            movie.setImage(BASE_IMAGE + path);
                            Log.i("String", part.getString("poster_path"));

                            String backdrop = part.getString("backdrop_path");
                            movie.setBackdrop(BASE_DROPIMAGE + backdrop);


                            String originaltitle = part.getString("original_title");
                            movie.setOriginaltitle(originaltitle);

                            String plotsynopsis = part.getString("overview");
                            movie.setPlotsynopsis(plotsynopsis);

                            String userrating = part.getString("vote_average");
                            movie.setUserrating(userrating);

                            String releasedate = part.getString("release_date");
                            movie.setReleasedate(releasedate);

                            String id = part.getString("id");
                            movie.setId(id);
                            String key = movie.getId();

                        }

                    }
                    arrayList.add(movie);
                }

                Log.i("This", items);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return Results;

        }


        @Override
        protected void onPostExecute(String s) {
            mprogressbar.cancel();
            imageadapter.setGridData(arrayList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.sortby_pop) {
            arrayList.clear();
            view();
            Toast.makeText(context, "Sorting by Popular Movies", Toast.LENGTH_SHORT).show();
            SearchQuery(POP_MOVIE);


            return true;
        } else if (itemThatWasClickedId == R.id.sortby_toprated) {
            arrayList.clear();
            view();

            Toast.makeText(context, "Sorting by Top Rated Movies", Toast.LENGTH_SHORT).show();
            SearchQuery(TOP_RATED);
        } else if (itemThatWasClickedId == R.id.sortby_Favourites) {
            views2();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SearchQuery(String sort) {
        URL SearchUrl = Networkutils.buildUrl(sort);
        new DownloadTask().execute(SearchUrl);

    }

    public void view() {
        imageadapter = new Imageadapter(this, R.layout.singleitem, arrayList);
        gridView.setAdapter(imageadapter);
        imageadapter.notifyDataSetChanged();
    }

    public void views2() {
        secarray = new ArrayList<>();
        moviesAdapter = new Imageadapter(this, R.layout.singleitem, secarray);
        gridView.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();
        moviedbh = new MovieDbHelper(this);
        getAllFavourites();

    }

    private void getAllFavourites() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                secarray.clear();
                secarray.addAll(moviedbh.getAllfavourites());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                moviesAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    public static URL url = null;


    public static URL buildUrltrailer(String key) {

        Uri builtUri = Uri.parse(fetchtrailer.BASE_URL).buildUpon()
                .appendPath(key).
                        appendPath(fetchtrailer.VIDEO)
                .appendQueryParameter(fetchtrailer.queryparam, fetchtrailer.API_KEY)
                .build();


        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i("Trailer", "Built URI " + url);

        return url;
    }

    void testmethod() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        trailermodel trailer;
                        try {
                            String res = response.toString();
                            JSONObject initial = new JSONObject(res);
                            JSONArray results = initial.getJSONArray("results");


                            for (int i = 0; i < results.length(); i++) {
                                JSONObject obj = results.getJSONObject(i);
                                String key = obj.getString("key");
                                String name = obj.getString("name");
                                trailer = new trailermodel();
                                trailer.setMoviename(name);

                                trailer.setKey(key);
                                Log.i("key", name);
                                secarrayList.add(trailer);
                            }

                            Log.i("Response", response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                // hide the progress dialog

            }
        });

        Volley.newRequestQueue(this).add(jsonObjReq);
    }
}