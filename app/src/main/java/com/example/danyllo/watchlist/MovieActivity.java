package com.example.danyllo.watchlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class MovieActivity extends AppCompatActivity {

    SharedPreferences prefs;
    private ListView movieList;
    private ArrayList<String> detailList = new ArrayList<String>();
    private ImageView poster;
    private ArrayAdapter<String> filmAdapter;
    private JSONObject movieJson;
    private Button addOrRemove;
    private ArrayList<Pair<String, JSONObject>> addedMovies = new ArrayList<Pair<String, JSONObject>>();

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
        addOrRemove = (Button) findViewById(R.id.button4);
        //Doesn't print ListView for some reason
        if (savedInstanceState == null) {
            try {
                movieJson = new JSONObject(movie);
                String title = movieJson.getString("Title");
                detailList.add(title);
                detailList.add(movieJson.getString("Year"));
                detailList.add(movieJson.getString("imdbID"));
                Log.d("TAG", detailList.get(0));
                filmAdapter.clear();
                filmAdapter.addAll(detailList);
                filmAdapter.notifyDataSetChanged();
                //http://stackoverflow.com/questions/8992964/android-load-from-url-to-bitmap
                //setBitMap(movieJson.getString("Poster")); //doesn't work due to https
                Pair<String, JSONObject> pair = new Pair<String, JSONObject>(title, movieJson);
                if (addedMovies.contains(pair)) {
                    addOrRemove.setText("Remove from Watch List");
                } else {
                    addOrRemove.setText("Add to Watch List");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //Function to stop refresh at rotation.
    // http://stackoverflow.com/questions/5123407/losing-data-when-rotate-screen
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    protected void setBitMap(String url){
        try {
            //alternate code
            /*URL link = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) link.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap picture = BitmapFactory.decodeStream(input);*/
            InputStream input = new java.net.URL(url).openStream();
            Bitmap picture = BitmapFactory.decodeStream(input);
            poster.setImageBitmap(picture);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void AddOrRemove(View view) {

        try {
            String title = movieJson.getString("Title");
            Pair<String, JSONObject> pair = new Pair<String, JSONObject>(title, movieJson);
            if (addedMovies.contains(pair)) {
                addedMovies.remove(pair);
                addOrRemove.setText("Add to Watch List");
            } else {
                addedMovies.add(pair);
                addOrRemove.setText("Remove from Watch List");
            }
            //Couldn't convert the list to string or something that can be put in the editor
            /*SharedPreferences.Editor edit = prefs.edit();
            edit.putString("addedMovies", ObjectSerializer.serialize(addedMovies));
            edit.apply();*/
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void goToList(View view) {
        Intent listActivity = new Intent(this, ListActivity.class);
        startActivity(listActivity);
    }
}
