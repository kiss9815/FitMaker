package com.juntcompany.fitmaker.SpecificCurriculum;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.util.OnItemClickListener;

/**
 * Created by EOM on 2016-03-12.
 */
public class HeaderYoutubeViewHolder extends RecyclerView.ViewHolder{


    TextView textTitle;
    public HeaderYoutubeViewHolder(View itemView) {
        super(itemView);

        textTitle = (TextView)itemView.findViewById(R.id.text_title);

    }

    public void setData(String string){
        textTitle.setText(string);
    }


}
