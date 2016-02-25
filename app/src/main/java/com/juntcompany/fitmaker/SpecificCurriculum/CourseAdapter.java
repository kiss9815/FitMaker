package com.juntcompany.fitmaker.SpecificCurriculum;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.juntcompany.fitmaker.Data.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-02-21.
 */
public class CourseAdapter extends BaseAdapter{

    List<Course> items = new ArrayList<Course>();

//
//    private static final int VIEW_TYPE_COUNT = 2;
//    private static final int SUMMARY_VIEW = 0;
//    private static final int COURSE_VIEW = 1;

    public void add(Course course){
        items.add(course);
        notifyDataSetChanged();
    }

    public  void addAll(List<Course> items){
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
        CourseView view;
        if(convertView == null){
            view = new CourseView(parent.getContext());
        }else {
            view = (CourseView)convertView;
        }
       view.setData(items.get(position));
        return view;
    }
}
