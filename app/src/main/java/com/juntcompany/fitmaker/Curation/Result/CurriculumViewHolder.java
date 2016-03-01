package com.juntcompany.fitmaker.Curation.Result;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.juntcompany.fitmaker.Data.Curriculum;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.util.OnItemClickListener;

/**
 * Created by EOM on 2016-03-01.
 */
public class CurriculumViewHolder extends RecyclerView.ViewHolder{


    OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        itemClickListener = listener;

    }

    ImageView image_picture;
    Curriculum curriculum;

    public CurriculumViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        });

        image_picture = (ImageView)itemView.findViewById(R.id.image_picture);
    }

    public void setData(Curriculum curriculum){
        if(!TextUtils.isEmpty(curriculum.curriculum_image)) {

        }else {
            image_picture.setImageResource(Integer.parseInt(String.valueOf(R.mipmap.ic_launcher)));
        }
    }
}
