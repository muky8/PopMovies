package com.example.mukhter.popmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.mukhter.popmovies.model.Popularmovies_model;
import com.example.mukhter.popmovies.model.Reviewmodel;
import com.example.mukhter.popmovies.model.trailermodel;
import com.example.mukhter.popmovies.network.Networkutils;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.example.mukhter.popmovies.network.Networkutils.BASE_URL;

public class Detailactivity extends AppCompatActivity {

    @InjectView(R.id.imagedetail)
    ImageView thumbnail;
    @InjectView(R.id.profile_id)
    ImageView backdropid;
    @InjectView(R.id.Movietitle)
    TextView Originaltitle;
    @InjectView(R.id.plotsynopsis)
    TextView Plotsynopsis;
    @InjectView(R.id.userrating)
    TextView Userrating;
    @InjectView(R.id.releasedate)
    TextView Releasedate;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    trailermodel trailmodel;
    private ViewGroup layoutContainer;
    private String mYTUri;
    private String id;
    public static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch";
    ArrayList<Reviewmodel> arrayList;
    MovieDbHelper moviedbh;
    public String image;
    public String originaltitle;
    public String plotsynopsis;
    public String userrating;
    public String releasedate;
    public String backdrop;
    MaterialFavoriteButton materialFavoriteButtonNice;
    Popularmovies_model popularmovies_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moviedbh = new MovieDbHelper(this);
        setContentView(R.layout.activity_detailactivity);
        ButterKnife.inject(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Movie Details");
        Intent intent = getIntent();
        final Popularmovies_model pop = intent.getParcelableExtra("Popmovies");
        image = pop.getImage();
        originaltitle = pop.getOriginaltitle();
        plotsynopsis = pop.getPlotsynopsis();
        userrating = pop.getUserrating();
        releasedate = pop.getReleasedate();
        backdrop = pop.getBackdrop();
        id = pop.getId();
        Picasso.with(this).load(image).into(thumbnail);
        Picasso.with(this).load(backdrop).into(backdropid);
        Originaltitle.setText(originaltitle);
        Plotsynopsis.setText(plotsynopsis);
        Userrating.setText(userrating);
        Releasedate.setText(releasedate);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        materialFavoriteButtonNice =
                (MaterialFavoriteButton) findViewById(R.id.toggleButton);
        materialFavoriteButtonNice.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (favorite) {

                            boolean isnserted = moviedbh.insertvalues(id, originaltitle, image,
                                    backdrop, releasedate, userrating, plotsynopsis);
                            if (isnserted = true) {
                                materialFavoriteButtonNice.setImageDrawable(ContextCompat.getDrawable(Detailactivity.this, R.drawable.ic_star_black_24dp));
                                SharedPreferences.Editor editor =
                                        getSharedPreferences("com.example.mukhter.popmovies.Detailactivity", MODE_PRIVATE).edit();
                                editor.putBoolean("Favorite Added", true);
                                editor.commit();

                            }
                        } else {
                            int movie_id = Integer.parseInt(id);
                            moviedbh = new MovieDbHelper(Detailactivity.this);
                            moviedbh.deleteFavorite(movie_id);
                            materialFavoriteButtonNice.setImageDrawable(ContextCompat.getDrawable(Detailactivity.this, R.drawable.ic_star_border_black_24dp));
                            SharedPreferences.Editor editor =
                                    getSharedPreferences("com.example.mukhter.popmovies.Detailactivity", MODE_PRIVATE).edit();
                            editor.putBoolean("Favorite Removed", false);
                            editor.commit();
                        }

                    }
                }
        );

    }


    public void launchYoutubeIntent(String video_key) {
        if (video_key != null) {
            Intent intent;
            if (checkAppInstalled("com.google.android.youtube")) {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + video_key));

            } else {
                Uri browser_YT = Uri.parse("https://www.youtube.com/watch")
                        .buildUpon()
                        .appendQueryParameter("v", video_key)
                        .build();
                intent = new Intent(Intent.ACTION_VIEW, browser_YT);

            }
            startActivity(intent);
        }

    }

    public boolean checkAppInstalled(String package_name) {
        Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage(package_name);
        if (intent != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void watchTrailer(View view) {
        String stringUri = BASE_URL + "/" + id + "/videos";
        final Uri uri = Uri.parse(stringUri).buildUpon()
                .appendQueryParameter(Networkutils.queryparam, Networkutils.API_KEY)
                .build();

        AsyncTask<Void, Void, JSONObject> async;
        async = new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... params) {
                URL url = null;
                String s = null;
                JSONObject jsonObject = null;

                try {
                    url = new URL(uri.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    s = Networkutils.getResponseFromHttpUrl(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    jsonObject = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return jsonObject;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                JSONArray jsonArray = null;
                JSONObject jsonObject1 = null;
                String key = null;

                try {
                    jsonArray = jsonObject.getJSONArray("results");
                    jsonObject1 = jsonArray.getJSONObject(0);
                    key = jsonObject1.getString("key");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Uri uri1 = Uri.parse(YOUTUBE_BASE_URL).buildUpon()
                        .appendQueryParameter("v", key)
                        .build();

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(uri1);

                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }

            }
        };

        async.execute();


    }


    public void reviewmethod() {
        String stringUri = BASE_URL + "/" + id + "/reviews";
        final Uri uri = Uri.parse(stringUri).buildUpon()
                .appendQueryParameter(Networkutils.queryparam, Networkutils.API_KEY)
                .build();

        AsyncTask<Void, Void, JSONObject> async;
        async = new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... params) {
                URL url = null;
                String s = null;
                JSONObject jsonObject = null;

                try {
                    url = new URL(uri.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    s = Networkutils.getResponseFromHttpUrl(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    jsonObject = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return jsonObject;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                JSONArray jsonArray = null;
                JSONObject jsonObject1 = null;
                arrayList = new ArrayList<>();
                String author, content;
                Reviewmodel model = new Reviewmodel();
                try {
                    jsonArray = jsonObject.getJSONArray("results");
                    jsonObject1 = jsonArray.getJSONObject(0);
                    author = jsonObject1.getString("author");
                    content = jsonObject1.getString("content");
                    model.setAuthor(author);
                    model.setContent(content);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Intent intent = new Intent(Detailactivity.this, Reviewactivity.class);
                intent.putExtra("review", model);

                startActivity(intent);
                arrayList.add(model);

            }
        };

        async.execute();


    }

    public void Review(View view) {
        reviewmethod();
    }
}
