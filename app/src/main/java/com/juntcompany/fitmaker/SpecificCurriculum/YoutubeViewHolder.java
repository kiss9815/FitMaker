package com.juntcompany.fitmaker.SpecificCurriculum;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juntcompany.fitmaker.Data.Content;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.util.OnItemClickListener;

/**
 * Created by EOM on 2016-03-12.
 */
public class YoutubeViewHolder extends RecyclerView.ViewHolder {


    public OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        itemClickListener =listener;
    }

    ImageView imageYoutube;
    TextView textName, textExercisePart;
    Context mContext;
    public YoutubeViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        });
        mContext = itemView.getContext();
        imageYoutube = (ImageView)itemView.findViewById(R.id.image_youtube);
        textName = (TextView)itemView.findViewById(R.id.text_content_name);
        textExercisePart = (TextView)itemView.findViewById(R.id.text_content_benefit_part);

    }


    public void setData(Content content){
        Glide.with(mContext).load(content.contentYoutubeThumbNail).into(imageYoutube);
        textName.setText(content.contentName);
        textExercisePart.setText(content.contentBenefitPart);
    }
}
