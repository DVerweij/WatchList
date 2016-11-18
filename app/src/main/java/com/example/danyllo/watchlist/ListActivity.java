package com.example.danyllo.watchlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class ListActivity extends AppCompatActivity {

    SharedPreferences prefs;

    private ListView addedMovies;
    private ArrayList<Pair<String, JSONObject>> movieObjects = new ArrayList<Pair<String, JSONObject>>();
    private ArrayList<String> addedList = new ArrayList<String>();
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
            addedMovies = (ListView) findViewById(R.id.addedMovies);
            prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
            listAdapter = new ArrayAdapter<String>(this, simple_list_item_1, addedList);
            addedMovies.setAdapter(listAdapter);
            addedMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Object titleObj = addedMovies.getItemAtPosition(position);
                    String titleString = (String) titleObj;
                    goToTitlePage(titleString);
                }
            });
    }

    //Function to stop refresh at rotation.
    // http://stackoverflow.com/questions/5123407/losing-data-when-rotate-screen
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    private void goToTitlePage(String titleString) {
        SharedPreferences.Editor edit = prefs.edit();
        String toSend = "";
        for (int i = 0; i < movieObjects.size(); i++) {
            if (movieObjects.get(i).getKey().equals(titleString)) {
                toSend = movieObjects.get(i).getValue().toString();
                break;
            }
        }
        edit.putString("movie", toSend);
        edit.apply();
        Intent MovActivity = new Intent(this, MovieActivity.class);
        startActivity(MovActivity);
    }
}
