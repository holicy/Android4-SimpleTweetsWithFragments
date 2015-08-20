package com.codepath.apps.simpletweets;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.simpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ProfileActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final String target_screen_name = getIntent().getStringExtra("screen_name");

        if (target_screen_name == null) {
            TwitterApplication.getRestClient().getUserInfo(new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("DEBUG", response.toString());
                    User user = User.fromJSON(response);

                    updateProfileView(user);
                }

            });
        } else {
            TwitterApplication.getRestClient().getUserInfo(target_screen_name, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("DEBUG", response.toString());
                    User user = User.fromJSON(response);

                    updateProfileView(user);
                }

            });
        }

        if (savedInstanceState == null) {
            UserTimelineFragment fragment = UserTimelineFragment.newInstance(target_screen_name);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flUserTimelineContainer, fragment);
            ft.commit();
        }
    }

    private void updateProfileView (User user)
    {
        getSupportActionBar().setTitle("@" + user.getScreenName());

        TextView name = (TextView) findViewById(R.id.tvName);
        TextView tagline = (TextView) findViewById(R.id.tvTagline);
        ImageView profile_image = (ImageView) findViewById(R.id.ivProfileImage);
        TextView followings = (TextView) findViewById(R.id.tvFollowings);
        TextView followers = (TextView) findViewById(R.id.tvFollowers);

        name.setText(user.getName());
        tagline.setText(user.getTagline());
        Picasso.with(ProfileActivity.this).load(user.getProfileImageUrl()).into(profile_image);

        switch (user.getFollowersCount()) {
            case 0:
                followers.setText("no follower");
                break;
            case 1:
                followers.setText("1 follower");
                break;
            default:
                followers.setText(user.getFollowersCount() + " followers");
        }
        switch (user.getFollowingsCount()) {
            case 0:
                followings.setText("no following");
                break;
            case 1:
                followings.setText("1 following");
                break;
            default:
                followings.setText(user.getFollowingsCount() + " followings");
        }
    }

}
