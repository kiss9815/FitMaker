package com.juntcompany.fitmaker.Curation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.juntcompany.fitmaker.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurationQuestion3Fragment extends Fragment implements View.OnClickListener {


    public CurationQuestion3Fragment() {
        // Required empty public constructor
    }

    ArrayList<Integer> curationValues;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle extra = getArguments();
        curationValues = extra.getIntegerArrayList(CurationQuestion3Fragment.FRAGMENT_CURATION_KEY); //큐레이션2에서 결과값 받아오고 리스트에 넣기
        View view = null;
        if(curationValues.get(1) == 1) { // 2번질문이 1번이면 인플레이트 시킬 화면
             view = inflater.inflate(R.layout.fragment_curation_question3_1, container, false);
            ((CurationActivity) getActivity()).setTitle("운동을 자주하시는 군요! 이유가 듣고 싶어요");
            Button btn = (Button) view.findViewById(R.id.btn_1);
            btn.setOnClickListener(this);
            btn = (Button) view.findViewById(R.id.btn_2);
            btn.setOnClickListener(this);

        }

        else { // 2번질문이 2번이면 인플레이트 시킬 화면
            view = inflater.inflate(R.layout.fragment_curation_question3_2, container, false);
            ((CurationActivity) getActivity()).setTitle("운동을 하지 않은 이유가 궁금해요!");
            Button btn = (Button) view.findViewById(R.id.btn_1);
            btn.setOnClickListener(this);
            btn = (Button) view.findViewById(R.id.btn_2);
            btn.setOnClickListener(this);
            btn = (Button) view.findViewById(R.id.btn_3);
            btn.setOnClickListener(this);

        }

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
            case R.id.btn_2:
                btnMessage =2;
            case R.id.btn_3:
                btnMessage =3;
                break;
        }

        curationValues.add(btnMessage);

        int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();
        Fragment f = new CurationQuestion4Fragment();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(CurationQuestion4Fragment.FRAGMENT_CURATION_KEY, curationValues);
        f.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.curation_container, f);
        ft.addToBackStack("" + count);
        ft.commit();
        Log.i(BACK_STACK_COUNT, "" + count);
    }
}
