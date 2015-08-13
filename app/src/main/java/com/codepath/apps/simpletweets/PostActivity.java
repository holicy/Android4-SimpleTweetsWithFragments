package com.codepath.apps.simpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class PostActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
    }

    public void onPost (View view)
    {
        EditText status = (EditText) findViewById(R.id.tvStatus);

        TwitterApplication.getRestClient().postStatusUpdate(status.getText().toString(), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess (int statusCode, Header[] headers, JSONObject response)
            {
                Log.d("DEBUG", response.toString());

                Intent i = new Intent(PostActivity.this, TimelineActivity.class);

                startActivity(i);
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
        getMenuInflater().inflate(R.menu.menu_post, menu);
        return true;
    }

}
