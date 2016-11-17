package com.example.danyllo.watchlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Toast;

public class WatchListActivity extends AppCompatActivity {

    SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);
        searchBar = (SearchView) findViewById(R.id.searchBar);
    }
    public void searchTitle(View view) {
        String search = searchBar.getQuery().toString().trim();
        Toast test = Toast.makeText(this, "W0t", Toast.LENGTH_SHORT);
        test.show();
    }
}
