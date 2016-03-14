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
import com.juntcompany.fitmaker.Data.Structure.TypeCurriculumResult;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.SpecificCurriculum.SpecificCurriculumActivity;
import com.juntcompany.fitmaker.util.OnItemClickListener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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

    public static final String FRAGMENT_CURATION_KEY = "fragment_key";

    ArrayList<Integer> curationKey;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extra = getArguments();
        curationKey = extra.getIntegerArrayList(FRAGMENT_CURATION_KEY);
    }

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
                Curriculum curriculum = (Curriculum) mAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), SpecificCurriculumActivity.class);
                intent.putExtra(SpecificCurriculumActivity.CURRICULUM_MESSAGE, curriculum);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
      //  initData();


        try { //큐레이션만 받아오게 함 , 큐레이션 질문은 1번, 4번, 6번만 받아오게 함
            NetworkManager.getInstance().getCurriculumCuration(getContext(),""+curationKey.get(0), ""+curationKey.get(2),""+curationKey.get(5),new NetworkManager.OnResultListener<TypeCurriculumResult>() {
                @Override
                public void onSuccess(Request request, TypeCurriculumResult result) {
                    for(Curriculum c : result.curriculums) {
                        mAdapter.add(c);
                    }
                    mAdapter.addHeader(result.curationType);
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
        type.typeName = "ddd";
        type.typePicture = String.valueOf(R.mipmap.ic_launcher);
        type.typeInfo = "asdasdfasdffad";

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
