package com.cs4540.githubreposearcher;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by seppc_laptop on 6/12/2017.
 */

public class NetworkUtils {

    public static final String GITHUB_BASE_URL = "http://api.github.com/search/repositories/";

    public static final String PARAM_QUERY = "Q";

    public static final String PARAM_SORT = "sort";

    public static URL makeURL(String searchQuery, String sortBy){
        Uri uri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, searchQuery)
                .appendQueryParameter(PARAM_SORT, sortBy).build();

        URL url = null;
        try{
            url = new URL(uri.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner input = new Scanner(in);

            input.useDelimiter("\\A");

            return (input.hasNext())? input.next():null;
        }finally{
            urlConnection.disconnect();
        }
    }

}
