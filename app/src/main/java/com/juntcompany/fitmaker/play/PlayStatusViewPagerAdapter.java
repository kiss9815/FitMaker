package com.juntcompany.fitmaker.play;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.juntcompany.fitmaker.Data.Content;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-03-15.
 */
public class PlayStatusViewPagerAdapter extends FragmentPagerAdapter {
    List<Content> items = new ArrayList<Content>();

    public PlayStatusViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void add(Content content){
        items.add(content);
        notifyDataSetChanged();
    }

    public void addAll(List<Content> contents){
        items.addAll(contents);
        notifyDataSetChanged();
    }

    private ArrayList<PlayStatusPagerFragment> mFragments = new ArrayList<PlayStatusPagerFragment>();

    public PlayStatusPagerFragment getFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        PlayStatusPagerFragment f = PlayStatusPagerFragment.newInstance(items.get(position));

        while (mFragments.size() <= position) {
            mFragments.add(null);
        }
        mFragments.set(position, f);
        return f;
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
