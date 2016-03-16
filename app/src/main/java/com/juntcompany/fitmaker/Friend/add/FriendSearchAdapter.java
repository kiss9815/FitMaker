package com.juntcompany.fitmaker.Friend.add;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juntcompany.fitmaker.Data.Friend;
import com.juntcompany.fitmaker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-03-10.
 */
public class FriendSearchAdapter extends RecyclerView.Adapter<FriendSearchViewHolder> implements FriendSearchViewHolder.OnItemSelectClickListener{

    List<Friend> items = new ArrayList<Friend>();

    public void add(Friend friend){
        items.add(friend);
        notifyDataSetChanged();
    }

    public void addAll(List<Friend> friends){
        items.addAll(friends);
        notifyDataSetChanged();
    }

    public void clear(){
        items.clear();

    }

    @Override
    public FriendSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_search_friend, parent, false);
        FriendSearchViewHolder holder = new FriendSearchViewHolder(view);
        holder.setOnItemClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(FriendSearchViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public Friend getItem(int position){
        if (position < 0 || position >= items.size()) return null;

        return items.get(position);
    }

    public interface OnAdapterItemClickListener {
        public void onAdapterItemAddClick(View view, int position);
    }

    OnAdapterItemClickListener mAdapterClickListener;
    public void setOnItemClickListener(OnAdapterItemClickListener listener){
        mAdapterClickListener = listener;
    }

    @Override
    public void onAdapterItemAddClick(View view, int position) {
        if(mAdapterClickListener!=null){
            mAdapterClickListener.onAdapterItemAddClick(view, position);
        }
    }


}
