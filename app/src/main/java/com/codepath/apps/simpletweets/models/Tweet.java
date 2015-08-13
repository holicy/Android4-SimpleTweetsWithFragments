package com.codepath.apps.simpletweets.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tweet
{

    private long id;

    private String body;

    private long uid;

    private String createdAt;

    private User user;

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public static Tweet fromJSON (JSONObject json_object)
    {
        Tweet result = new Tweet();
        try {
            result.body = json_object.getString("text");
            result.uid = json_object.getLong("id");
            result.createdAt = json_object.getString("created_at");
            result.user = User.fromJSON(json_object.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public static List<Tweet> fromJSONArray (JSONArray json_array)
    {
        ArrayList<Tweet> results = new ArrayList<>();

        for (int i = 0; i < json_array.length(); ++i) {
            try {
                JSONObject json_object = json_array.getJSONObject(i);
                Tweet tweet = fromJSON(json_object);
                if (tweet != null) {
                    results.add(tweet);
                }
            } catch (JSONException e ) {
                e.printStackTrace();
                continue;
            }
        }

        return results;
    }

}
