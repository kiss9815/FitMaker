package com.juntcompany.fitmaker.Badge;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juntcompany.fitmaker.Data.Badge;
import com.juntcompany.fitmaker.Data.Friend;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-02-28.
 */
public class BadgeAdapter extends RecyclerView.Adapter<BadgeViewHolder> implements OnItemClickListener{

    List<Badge> items = new ArrayList<Badge>();

    public void add(Badge badge){
        items.add(badge);
        notifyDataSetChanged();
    }

    @Override
    public BadgeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_badge, parent, false);
        BadgeViewHolder holder = new BadgeViewHolder(view);
        holder.setOnItemClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(BadgeViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {

        return items.size();
    }


    @Override
    public void onItemClick(View view, int position) {

    }
    OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    public Badge getItem(int position){
        if (position < 0 || position >= items.size()) return null;

        return items.get(position);
    }

}
