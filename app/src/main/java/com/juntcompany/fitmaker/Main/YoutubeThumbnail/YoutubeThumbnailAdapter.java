package com.juntcompany.fitmaker.Main.YoutubeThumbnail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juntcompany.fitmaker.Data.Content;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.SpecificCurriculum.HeaderYoutubeViewHolder;
import com.juntcompany.fitmaker.SpecificCurriculum.YoutubeViewHolder;
import com.juntcompany.fitmaker.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-03-17.
 */
public class YoutubeThumbnailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnItemClickListener {

    List<Content> items = new ArrayList<Content>();
    List<String> header = new ArrayList<String>();


    public void add(Content content){
        items.add(content);
        notifyDataSetChanged();
    }
    public void addAll(List<Content> contents){
        items.addAll(contents);
        notifyDataSetChanged();
    }

    public void addHeader(String string){
        header.add(string);
        notifyDataSetChanged();
    }

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 100;


    @Override
    public int getItemViewType(int position) {
        if(position < header.size()){
            return VIEW_TYPE_HEADER;
        }
        return VIEW_TYPE_ITEM;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        switch (viewType){
            case VIEW_TYPE_ITEM: {
                view = inflater.inflate(R.layout.view_youtube, parent, false);
                YoutubeViewHolder holder = new YoutubeViewHolder(view);
                holder.setOnItemClickListener(this);
                return holder;
            }
            default: {
                view = inflater.inflate(R.layout.header_youtube, parent, false);
                HeaderYoutubeViewHolder holder = new HeaderYoutubeViewHolder(view);
                return holder;
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position == 0){ // 헤더뷰가 하나인 경우
            ((HeaderYoutubeViewHolder)holder).setData(header.get(position));

        }else {
            int index = position - 1;  // 헤더뷰 하나를 빼고 나머지 아이템 뷰 인 경우
            ((YoutubeViewHolder)holder).setData(items.get(index));
        }
    }

    @Override
    public int getItemCount() {
        return items.size() + header.size();
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

    public Content getItem(int position){
        if(position<0 || position >= items.size() + header.size()) return null;

        return items.get(position -1); // -1은 헤더뷰가 하나 있다고 가정하고 헤더뷰를 제외한 값
    }

}
