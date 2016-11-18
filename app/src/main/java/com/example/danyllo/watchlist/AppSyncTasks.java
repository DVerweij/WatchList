package com.example.danyllo.watchlist;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
    }

    @Override
    protected String doInBackground(String... params) {
        HttpParser apiParser = new HttpParser(apiURL);
        try {
            return apiParser.extractFromURL(params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPreExecute(){
        Toast preexe = Toast.makeText(context, "Getting data from server", Toast.LENGTH_LONG);
        preexe.show();
    }

    protected void onPostExecute(String result){
        super.onPostExecute(result);

        if(result.length() == 0) {
            Toast noData = Toast.makeText(context, "No data was found", Toast.LENGTH_LONG);
            noData.show();
        } else {
            try {
                JSONObject json = new JSONObject(result);
                Log.d("LOL", json.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
