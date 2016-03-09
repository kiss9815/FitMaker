package com.juntcompany.fitmaker.Curation.Result;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juntcompany.fitmaker.Data.CurationType;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.util.OnHeaderClickListener;

/**
 * Created by EOM on 2016-03-01.
 */
public class HeaderCurationViewHolder extends RecyclerView.ViewHolder{

    OnHeaderClickListener mClickListener;
    public void setOnHeaderClickListener(OnHeaderClickListener listener){
        mClickListener = listener;
    }


    ImageView imagePicture;
    TextView textTypeName, textTypeInfo;
    CurationType type;
    Context mContext;

    public HeaderCurationViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onHeaderClick(v, getAdapterPosition());
                }
            }
        });
        mContext = itemView.getContext();
        imagePicture = (ImageView)itemView.findViewById(R.id.image_picture);
        textTypeName = (TextView)itemView.findViewById(R.id.text_type_name);
        textTypeInfo = (TextView)itemView.findViewById(R.id.text_type_info);
    }

        public void setData(CurationType type){

            textTypeName.setText(type.type_name);
            textTypeInfo.setText(type.type_info);
            if(!TextUtils.isEmpty(type.type_picture)){
                Glide.with(mContext).load(type.type_picture).into(imagePicture);
            }else {
                imagePicture.setImageResource(R.mipmap.fit_logo);
            }
        }

}
