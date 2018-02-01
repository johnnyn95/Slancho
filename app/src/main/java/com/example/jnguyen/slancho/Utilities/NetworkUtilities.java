package com.example.jnguyen.slancho.Utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by JNguyen on 30.1.2018.
 */

public final class NetworkUtilities {
 //TODO implement Network Utilities

    private static final String TAG = NetworkUtilities.class.getSimpleName();


    private static final String format = "json";
    private static final String units = "metric";
    private static final int numDays = 5;

    private static final String API_KEY = "80bb668417181903a5fcd4b7b4dd0910";
    private static final String BASE_URL = "api.openweathermap.org/data/2.5/weather";

    final static String QUERY_PARAM = "?q=";
    final static String QUERY_API = "&appid=";

    final static String LAT_PARAM = "lat";
    final static String LON_PARAM = "lon";
    final static String FORMAT_PARAM = "mode";
    final static String UNITS_PARAM = "units";
    final static String DAYS_PARAM = "cnt";

    private static final String DUMMY_WEATHER_URL = "http://samples.openweathermap.org/data/2.5/forecast?q=M%C3%BCnchen,DE&appid=b6907d289e10d714a6e88b30761fae22";

    public static URL buildURLforFiveDays(String location){
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM,location)
                .appendQueryParameter(QUERY_API,API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.v(TAG,"Built URL" + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}