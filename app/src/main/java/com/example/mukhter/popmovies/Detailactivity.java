package com.example.mukhter.popmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mukhter.popmovies.model.Popularmovies_model;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Detailactivity extends AppCompatActivity {

    @InjectView(R.id.imagedetail)
    ImageView thumbnail;
    @InjectView(R.id.Movietitle)
    TextView Originaltitle;
    @InjectView(R.id.plotsynopsis)
    TextView Plotsynopsis;
    @InjectView(R.id.userrating)
    TextView Userrating;
    @InjectView(R.id.releasedate)
    TextView Releasedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailactivity);
        ButterKnife.inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Movie Detail");
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Popularmovies_model pop = intent.getParcelableExtra("Popmovies");


            String image = pop.getImage();
            String originaltitle = pop.getOriginaltitle();
            String plotsynopsis = pop.getPlotsynopsis();
            String userrating = pop.getUserrating();
            String releasedate = pop.getReleasedate();

            Picasso.with(this).load(image).into(thumbnail);
            Originaltitle.setText(originaltitle);
            Plotsynopsis.setText(plotsynopsis);
            Userrating.setText(userrating);
            Releasedate.setText(releasedate);

        }
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
}
