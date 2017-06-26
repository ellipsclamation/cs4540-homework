package com.cs4540.newsapp.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by seppc_laptop on 6/21/2017.
 */

public class NetworkUtils {
// TODO   api key
    private static final String BASE_URL = "https://newsapi.org/v1/articles";

    final static String QUERY_PARAM = "source";

    final static String PARAM_SORT = "sort";
    final static String sortBy = "latest";
    final static String apiKey = "apiKey";

    final static String key = "aaf358e7f6e844948361a06b15c5903d";

    public static URL buildUrl(String newsQuery) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, newsQuery)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .appendQueryParameter(apiKey, key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);
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
