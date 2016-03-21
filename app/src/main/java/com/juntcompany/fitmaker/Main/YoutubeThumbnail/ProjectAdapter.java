package com.juntcompany.fitmaker.Main.YoutubeThumbnail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.juntcompany.fitmaker.Data.Project;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-03-17.
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectViewHolder> implements OnItemClickListener {

    //main에서 게임 플레이스 contentDialog 와 콘텐츠와 형식 모두 똑같고 view만 다르면 어댑터에 담는 viewHolder 만 다르게 하면 될듯

    List<Project> items = new ArrayList<Project>();

    public void add(Project project){
        items.add(project);
        notifyDataSetChanged();
    }

    public void addAll(List<Project> projects){
        items.addAll(projects);
        notifyDataSetChanged();
    }


    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_project, parent, false);
        ProjectViewHolder holder = new ProjectViewHolder(view);
        holder.setOnItemClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        holder.setData(items.get(position), position);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }
    @Override
    public void onItemClick(View view, int position) {
        if(itemClickListener != null){
            itemClickListener.onItemClick(view, position);
        }
    }

    public Project getItem(int position) {
        if (position < 0 || position >= items.size()) return null;

        return items.get(position);
    }
}
