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

    TextView textName, text_friend_State;
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
        textName = (TextView) itemView.findViewById(R.id.text_friend_name);
        text_friend_State = (TextView) itemView.findViewById(R.id.text_friend_State);

        imageProfile = (ImageView) itemView.findViewById(R.id.image_profile);

    }

    public void setData(Friend friend) {
        this.friend = friend;
        textName.setText(friend.friendName);
        if(friend.friendState == 1){
            text_friend_State.setText("이미 친구입니다");
            btnAddFriend.setBackgroundResource(R.drawable.ic_navigation_check);
            btnAddFriend.setSelected(false);
        }else if(friend.friendState == 0){
            text_friend_State.setText("요청 중 입니다");
            btnAddFriend.setBackgroundResource(R.drawable.ic_navigation_check);
            btnAddFriend.setSelected(false);
        }else if(friend.friendState == -1){
            text_friend_State.setText("거절했습니다.");
        }else if(friend.friendState == 2){
            text_friend_State.setText("친구 합시다");
            text_friend_State.setTextColor(android.R.color.black);
        }

        if (!TextUtils.isEmpty(friend.friendProfile)) {
            Glide.with(mContext).load(friend.friendProfile).into(imageProfile);
        } else {
            imageProfile.setImageResource(R.drawable.default_friend);
        }
    }
}
