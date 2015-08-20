package com.codepath.apps.simpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.simpletweets.EndlessScrollListener;
import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TweetArrayAdapter;
import com.codepath.apps.simpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public abstract class TweetsListFragment extends Fragment
{

    protected List<Tweet> tweets;

    protected TweetArrayAdapter tweets_adapter;

    protected ListView tweets_view;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tweets_list, container, false);

        tweets_view = (ListView) view.findViewById(R.id.lvTweets);

        tweets_view.setAdapter(tweets_adapter);

        tweets_view.setOnScrollListener(new EndlessScrollListener() {

            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMore(page);
            }

        });

        return view;
    }

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        tweets = new ArrayList<>();

        tweets_adapter = new TweetArrayAdapter(getActivity(), tweets);

        populateTimeline();
    }

    protected abstract void populateTimeline ();

    protected abstract void loadMore (int page);

}
