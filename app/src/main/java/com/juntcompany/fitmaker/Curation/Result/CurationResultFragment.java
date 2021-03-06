package com.juntcompany.fitmaker.Curation.Result;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.juntcompany.fitmaker.Curation.CurationActivity;
import com.juntcompany.fitmaker.Data.Curriculum;
import com.juntcompany.fitmaker.Data.CurationType;
import com.juntcompany.fitmaker.Data.Structure.TypeCurriculumResult;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.Manager.PropertyManager;
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
        if(extra != null) {
            curationKey = extra.getIntegerArrayList(FRAGMENT_CURATION_KEY);
        }
    }


    private static final String NETWORK_RESULT = "network result";

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

        if(curationKey != null) {
            Log.i("question all", "question all : " + curationKey.toString());

            if(curationKey.size() == 5) {
                int que6 =0;
                getCurationQuestion(curationKey.get(0), curationKey.get(3), que6); // 6번을 안해서 값이 없는 경우
            }else {
                getCurationQuestion(curationKey.get(0), curationKey.get(3), curationKey.get(5)); // 큐레이션을 다 하고 온 경우
            }
        }else {
            getCurriculumByCuration(); // 메인 네비에서 큐레이션이 있을때 온 경우
        }


        return view;
    }


    private void getCurationQuestion(int q1, int q2, int q3){
        Log.i("q_three", "q_three : " + q1+ ", " + q2 + ", "+ q3);
        try {
            NetworkManager.getInstance().getCurriculumCuration(getContext(), "" + q1, "" + q2, "" + q3, new NetworkManager.OnResultListener<TypeCurriculumResult>() {
                @Override
                public void onSuccess(Request request, TypeCurriculumResult result) {
                    Log.i(NETWORK_RESULT, result.message);
                    for(Curriculum c : result.curriculums) {
                        mAdapter.add(c);
                    }
                    mAdapter.addHeader(result.curationType);

                    PropertyManager.getInstance().setCurationType(result.curationType.typeId);
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void getCurriculumByCuration(){
        try {
            NetworkManager.getInstance().getCurriculumByCuration(getContext(), "" + PropertyManager.getInstance().getCurationType(), new NetworkManager.OnResultListener<TypeCurriculumResult>() {
                @Override
                public void onSuccess(Request request, TypeCurriculumResult result) {
                    for (Curriculum c : result.curriculums) {
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
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = ((CurationActivity) getActivity()).getSupportActionBar();
        View view = actionBar.getCustomView();
        TextView textView = (TextView)view.findViewById(R.id.text_toolbar);
        textView.setText("큐레이션 결과");
        //textView.setLayoutParams();
    }


}
