package com.example.danyllo.watchlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import static android.R.layout.simple_list_item_1;

public class WatchListActivity extends AppCompatActivity {

    private EditText searchET;
    private ListView movieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);
        searchET = (EditText) findViewById(R.id.editText);
        ArrayAdapter<String> movAdapter = new ArrayAdapter<String>(this, simple_list_item_1);
    }

    public void searchMovie(View view) {
        String movie = searchET.getText().toString().trim();
        AppSyncTasks task = new AppSyncTasks(this, "http://www.omdbapi.com/?");
        task.execute(movie);
    }
}
