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
public class CurationQuestion6Fragment extends Fragment implements View.OnClickListener{


    public CurationQuestion6Fragment() {
        // Required empty public constructor
    }


    ArrayList<Integer> curationValues;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // ((CurationActivity) getActivity()).setTitle("당신이 원하는 몸매는 무엇인가요?");

        // Inflate the layout for this fragment
        Bundle extra = getArguments();
        curationValues = extra.getIntegerArrayList(CurationQuestion3Fragment.FRAGMENT_CURATION_KEY); //큐레이션5에서 결과값 받아오고 리스트에 넣기
        View view =null;
        if(curationValues.get(0) ==1) { // 1번 문항 1번(여자)이고 3번 문항 3번인 사람
            view = inflater.inflate(R.layout.fragment_curation_question6_1, container, false);
            Button btn = (Button) view.findViewById(R.id.btn_1);
            btn.setOnClickListener(this);
            btn = (Button) view.findViewById(R.id.btn_2);
            btn.setOnClickListener(this);
            btn = (Button) view.findViewById(R.id.btn_3);
            btn.setOnClickListener(this);
            btn = (Button) view.findViewById(R.id.btn_4);
            btn.setOnClickListener(this);

        }else { // 1번 문항 2번(남자)이고 3번 문항 3번인 사람
            view = inflater.inflate(R.layout.fragment_curation_question6_2, container, false);
            Button btn = (Button) view.findViewById(R.id.btn_1);
            btn.setOnClickListener(this);
            btn = (Button) view.findViewById(R.id.btn_2);
            btn.setOnClickListener(this);
            btn = (Button) view.findViewById(R.id.btn_3);
            btn.setOnClickListener(this);
            btn = (Button) view.findViewById(R.id.btn_4);
            btn.setOnClickListener(this);
        }
        return view;
    }
    private static final String ARRAY_LIST_TEST = "array_list";
    public static final String FRAGMENT_CURATION_KEY = "fragment_key";

    private static final String BACK_STACK_COUNT = "back_stack_count";

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
            case R.id.btn_4:
                btnMessage =4;
                break;
        }


        curationValues.add(btnMessage);
        //테스트용
        Log.i("question", "question6 : " + btnMessage);
        Log.i(ARRAY_LIST_TEST, curationValues.toString());

        int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();
        Fragment f = new CurationResultFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList(CurationResultFragment.FRAGMENT_CURATION_KEY, curationValues);
        f.setArguments(args);

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
        ft.replace(R.id.curation_container, f);
        ft.addToBackStack("" + count);
        ft.commit();
        Log.i(BACK_STACK_COUNT, "" + count);
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = ((CurationActivity) getActivity()).getSupportActionBar();
        View view = actionBar.getCustomView();
        TextView textView = (TextView)view.findViewById(R.id.text_toolbar);
        textView.setText("당신이 원하는 몸매는 무엇인가요?");

    }
}
