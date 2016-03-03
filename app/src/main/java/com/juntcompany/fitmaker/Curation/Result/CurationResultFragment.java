package com.juntcompany.fitmaker.Curation.Result;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.juntcompany.fitmaker.Data.Curriculum;
import com.juntcompany.fitmaker.Data.CurationType;
import com.juntcompany.fitmaker.Data.TypeCurriculumResult;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.SpecificCurriculum.SpecificCurriculumActivity;
import com.juntcompany.fitmaker.util.OnItemClickListener;

import java.io.UnsupportedEncodingException;
import java.util.List;

import okhttp3.Request;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurationResultFragment extends Fragment { // 헤더는 무조건 하나 들어가게 설계함


    public CurationResultFragment() {
        setHasOptionsMenu(true); // 옵션 메뉴 만들기


    }

    RecyclerView recyclerView;
    CurriculumAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_curation_result, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        mAdapter = new CurriculumAdapter();
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Curriculum c = (Curriculum) mAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), SpecificCurriculumActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        initData();


//        try { // 커리큘럼만 받아오게 함
//            NetworkManager.getInstance().getCurriculum(getContext(),""+1, ""+1, ""+1, new NetworkManager.OnResultListener<List<Curriculum>>() {
//                @Override
//                public void onSuccess(Request request, List<Curriculum> result) {
//                    for(Curriculum c : result) {
//                        mAdapter.add(c);
//                    }
//                }
//
//                @Override
//                public void onFailure(Request request, int code, Throwable cause) {
//
//                }
//
//                //        for(int i =0; i<5; i++){
//                //            Curriculum curriculum = new Curriculum();
//                //            mAdapter.add(curriculum);
//                //        }
//            });
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        try { //큐레이션만 받아오게 함
//            NetworkManager.getInstance().getCuration(getContext(), "" + 1, "" + 1, "" + 1, new NetworkManager.OnResultListener<CurationType>() {
//                @Override
//                public void onSuccess(Request request, CurationType result) {
//                    mAdapter.addHeader(result);
//                }
//
//                @Override
//                public void onFailure(Request request, int code, Throwable cause) {
//
//                }
//            });
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        try { //큐레이션만 받아오게 함
            NetworkManager.getInstance().getCurriculumCuration(getContext(), "" + 1, "" + 1, "" + 1, new NetworkManager.OnResultListener<TypeCurriculumResult>() {
                @Override
                public void onSuccess(Request request, TypeCurriculumResult result) {
                    for(Curriculum c : result.curriculums) {
                        mAdapter.add(c);
                    }
                    mAdapter.addHeader(result.exctype);
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }




//////////////////////////////////
        return view;
    }

    private void initData(){
        CurationType type = new CurationType();
        type.type_name = "ddd";
        type.type_picture = String.valueOf(R.mipmap.ic_launcher);
        type.type_info = "asdasdfasdffad";

//        mAdapter.addHeader(type);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_curation_result, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_curation_again:
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE); //스택에 있는걸 다 팝하는 효과 , null 에 값쓰면 그 프래그 먼트 까지 팝
                                                                                                                            //0을 쓰면 해당 되는 거 위까지 팝 ,inclusive는 해당까지 pop

        }

        return super.onOptionsItemSelected(item);
    }
}
