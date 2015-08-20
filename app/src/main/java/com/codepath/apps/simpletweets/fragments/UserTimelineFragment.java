package com.codepath.apps.simpletweets.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserTimelineFragment extends TweetsListFragment
{

    public static UserTimelineFragment newInstance (String screen_name)
    {
        UserTimelineFragment fragment = new UserTimelineFragment();

        Bundle bundle = new Bundle();

        bundle.putString("screen_name", screen_name);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected void populateTimeline() {
        String screen_name = getArguments().getString("screen_name");

        TwitterApplication.getRestClient().getUserTimeline(screen_name, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUG", response.toString());
                tweets_adapter.addAll(Tweet.fromJSONArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }

        });
    }

    @Override
    protected void loadMore(int page) {
        Log.i("DEBUG", "count: " + tweets.size());
        Log.i("DEBUG", "page: " + page);

        if (tweets.size() < 10 * (page - 1)) {
            return;
        }

        String screen_name = getArguments().getString("screen_name");
        TwitterApplication.getRestClient().getUserTimeline(screen_name, tweets.get(tweets.size() - 1).getUid() - 1, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUG", response.toString());

                tweets_adapter.addAll(Tweet.fromJSONArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }

        });
    }
}
