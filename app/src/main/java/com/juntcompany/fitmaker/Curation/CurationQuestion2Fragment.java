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

import com.juntcompany.fitmaker.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurationQuestion2Fragment extends Fragment implements View.OnClickListener {


    public CurationQuestion2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_curation_question2, container, false);
        //((CurationActivity) getActivity()).setTitle("운동은 자주 하시는 편인가요?");

        Button btn = (Button)view.findViewById(R.id.btn_1);
        btn.setOnClickListener(this);
        btn = (Button)view.findViewById(R.id.btn_2);
        btn.setOnClickListener(this);
        return view;
    }

    private static final String BACK_STACK_COUNT = "back_stack_count";
    public static final String FRAGMENT_CURATION_KEY = "fragment_key";
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

        }
        Bundle extra = getArguments();
        ArrayList<Integer> curationValues; // 큐레이션 값들을 저장해서 보내기 위해 리스트를 만듬
        curationValues = extra.getIntegerArrayList(CurationQuestion2Fragment.FRAGMENT_CURATION_KEY); //큐레이션1에서 결과값 받아오기
        curationValues.add(btnMessage);
        Log.i("question", "question2 : " + btnMessage);

        int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();
        Fragment f = new CurationQuestion3Fragment();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(CurationQuestion3Fragment.FRAGMENT_CURATION_KEY, curationValues);
        f.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
        ft.replace(R.id.curation_container, f);
        ft.addToBackStack("" + count);
        ft.commit();
        Log.i(BACK_STACK_COUNT, ""+count);
    }


    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = ((CurationActivity) getActivity()).getSupportActionBar();
        View view = actionBar.getCustomView();
        TextView textView = (TextView)view.findViewById(R.id.text_toolbar);
        textView.setText("운동은 자주 하시는 편인가요?");
        //textView.setLayoutParams();
    }
}
