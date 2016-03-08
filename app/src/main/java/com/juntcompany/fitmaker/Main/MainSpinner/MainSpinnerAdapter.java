package com.juntcompany.fitmaker.Main.MainSpinner;

import android.graphics.Color;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.juntcompany.fitmaker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-03-04.
 */
public class MainSpinnerAdapter extends BaseAdapter {
    List<String> items = new ArrayList<String>();

    public void add(String s) {
        items.add(s);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView == null) {
            tv = new TextView(parent.getContext());
        } else {
            tv = (TextView)convertView;
        }
        tv.setText(items.get(position));
        return tv;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView == null) {
            tv = new TextView(parent.getContext());
        } else {
            tv = (TextView)convertView;
        }
        tv.setText(items.get(position));
        tv.setBackgroundResource(R.color.colorActionBar);
        return tv;
    }
}
