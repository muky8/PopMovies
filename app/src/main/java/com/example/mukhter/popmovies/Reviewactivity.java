package com.example.mukhter.popmovies;

/**
 * Created by MUKHTER on 14/05/2017.
 */

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mukhter.popmovies.model.Popularmovies_model;
import com.example.mukhter.popmovies.model.Reviewmodel;
import com.example.mukhter.popmovies.network.Networkutils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Reviewactivity extends AppCompatActivity {
TextView textView1,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_activity);
        textView1 = (TextView)findViewById(R.id.textView);
        textView2=(TextView)findViewById(R.id.textView2);
        Intent intent = getIntent();
        Reviewmodel reviewmodel = intent.getParcelableExtra("review");
        String author = reviewmodel.getAuthor();
        String content = reviewmodel.getContent();
        textView1.setMovementMethod(new ScrollingMovementMethod());
textView2.setMovementMethod(new ScrollingMovementMethod());

        textView1.setText(author);
        textView2.setText(content);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }

        return false;
    }


}
