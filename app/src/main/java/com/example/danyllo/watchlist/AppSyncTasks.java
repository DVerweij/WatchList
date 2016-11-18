package com.example.danyllo.watchlist;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Danyllo on 18-11-2016.
 */

public class AppSyncTasks extends AsyncTask<String, Integer, String>{
    Context context;
    WatchListActivity activity;
    private String apiURL;

    // Constructor
    public AppSyncTasks(WatchListActivity activity, String api) {
        this.activity = activity;
        this.context = this.activity.getApplicationContext();
        apiURL = api;
        Log.d("CONS", apiURL);
    }

    @Override
    protected String doInBackground(String... params) {
        HttpParser apiParser = new HttpParser(apiURL);
        try {
            return apiParser.extractFromURL(params);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    protected void onPreExecute(){
        Toast preexe = Toast.makeText(context, "Acquiring data", Toast.LENGTH_LONG);
        preexe.show();
    }

    protected void onPostExecute(String result){
        super.onPostExecute(result);

        if(result.length() == 0) {
            Toast noData = Toast.makeText(context, "No data was found", Toast.LENGTH_LONG);
            noData.show();
        } else {
            ArrayList<String> titles = new ArrayList<String>();
            Map<String, JSONObject> movieMap = new HashMap<String, JSONObject>();
            try {
                JSONObject json = new JSONObject(result);
                JSONArray listofmovies = json.getJSONArray("Search");
                for(int i=0; i < listofmovies.length(); i++) {
                    JSONObject jsonObj = listofmovies.getJSONObject(i);
                    titles.add(jsonObj.getString("Title"));
                    movieMap.put(jsonObj.getString("Title"), jsonObj);
                }
                Log.d("LOL", listofmovies.getJSONObject(0).getString("Title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.activity.setData(titles, movieMap);
        }
    }
}
