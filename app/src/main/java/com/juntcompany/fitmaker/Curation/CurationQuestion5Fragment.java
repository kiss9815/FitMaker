package com.juntcompany.fitmaker.Curation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.juntcompany.fitmaker.Curation.Result.CurationResultFragment;
import com.juntcompany.fitmaker.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurationQuestion5Fragment extends Fragment implements View.OnClickListener{


    public CurationQuestion5Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_curation_question5, container, false);
        //((CurationActivity) getActivity()).setTitle("당신이 원하는 운동 스타일을 알려주세요!");

        Button btn = (Button)view.findViewById(R.id.btn_1);
        btn.setOnClickListener(this);
        btn = (Button)view.findViewById(R.id.btn_2);
        btn.setOnClickListener(this);
        btn = (Button)view.findViewById(R.id.btn_3);
        btn.setOnClickListener(this);
        return view;
    }
    private static final String BACK_STACK_COUNT = "back_stack_count";
    public static final String FRAGMENT_CURATION_KEY = "fragment_key";
    private static final String ARRAY_LIST_TEST = "array_list until 5q";
    private int btnMessage;
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_1:
                btnMessage =1;
                break;
            case R.id.btn_2:
                btnMessage =2;
                break;
            case R.id.btn_3:
                btnMessage =3;
                break;
        }

        ArrayList<Integer> curationValues ;
        Bundle extra = getArguments();
        curationValues = extra.getIntegerArrayList(CurationQuestion3Fragment.FRAGMENT_CURATION_KEY); //큐레이션4에서 결과값 받아오고 리스트에 넣기
        curationValues.add(btnMessage);

        Log.i("question", "question5 : " + btnMessage);
        int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();


        if((curationValues.get(0) ==1 && curationValues.get(3) == 3) || (curationValues.get(0) == 2 && curationValues.get(3) ==3)) { // 1번질문이 1(여성) 이고 4번 질문이 3번을 선택한 경우에만 6번 질문을 물어봄
                                                                                            //// 1번질문이 2(남성) 이고 4번 질문이 3번을 선택한 경우에만 6번 질문을 물어봄
            Fragment f = new CurationQuestion6Fragment();
            Bundle bundle = new Bundle();
            bundle.putIntegerArrayList(CurationQuestion6Fragment.FRAGMENT_CURATION_KEY, curationValues);
            f.setArguments(bundle);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
            ft.replace(R.id.curation_container, f);
            ft.addToBackStack("" + count);
            ft.commit();
//        }else if(curationValues.get(0) == 2 && curationValues.get(2) ==3) { // 1번질문이 2(남성) 이고 3번 질문이 3번을 선택한 경우에만 6번 질문을 물어봄
//            Fragment f = new CurationQuestion6Fragment();
//            Bundle bundle = new Bundle();
//            bundle.putIntegerArrayList(CurationQuestion6Fragment.FRAGMENT_CURATION_KEY, curationValues);
//            f.setArguments(bundle);
//            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.curation_container, f);
//            ft.addToBackStack("" + count);
//            ft.commit();
        }else {
            Fragment f = new CurationResultFragment();
            Bundle bundle = new Bundle();
            bundle.putIntegerArrayList(CurationQuestion6Fragment.FRAGMENT_CURATION_KEY, curationValues);
            f.setArguments(bundle);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
            ft.replace(R.id.curation_container, f);
            ft.addToBackStack("" + count);
            ft.commit();
            Log.i(ARRAY_LIST_TEST, curationValues.toString());
        }
        Log.i(BACK_STACK_COUNT, "" + count);
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = ((CurationActivity) getActivity()).getSupportActionBar();
        View view = actionBar.getCustomView();
        TextView textView = (TextView)view.findViewById(R.id.text_toolbar);
        textView.setText("당신이 원하는 운동 스타일을 알려주세요!");

    }
}
