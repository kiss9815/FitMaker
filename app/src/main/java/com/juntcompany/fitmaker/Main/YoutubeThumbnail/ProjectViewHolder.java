package com.juntcompany.fitmaker.Main.YoutubeThumbnail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.juntcompany.fitmaker.Data.Content;
import com.juntcompany.fitmaker.Data.Project;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.util.OnItemClickListener;

/**
 * Created by EOM on 2016-03-17.
 */
public class ProjectViewHolder extends RecyclerView.ViewHolder {

    TextView textName;

    Project project;

    public OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }


    public ProjectViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        });
        textName = (TextView) itemView.findViewById(R.id.text_name);


    }

    public void setData(Project project,int position) {
        this.project = project;

        if(position%2 == 1){
            itemView.setBackgroundResource(R.color.FitGray);
        }else {
            itemView.setBackgroundResource(R.color.FitGray_light);
        }
        textName.setText(project.projectName);

    }
}