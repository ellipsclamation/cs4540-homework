package com.cs4540.newsapp.utilities;

import android.content.Context;

import com.cs4540.newsapp.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by seppc_laptop on 6/21/2017.
 */

public class NewsJsonUtils {

    public static ArrayList<NewsItem> getSimpleNewsStringsFromJson(Context context, String newsJsonStr)
            throws JSONException {

        final String NEWS_ARTICLES = "articles";

        final String NEWS_AUTHOR = "author";

        final String NEWS_TITLE = "title";

        final String NEWS_DESCRIPTION = "description";

        final String NEWS_URL = "url";

        final String NEWS_IMAGE = "urlToImage";

        final String NEWS_TIME = "publishedAt";

        final String NEWS_STATUS = "status";


        JSONObject newsJson = new JSONObject(newsJsonStr);

        JSONArray newsArray = newsJson.getJSONArray(NEWS_ARTICLES);

        ArrayList<NewsItem> newsItems = new ArrayList<>();

        for (int i = 0; i < newsArray.length(); i++) {
            String author;
            String title;
            String description;
            String url;
            String imageUrl;
            String publishedAt;

            JSONObject newsArticle = newsArray.getJSONObject(i);

            author = newsArticle.getString(NEWS_AUTHOR);
            title = newsArticle.getString(NEWS_TITLE);
            description = newsArticle.getString(NEWS_DESCRIPTION);
            url = newsArticle.getString(NEWS_URL);
            imageUrl = newsArticle.getString(NEWS_IMAGE);
            publishedAt = newsArticle.getString(NEWS_TIME);

            newsItems.add(new NewsItem(author, title, description, url, imageUrl, publishedAt));
        }

        return newsItems;
    }
}
