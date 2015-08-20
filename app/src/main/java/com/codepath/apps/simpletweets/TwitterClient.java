package com.codepath.apps.simpletweets;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient
{

	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "DduBUeix9hN4nRPyTrsqLA";       // Change this
	public static final String REST_CONSUMER_SECRET = "gFMJz0M6Am6eQxjvXDGAayyAlg4fbVp0rtlMq3fXE"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}


	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
	public void getHomeTimeline (AsyncHttpResponseHandler handler)
	{
		String api_url = getApiUrl("statuses/home_timeline.json");

		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("since_id", 1);

		getClient().get(api_url, params, handler);
	}

    public void getHomeTimelineSince (long max_id, AsyncHttpResponseHandler handler)
    {
        String api_url = getApiUrl("statuses/home_timeline.json");

        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("since_id", 1);
        params.put("max_id", max_id);

        getClient().get(api_url, params, handler);
    }

	public void getMentionsTimeline (AsyncHttpResponseHandler handler)
	{
		String api_url = getApiUrl("statuses/mentions_timeline.json");

		RequestParams params = new RequestParams();
		params.put("count", 10);

		getClient().get(api_url, params, handler);
	}

    public void getMentionsTimeline (long max_id, AsyncHttpResponseHandler handler)
    {
        String api_url = getApiUrl("statuses/mentions_timeline.json");

        RequestParams params = new RequestParams();
        params.put("count", 10);
        params.put("max_id", max_id);

        getClient().get(api_url, params, handler);
    }

    public void getUserTimeline (String screen_name, AsyncHttpResponseHandler handler)
    {
        String api_url = getApiUrl("statuses/user_timeline.json");

        RequestParams params = new RequestParams();
        params.put("count", 10);
        params.put("screen_name", screen_name);

        getClient().get(api_url, params, handler);
    }

    public void getUserTimeline (String screen_name, long max_id, AsyncHttpResponseHandler handler)
    {
        String api_url = getApiUrl("statuses/user_timeline.json");

        RequestParams params = new RequestParams();
        params.put("count", 10);
        params.put("max_id", max_id);
        params.put("screen_name", screen_name);

        getClient().get(api_url, params, handler);
    }

    public void getUserInfo (AsyncHttpResponseHandler handler)
    {
        String api_url = getApiUrl("account/verify_credentials.json");

        getClient().get(api_url, null, handler);
    }

    public void getUserInfo (String screen_name, AsyncHttpResponseHandler handler)
    {
        String api_url = getApiUrl("users/show.json");

        RequestParams params = new RequestParams();
        params.put("screen_name", screen_name);

        getClient().get(api_url, params, handler);
    }

    public void postStatusUpdate (String status, AsyncHttpResponseHandler handler)
    {
        String api_url = getApiUrl("statuses/update.json");

        RequestParams params = new RequestParams();
        params.put("status", status);

        getClient().post(api_url, params, handler);
    }

}