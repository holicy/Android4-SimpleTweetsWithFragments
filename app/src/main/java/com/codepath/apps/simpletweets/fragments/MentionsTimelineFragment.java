package com.codepath.apps.simpletweets.fragments;

import android.util.Log;

import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class MentionsTimelineFragment extends TweetsListFragment
{

    @Override
    protected void populateTimeline() {
        TwitterApplication.getRestClient().getMentionsTimeline(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response)
            {
                Log.d("DEBUG", response.toString());
                tweets_adapter.addAll(Tweet.fromJSONArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
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

        TwitterApplication.getRestClient().getMentionsTimeline(tweets.get(tweets.size() - 1).getUid() - 1, new JsonHttpResponseHandler() {

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
