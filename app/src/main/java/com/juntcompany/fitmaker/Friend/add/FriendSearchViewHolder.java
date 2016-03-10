package com.juntcompany.fitmaker.Friend.add;

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
 * Created by EOM on 2016-03-10.
 */
public class FriendSearchViewHolder extends RecyclerView.ViewHolder {

    TextView textEmail;
    ImageView imageProfile;
    Friend friend;
    Context mContext;
    Button btnAddFriend;

    public interface OnItemSelectClickListener {
        public void onAdapterItemAddClick(View view, int position);
    }

    OnItemSelectClickListener mAdapterClickListener;
    public void setOnItemClickListener(OnItemSelectClickListener listener){
        mAdapterClickListener = listener;
    }



    public FriendSearchViewHolder(final View itemView) {
        super(itemView);


        btnAddFriend = (Button)itemView.findViewById(R.id.btn_add_friend);
        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapterClickListener != null) {
                    mAdapterClickListener.onAdapterItemAddClick(itemView, getAdapterPosition());
                }
            }
        });
        mContext = itemView.getContext();
        textEmail = (TextView) itemView.findViewById(R.id.text_email);
//        text_accept = (TextView) itemView.findViewById(R.id.text_accept);
//        text_reject = (TextView) itemView.findViewById(R.id.text_reject);
        imageProfile = (ImageView) itemView.findViewById(R.id.image_profile);

    }

    public void setData(Friend friend) {
        this.friend = friend;
        textEmail.setText(friend.friendEmail);

        if (!TextUtils.isEmpty(friend.friendProfile)) {
            Glide.with(mContext).load(friend.friendProfile).into(imageProfile);
        } else {
            imageProfile.setImageResource(R.mipmap.ic_launcher);
        }
    }
}
