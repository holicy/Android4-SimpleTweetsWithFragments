package com.codepath.apps.simpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.simpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.simpletweets.fragments.MentionsTimelineFragment;

public class TimelineActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timeline);

        ViewPager view_pager = (ViewPager) findViewById(R.id.viewpager);
        view_pager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));

        PagerSlidingTabStrip tab_strip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tab_strip.setViewPager(view_pager);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);

        return true;
    }

    public void onPost (MenuItem item)
    {
        Intent i = new Intent(this, PostActivity.class);
        startActivity(i);
    }

    public void onProfileView (MenuItem item)
    {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter
    {

        private String[] items = { "Home", "Mentions" };

        public TweetsPagerAdapter (FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem (int position)
        {
            switch (position) {
                case 0:
                    return new HomeTimelineFragment();
                case 1:
                    return new MentionsTimelineFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount()
        {
            return items.length;
        }

        @Override
        public CharSequence getPageTitle (int position)
        {
            return items[position];
        }

    }

}
