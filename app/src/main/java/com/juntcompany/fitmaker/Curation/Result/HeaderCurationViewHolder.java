package com.juntcompany.fitmaker.Curation.Result;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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


    ImageView image_picture;
    TextView text_type_name, text_type_info;
    CurationType type;
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
        image_picture = (ImageView)itemView.findViewById(R.id.image_picture);
        text_type_name = (TextView)itemView.findViewById(R.id.text_type_name);
        text_type_info = (TextView)itemView.findViewById(R.id.text_type_info);
    }

        public void setData(Object data){
            image_picture.setImageResource(Integer.valueOf(type.type_picture));
            text_type_name.setText(type.type_name);
            text_type_info.setText(type.type_info);
        }

}
