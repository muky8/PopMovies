package com.example.mukhter.popmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mukhter.popmovies.Network.InternetConnection;
import com.example.mukhter.popmovies.Network.Networkutils;
import com.example.mukhter.popmovies.model.Popularmovies_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String items;
    private static final String POP_MOVIE= "popular";
    private static final String TOP_RATED= "top_rated";
    private static final String BASE_IMAGE="http://image.tmdb.org/t/p/w185/";
    GridView gridView;
    JSONArray array;
    JSONObject part;
    String title;
    static List<String> urls;
    ArrayList<Popularmovies_model>arrayList;

    Imageadapter imageadapter;
    ProgressDialog mprogressbar;
  Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         gridView = (GridView)findViewById(R.id.grid);
        if (InternetConnection.checkConnection(context)) {
//internet available
        } else {
            Toast.makeText(context, "Check internet connection", Toast.LENGTH_SHORT).show();
        }


        arrayList = new ArrayList<>(); // initializing the arraylist
      mprogressbar= new ProgressDialog(this);

        SearchQuery(POP_MOVIE);  // by default populates the screen with popular movies

        imageadapter = new Imageadapter(this,R.layout.singleitem,arrayList);

        gridView.setAdapter(imageadapter);
         gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Popularmovies_model popposition = (Popularmovies_model)parent.getItemAtPosition(position);
     Intent intent = new Intent(MainActivity.this,Detailactivity.class);

        intent.putExtra("Image",popposition.getImage());
        intent.putExtra("Originaltitle",popposition.getOriginaltitle());
        intent.putExtra("Plotsynopsis",popposition.getPlotsynopsis());
        intent.putExtra("Userrating",popposition.getUserrating());
        intent.putExtra("Releasedate",popposition.getReleasedate());
        startActivity(intent);
    }
});
    }


    public class DownloadTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            mprogressbar.setMessage("Loading...");
            mprogressbar.setCancelable(false);
            mprogressbar.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String Results = null;
            urls= new ArrayList<>();
            Popularmovies_model movie;
            try {
                Results = Networkutils.getResponseFromHttpUrl(searchUrl);
                JSONObject jsonObject = new JSONObject(Results);
                items = jsonObject.getString("results");
                  array = new JSONArray(items);

                for(int i=0;i<array.length();i++) {
                    movie = new Popularmovies_model();
                    part = array.getJSONObject(i);
                    if (null != part&& part.length() > 0) {
                        if (part != null) {
                            String path =part.getString("poster_path");
                            movie.setImage(BASE_IMAGE + path);
                            Log.i("String", part.getString("poster_path"));

                              String originaltitle = part.getString("original_title");
                            movie.setOriginaltitle(originaltitle);

                            String plotsynopsis = part.getString("overview");
                            movie.setPlotsynopsis(plotsynopsis);

                            String userrating = part.getString("vote_average");
                            movie.setUserrating(userrating);

                            String releasedate = part.getString("release_date");
                            movie.setReleasedate(releasedate);


                        }

                    }
                    arrayList.add(movie);
                }

                Log.i("This",items);
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
          SearchQuery(POP_MOVIE);


            return true;
        }else if(itemThatWasClickedId==R.id.sortby_toprated){
            arrayList.clear();

        SearchQuery(TOP_RATED);
        }
        return super.onOptionsItemSelected(item);
    }

    private void SearchQuery(String sort) {
        URL SearchUrl = Networkutils.buildUrl(sort);

        new DownloadTask().execute(SearchUrl);

    }
    @Override
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();
        Intent i = new Intent(MainActivity.this, MainActivity.class);
        startActivity(i);
        finish();

    }

}
