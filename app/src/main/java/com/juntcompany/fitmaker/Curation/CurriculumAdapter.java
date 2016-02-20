package com.juntcompany.fitmaker.Curation;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.juntcompany.fitmaker.Data.Curriculum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-02-20.
 */
public class CurriculumAdapter extends BaseAdapter {

    List<Curriculum> items = new ArrayList<Curriculum>();

    public void add(Curriculum curriculum){
        items.add(curriculum);
        notifyDataSetChanged();
    }

    public void addAll(List<Curriculum> items){
        this.items.addAll(items);
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
        CurriculumView view;
        if(convertView == null){
            view = new CurriculumView(parent.getContext());
        }else {
            view = (CurriculumView)convertView;
        }
        view.setData(items.get(position));
        return view;
    }
}
