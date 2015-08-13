package com.codepath.apps.simpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.simpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends ActionBarActivity {

    private TwitterClient client;
    private List<Tweet> tweets;
    private TweetArrayAdapter tweets_adapter;
    private ListView tweets_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        tweets_view = (ListView) findViewById(R.id.lvTweets);

        tweets = new ArrayList<>();

        tweets_adapter = new TweetArrayAdapter(this, tweets);

        tweets_view.setAdapter(tweets_adapter);
        tweets_view.setOnScrollListener(new EndlessScrollListener() {

            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                Log.i("DEBUG", "count: " + tweets.size());
                Log.i("DEBUG", "page: " + page);

                if (tweets.size() < 25 * (page - 1)) {
                    return;
                }

                client.getHomeTimelineSince(tweets.get(tweets.size() - 1).getUid(), new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess (int statusCode, Header[] headers, JSONArray response)
                    {
                        Log.d("DEBUG", response.toString());

                        tweets_adapter.addAll(Tweet.fromJSONArray(response));
                    }

                    @Override
                    public void onFailure (int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
                    {
                        Log.d("DEBUG", errorResponse.toString());
                    }

                });
            }

        });

        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    private void populateTimeline()
    {
        client.getHomeTimeline(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess (int statusCode, Header[] headers, JSONArray response)
            {
                Log.d("DEBUG", response.toString());

                tweets_adapter.addAll(Tweet.fromJSONArray(response));
            }

            @Override
            public void onFailure (int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
                Log.d("DEBUG", errorResponse.toString());
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_post) {
            Intent i = new Intent(TimelineActivity.this, PostActivity.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
