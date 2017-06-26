package com.cs4540.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.cs4540.newsapp.utilities.NetworkUtils;
import com.cs4540.newsapp.utilities.NewsJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mNewsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsTextView = (TextView) findViewById(R.id.news_display);

        loadNewsData();
    }

    private void loadNewsData(){
        String source = "the-next-web";
        new FetchNewsTask().execute(source);
    }

    public class FetchNewsTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {

            String source = params[0];

            URL newsRequestUrl = NetworkUtils.buildUrl(source);

            try {
                String jsonNewsResponse = NetworkUtils
                        .getResponseFromHttpUrl(newsRequestUrl);

                String[] simpleJsonNewsData = NewsJsonUtils
                        .getSimpleNewsStringsFromJson(MainActivity.this, jsonNewsResponse);

                return simpleJsonNewsData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] newsData) {
            if (newsData != null) {
                for (String newsString : newsData) {
                    mNewsTextView.append((newsString) + "\n\n\n");
                }
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
            mNewsTextView.setText("");
            loadNewsData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
