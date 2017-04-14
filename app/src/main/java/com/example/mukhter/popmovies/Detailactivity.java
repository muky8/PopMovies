package com.example.mukhter.popmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Detailactivity extends AppCompatActivity {
    ImageView thumbnail;
    TextView Originaltitle,
     Plotsynopsis,
    Userrating,
    Releasedate;
    RelativeLayout activity_detailactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailactivity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Movie Detail");
        Bundle bundle= getIntent().getExtras();
        String image = bundle.getString("Image");
        String originaltitle = bundle.getString("Originaltitle");
        String plotsynopsis = bundle.getString("Plotsynopsis");
        String userrating = bundle.getString("Userrating");
        String releasedate = bundle.getString("Releasedate");


        thumbnail= (ImageView)findViewById(R.id.imagedetail);
        Picasso.with(this).load(image).into(thumbnail);
        Originaltitle =(TextView)findViewById(R.id.Movietitle);
        Plotsynopsis =(TextView)findViewById(R.id.plotsynopsis);
        Userrating=(TextView)findViewById(R.id.userrating);
       Releasedate =(TextView)findViewById(R.id.releasedate);

        Originaltitle.setText(originaltitle);
        Plotsynopsis.setText(plotsynopsis);
        Userrating.setText(userrating);
        Releasedate.setText(releasedate);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
