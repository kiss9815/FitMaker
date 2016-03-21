package com.juntcompany.fitmaker.Curation.Result;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    TextView textName;
    ImageView image_picture, imageLevel;
    Curriculum curriculum;
    Context mContext;

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
        mContext = itemView.getContext();
        image_picture = (ImageView)itemView.findViewById(R.id.image_picture);
        imageLevel = (ImageView)itemView.findViewById(R.id.image_level);
        textName = (TextView)itemView.findViewById(R.id.text_name);
    }

    public void setData(Curriculum curriculum){
        textName.setText(curriculum.curriculumName);
        if(!TextUtils.isEmpty(curriculum.curriculum_image)) {
            Glide.with(mContext).load(curriculum.curriculum_image).into(image_picture);
        }

        if(curriculum.curriculum_level.equals("초급")){
            imageLevel.setImageResource(R.drawable.ic_curriculum_level_1);
        }else if(curriculum.curriculum_level.equals("중급")){
            imageLevel.setImageResource(R.drawable.ic_curriculum_level_2);
        }else if(curriculum.curriculum_level.equals("상급")){
            imageLevel.setImageResource(R.drawable.ic_curriculum_level_3);
        }
    }
}
