package com.example.danyllo.watchlist;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class MovieActivity extends AppCompatActivity {

    SharedPreferences prefs;
    private ListView movieList;
    private ArrayList<String> detailList = new ArrayList<String>();
    private ImageView poster;
    private ArrayAdapter<String> filmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        String movie = prefs.getString("movie", "");
        Log.d("WOW", movie);
        movieList = (ListView) findViewById(R.id.listView);
        poster = (ImageView) findViewById(R.id.imageView);
        filmAdapter = new ArrayAdapter<String>(this, simple_list_item_1, detailList);
        movieList.setAdapter(filmAdapter);
        try {
            JSONObject movieJson = new JSONObject(movie);
            detailList.add(movieJson.getString("Title"));
            detailList.add(movieJson.getString("Year"));
            detailList.add(movieJson.getString("imdbID"));
            Log.d("TAG", detailList.get(0));
            filmAdapter.clear();
            filmAdapter.addAll(detailList);
            filmAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
