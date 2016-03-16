package com.juntcompany.fitmaker.Friend.request;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juntcompany.fitmaker.Data.Friend;
import com.juntcompany.fitmaker.R;

/**
 * Created by EOM on 2016-02-26.
 */
public class FriendRequestViewHolder extends RecyclerView.ViewHolder {

    TextView textName, text_accept, text_reject;
    ImageView imageProfile;
    Friend friend;
    Context mContext;

//    public OnItemClickListener itemClickListener;
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        itemClickListener = listener;
//    }
    public interface OnItemSelectClickListener {
        public void onAdapterItemAcceptClick(View view, int position);
        public void onAdapterItemRejectClick(View view, int position);
    }

    OnItemSelectClickListener mAdapterClickListener;
    public void setOnItemClickListener(OnItemSelectClickListener listener){
        mAdapterClickListener = listener;
    }


    Button rejectView, acceptView;

    public FriendRequestViewHolder(final View itemView) {
        super(itemView);

        rejectView = (Button)itemView.findViewById(R.id.btn_reject);
        acceptView = (Button)itemView.findViewById(R.id.btn_accept);
        acceptView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapterClickListener != null) {
                    mAdapterClickListener.onAdapterItemAcceptClick(itemView, getAdapterPosition());
                }
            }
        });
        rejectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapterClickListener != null) {
                    mAdapterClickListener.onAdapterItemRejectClick(itemView,getAdapterPosition());
                }
            }
        });
        mContext = itemView.getContext();
        textName = (TextView) itemView.findViewById(R.id.text_name);
//        text_accept = (TextView) itemView.findViewById(R.id.text_accept);
//        text_reject = (TextView) itemView.findViewById(R.id.text_reject);
        imageProfile = (ImageView) itemView.findViewById(R.id.image_profile);

    }

    public void setData(Friend friend) {
        this.friend = friend;
        textName.setText(friend.friendName);

        if (!TextUtils.isEmpty(friend.friendProfile)) {
            Glide.with(mContext).load(friend.friendProfile).into(imageProfile);
        } else {
            imageProfile.setImageResource(R.drawable.default_friend);
        }
    }
}