package com.juntcompany.fitmaker.Curation.Recommend;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.juntcompany.fitmaker.Data.Curriculum;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-03-03.
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendViewHolder> implements OnItemClickListener {

    List<Curriculum> items = new ArrayList<Curriculum>();


    public void add(Curriculum curriculum){
        items.add(curriculum);
        notifyDataSetChanged();
    }

    public void addAll(List<Curriculum> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }


    @Override
    public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

               View view = inflater.inflate(R.layout.view_curriculum, parent, false);
                RecommendViewHolder holder = new RecommendViewHolder(view);
                holder.setOnItemClickListener(this);
                return holder;

    }

    @Override
    public void onBindViewHolder(RecommendViewHolder holder, int position) {

            holder.setData(items.get(position));
        }




    @Override
    public int getItemCount() {
        return items.size();
    }



    @Override
    public void onItemClick(View view, int position) {
        if(itemClickListener !=null){
            itemClickListener.onItemClick(view, position);
        }
    }

    OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    public Curriculum getItem(int position){
        if (position < 0 || position >= items.size()) return null;

        return items.get(position);
    }


}
