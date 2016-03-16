package com.juntcompany.fitmaker.Friend.request;

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
public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestViewHolder> implements FriendRequestViewHolder.OnItemSelectClickListener {

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
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public FriendRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_request_friend, parent, false);
        FriendRequestViewHolder holder = new FriendRequestViewHolder(view);
        holder.setOnItemClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(FriendRequestViewHolder holder, int position) {
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




    public Friend getItem(int position){
        if (position < 0 || position >= items.size()) return null;

        return items.get(position);
    }

    public interface OnAdapterItemClickListener {
        public void onAdapterItemAcceptClick(View view, int position);
        public void onAdapterItemRejectClick(View view, int position);
    }

    OnAdapterItemClickListener mAdapterClickListener;
    public void setOnItemClickListener(OnAdapterItemClickListener listener){
        mAdapterClickListener = listener;
    }

    @Override // viewHolder에서 아이템을 클릭했을 경우, viewHolder에서 accept와 reject이 나누어짐
    public void onAdapterItemAcceptClick(View view, int position) {
        if(mAdapterClickListener!=null){
            mAdapterClickListener.onAdapterItemAcceptClick(view, position);
        }
    }

    @Override
    public void onAdapterItemRejectClick(View view, int position) {
        if(mAdapterClickListener!=null){
            mAdapterClickListener.onAdapterItemRejectClick(view, position);
        }
    }
}
