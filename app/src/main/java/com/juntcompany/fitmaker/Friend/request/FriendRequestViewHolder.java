package com.juntcompany.fitmaker.Friend.request;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntcompany.fitmaker.Data.Friend;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.util.OnItemClickListener;

/**
 * Created by EOM on 2016-02-26.
 */
public class FriendRequestViewHolder extends RecyclerView.ViewHolder {

    TextView text_name, text_accept, text_reject;
    ImageView image_profile;
    Friend friend;

    public OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    public FriendRequestViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        });

        text_name = (TextView) itemView.findViewById(R.id.text_name);
//        text_accept = (TextView) itemView.findViewById(R.id.text_accept);
//        text_reject = (TextView) itemView.findViewById(R.id.text_reject);
        image_profile = (ImageView) itemView.findViewById(R.id.image_profile);

    }

    public void setData(Friend friend) {
        this.friend = friend;
        text_name.setText(friend.user_name);

        if (!TextUtils.isEmpty(friend.user_image)) {

        } else {
            image_profile.setImageResource(R.mipmap.ic_launcher);
        }
    }
}