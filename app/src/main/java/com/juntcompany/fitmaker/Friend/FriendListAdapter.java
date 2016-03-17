package com.juntcompany.fitmaker.Friend;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juntcompany.fitmaker.Data.Friend;
import com.juntcompany.fitmaker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-02-26.
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListViewHolder> implements FriendListViewHolder.OnItemSelectorClickListener {

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
        notifyDataSetChanged();
    }


    @Override
    public FriendListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_list_friend, parent, false);
        FriendListViewHolder holder = new FriendListViewHolder(view);
        holder.setOnItemClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(FriendListViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



//    OnItemClickListener itemClickListener;
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        itemClickListener = listener;
//    }
//    @Override
//    public void onItemClick(View view, int position) {
//        if(itemClickListener!=null){
//            itemClickListener.onItemClick(view, position);
//        }
//    }

    public interface OnAdapterItemClickListener {
        public void onAdapterItemProfileClick(View view, int position);
    }


    OnAdapterItemClickListener mAdapterClickListener;
    public void setOnItemClickListener(OnAdapterItemClickListener listener){
        mAdapterClickListener = listener;
    }

    @Override
    public void onAdapterItemProfileClick(View view, int position) {
        if(mAdapterClickListener != null){
            mAdapterClickListener.onAdapterItemProfileClick(view, position);
        }
    }



    public Friend getItem(int position){
        if (position < 0 || position >= items.size()) return null;

        return items.get(position);
    }

}
