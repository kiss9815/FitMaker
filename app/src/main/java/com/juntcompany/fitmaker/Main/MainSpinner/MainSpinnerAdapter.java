package com.juntcompany.fitmaker.Main.MainSpinner;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.juntcompany.fitmaker.Data.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-03-04.
 */
public class MainSpinnerAdapter extends BaseAdapter {
    List<Project> items = new ArrayList<Project>();

    public void insert(Project project, int index) {
            if (project != null) {
                items.add(index, project);
            } else {
                items.add(index, project);
            }

        notifyDataSetChanged();
    }

    public void remove(Project project) {

            if (project != null) {
                items.remove(project);
            } else {
                items.remove(project);
            }

         notifyDataSetChanged();
    }

    public void add(Project p) {
        items.add(p);
        notifyDataSetChanged();
    }

    public void addAll(List<Project> projects){
        items.addAll(projects);
        notifyDataSetChanged();
    }

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    public List<Project> getItems(){

        return new ArrayList<>(items);
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
        MainSpinnerView spinnerView;
        if (convertView == null) {
            spinnerView = new MainSpinnerView(parent.getContext());
        } else {
            spinnerView = (MainSpinnerView)convertView;
        }
            spinnerView.setData(items.get(position));
        return spinnerView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        MainSpinnerView spinnerView;
        if (convertView == null) {
            spinnerView = new MainSpinnerView(parent.getContext());
        } else {
            spinnerView = (MainSpinnerView)convertView;
        }
        spinnerView.setData(items.get(position));

        return spinnerView;
    }
}
