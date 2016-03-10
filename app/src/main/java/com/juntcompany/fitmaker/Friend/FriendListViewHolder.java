package com.juntcompany.fitmaker.Friend;

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
public class FriendListViewHolder extends RecyclerView.ViewHolder{

    TextView textName, textHour;
    ImageView imageProfile;
    Button btnFriendProfile;
    Friend friend;
    Context mContext;

    public interface OnItemSelectorClickListener{
        public void onAdapterItemProfileClick(View view, int position);
    }

    public OnItemSelectorClickListener mAdapterClickListener;
    public void setOnItemClickListener (OnItemSelectorClickListener listener){
        mAdapterClickListener = listener;
    }


    public FriendListViewHolder(View itemView) {
        super(itemView);

        btnFriendProfile = (Button)itemView.findViewById(R.id.btn_friend_profile);
        btnFriendProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapterClickListener != null) {
                    mAdapterClickListener.onAdapterItemProfileClick(v, getAdapterPosition());
                }
            }
        });

        mContext = itemView.getContext();
        textName = (TextView)itemView.findViewById(R.id.text_name);
//        textHour = (TextView)itemView.findViewById(R.id.text_hour);
        imageProfile = (ImageView)itemView.findViewById(R.id.image_profile);

    }

    public void setData(Friend friend){
        this.friend = friend;
        textName.setText(friend.friendName);
        //textHour.setText("" + friend.friendExerciseHour);
        if(!TextUtils.isEmpty(friend.friendProfile)){
            Glide.with(mContext).load(friend.friendProfile).into(imageProfile);
        }else {
            imageProfile.setImageResource(R.mipmap.fit_logo);
        }
    }
}
