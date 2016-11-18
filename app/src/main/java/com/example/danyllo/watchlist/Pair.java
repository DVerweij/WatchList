package com.example.danyllo.watchlist;

import org.json.JSONException;

/**
 * Created by Danyllo on 18-11-2016.
 */

public class Pair<String, JSONObject> {
    public final String str;
    public final JSONObject json;
    public Pair(String str, JSONObject json) {
        this.str = str;
        this.json = json;
    }
    public String getKey() {
        return str;
    }
    public JSONObject getValue() {
        return json;
    }
}
