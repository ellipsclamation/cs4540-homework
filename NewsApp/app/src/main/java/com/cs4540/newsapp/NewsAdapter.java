package com.cs4540.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by seppc_laptop on 6/28/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private ArrayList<NewsItem> mNewsData;

    private final NewsAdapterOnClickHandler mClickHandler;

    public interface NewsAdapterOnClickHandler {
        void onClick(String newsArticle);
    }

    public NewsAdapter(NewsAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public final TextView mNewsTitleTextView;
        public final TextView mNewsDescriptionTextView;
        public final TextView mNewsTimeTextView;

        public NewsAdapterViewHolder (View view){
            super(view);
            mNewsTitleTextView = (TextView) view.findViewById(R.id.news_title);
            mNewsDescriptionTextView = (TextView) view.findViewById(R.id.news_description);
            mNewsTimeTextView = (TextView) view.findViewById(R.id.news_time);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            int adapterPosition = getAdapterPosition();
            String newsArticle = mNewsData.get(adapterPosition).getUrl();
            mClickHandler.onClick(newsArticle);
        }
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        String  newsTitle = mNewsData.get(position).getTitle();
        String  newsDescription = mNewsData.get(position).getDescription();
        String  newsTime = mNewsData.get(position).getPublishedAt();

        holder.mNewsTitleTextView.setText(newsTitle);
        holder.mNewsDescriptionTextView.setText(newsDescription);
        holder.mNewsTimeTextView.setText(newsTime);
    }

    @Override
    public int getItemCount() {
        if(mNewsData == null)
            return 0;
        return mNewsData.size();
    }

    public void setNewsData(ArrayList<NewsItem> newsData) {
        mNewsData = newsData;
        notifyDataSetChanged();
    }
}
