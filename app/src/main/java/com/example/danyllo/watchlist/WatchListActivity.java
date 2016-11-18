package com.example.danyllo.watchlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_list_item_1;

public class WatchListActivity extends AppCompatActivity {

    private EditText searchET;
    private ListView movieList;
    private ArrayList<String> titleList = new ArrayList<String>();
    private Map<String, JSONObject> movieJson = new HashMap<String, JSONObject>();
    private ArrayAdapter<String> movAdapter;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);
        prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        searchET = (EditText) findViewById(R.id.editText);
        movieList = (ListView) findViewById(R.id.movieList);
        movAdapter = new ArrayAdapter<String>(this, simple_list_item_1, titleList);
        movieList.setAdapter(movAdapter);
        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Object titleObj = movieList.getItemAtPosition(position);
                    String titleString = (String) titleObj;
                    goToTitlePage(titleString);
                }
        });
    }

    private void goToTitlePage(String titleString) {
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("movie", movieJson.get(titleString).toString());
        edit.apply();
        Intent MovActivity = new Intent(this, MovieActivity.class);
        startActivity(MovActivity);
    }


    public void searchMovie(View view) {
        String movie = searchET.getText().toString().trim();
        AppSyncTasks task = new AppSyncTasks(this, "http://www.omdbapi.com/?");
        task.execute(movie);
        Log.d("EXEC", movie);
    }

    public void clickIt(View view) {
        Log.d("CLICKED", "click");
    }

    public void setData(ArrayList<String> titles, Map<String, JSONObject> movieMap) {
        titleList = titles;
        movieJson = movieMap;
        movAdapter.clear();
        movAdapter.addAll(titleList);
        movAdapter.notifyDataSetChanged();
    }
}
