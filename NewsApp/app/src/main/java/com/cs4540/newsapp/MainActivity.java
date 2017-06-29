package com.cs4540.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cs4540.newsapp.NewsAdapter.NewsAdapterOnClickHandler;
import com.cs4540.newsapp.utilities.NetworkUtils;
import com.cs4540.newsapp.utilities.NewsJsonUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_news);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mNewsAdapter = new NewsAdapter(this);

        mRecyclerView.setAdapter(mNewsAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadNewsData();
    }

    private void loadNewsData(){
        showNewsDataView();

        String source = "the-next-web";
        new FetchNewsTask().execute(source);
    }

    private void showNewsDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(String newsArticle) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsArticle));
        startActivity(browserIntent);
    }

    public class FetchNewsTask extends AsyncTask<String, Void, ArrayList<NewsItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<NewsItem> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String source = params[0];

            URL newsRequestUrl = NetworkUtils.buildUrl(source);

            try {
                String jsonNewsResponse = NetworkUtils
                        .getResponseFromHttpUrl(newsRequestUrl);

                ArrayList<NewsItem> simpleJsonNewsData = NewsJsonUtils
                        .getSimpleNewsStringsFromJson(MainActivity.this, jsonNewsResponse);

                return simpleJsonNewsData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> newsData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (newsData != null) {
                showNewsDataView();
                mNewsAdapter.setNewsData(newsData);
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            mNewsAdapter.setNewsData(null);
            loadNewsData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
