package com.cs4540.newsapp.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by seppc_laptop on 6/21/2017.
 */

public class NewsJsonUtils {

    public static String[] getSimpleNewsStringsFromJson(Context context, String newsJsonStr)
            throws JSONException {

        final String NEWS_ARTICLES = "articles";

        final String NEWS_AUTHOR = "author";

        final String NEWS_TITLE = "title";

        final String NEWS_DESCRIPTION = "description";

        final String NEWS_URL = "url";

        final String NEWS_IMAGE = "urlToImage";

        final String NEWS_STATUS = "status";


        String[] parsedNewsData = null;

        JSONObject newsJson = new JSONObject(newsJsonStr);

        JSONArray newsArray = newsJson.getJSONArray(NEWS_ARTICLES);

        parsedNewsData = new String[newsArray.length()];

        for (int i = 0; i < newsArray.length(); i++) {
            String title;
            String description;

            JSONObject newsArticle = newsArray.getJSONObject(i);

            title = newsArticle.getString(NEWS_TITLE);
            description = newsArticle.getString(NEWS_DESCRIPTION);

            parsedNewsData[i] = title + "\n\n" + description;
        }

        return parsedNewsData;
    }
}
