package com.juntcompany.fitmaker.Badge;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntcompany.fitmaker.Data.Badge;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.util.OnItemClickListener;

/**
 * Created by EOM on 2016-02-28.
 */
public class BadgeViewHolder extends RecyclerView.ViewHolder {

    TextView text_badge;
    ImageView image_badge;
    Badge badge;

    OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
            itemClickListener = listener;

    }

    public BadgeViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener !=null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        });

        text_badge = (TextView)itemView.findViewById(R.id.text_badge);
        image_badge = (ImageView)itemView.findViewById(R.id.image_badge);
    }

    public void setData(Badge badge){
//        text_badge.setText(badge.badge_text);
        if(!TextUtils.isEmpty(badge.badge_image)) {

        }else {
            image_badge.setImageResource(Integer.parseInt(String.valueOf(R.mipmap.ic_launcher)));
        }
    }
}
