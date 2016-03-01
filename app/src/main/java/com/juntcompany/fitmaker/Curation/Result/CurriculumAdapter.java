package com.juntcompany.fitmaker.Curation.Result;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juntcompany.fitmaker.Data.Curriculum;
import com.juntcompany.fitmaker.Data.CurationType;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.util.OnHeaderClickListener;
import com.juntcompany.fitmaker.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EOM on 2016-02-20.
 */
public class CurriculumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnItemClickListener, OnHeaderClickListener{
    List<HeaderCurationResult> headers = new ArrayList<HeaderCurationResult>();
    List<Curriculum> items = new ArrayList<Curriculum>();

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 100;

    public void addHeader(View headerView, Object data){ // 클릭을 원하면 파라미터로 boolean isClick 도 추가
        HeaderCurationResult header = new HeaderCurationResult();
        header.headerView = headerView;
        header.data = data;
//        header.isClickable = isClick;
        headers.add(header);
        notifyDataSetChanged();
    }

    public void add(Curriculum curriculum){
        items.add(curriculum);
        notifyDataSetChanged();
    }

    public void addAll(List<Curriculum> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position < headers.size()){
            return VIEW_TYPE_HEADER; //?? +position 을 해야 하는가??
        }

        return VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        switch (viewType) {
            case VIEW_TYPE_ITEM: {
                view = inflater.inflate(R.layout.view_curriculum, parent, false);
                CurriculumViewHolder holder = new CurriculumViewHolder(view);
                holder.setOnItemClickListener(this);
                return holder;
            }
            default: { // 헤더뷰는 하나 무조건 있으므로
//                 view = inflater.inflate(R.layout.header_curation_result, parent, false);
                HeaderCurationResult header = headers.get(viewType);
                HeaderCurationViewHolder holder = new HeaderCurationViewHolder(header.headerView);
                return holder;
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position >= headers.size()){ // 헤더뷰 말고 커리큘럼 뷰
            int index = position - headers.size();
            ((CurriculumViewHolder)holder).setData(items.get(index));
        }
        //헤더뷰
            int headerIndex = position - items.size();
//        ((HeaderCurationViewHolder)holder).setData(headers.get(headerIndex));
    }

    @Override
    public int getItemCount() {
        return items.size() + headers.size();
    }


    @Override
    public void onHeaderClick(View view, int position) {
            ///....클릭하게 하려면 헤더 클릭리스너 만들어 주고 추가
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
        if (position < 0 || position >= items.size()+ headers.size()) return null;

        return items.get(position);
    }


}
