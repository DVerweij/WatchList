package com.example.danyllo.watchlist;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Danyllo on 18-11-2016.
 */

public class HttpParser {
    private static String api;
    private static final String extra = "&r=json";
    HttpParser(String url) {
        api = url;
    }
    static synchronized String extractFromURL(String... params) throws Exception{
        //return value
        String extract = "";

        //get movie title
        String movie = params[0];
        if (movie.length() < 2) {
            /*Toast tooShort = Toast.makeText(this, "More than one character please", Toast.LENGTH_LONG);
            tooShort.show();*/ //doesn't work
            return "";
        }
        String url = api + "s=" + movie + extra;
        Log.d("URL", url);

        //get url and establish connection
        URL extractURL = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) extractURL.openConnection();
        connection.setRequestMethod("GET");

        Integer code = connection.getResponseCode();
        if (200 <= code && code <= 299) {
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                extract = extract + line;
            }
        } else {
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(connection.getErrorStream()));

        }
        Log.d("RESULT",extract);
        return extract;
    }
}
