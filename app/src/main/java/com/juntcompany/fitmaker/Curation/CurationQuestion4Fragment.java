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
public class CurationQuestion4Fragment extends Fragment implements View.OnClickListener{


    public CurationQuestion4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_curation_question4, container, false);
        //((CurationActivity) getActivity()).setTitle("핏메이커를 찾으신 이유를 알려주세요!");

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

        ArrayList<Integer> curationValues;
        Bundle extra = getArguments();
        curationValues = extra.getIntegerArrayList(CurationQuestion3Fragment.FRAGMENT_CURATION_KEY); //큐레이션3에서 결과값 받아오고 리스트에 넣기
        curationValues.add(btnMessage);


        int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();
        Fragment f = new CurationQuestion5Fragment();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(CurationQuestion5Fragment.FRAGMENT_CURATION_KEY, curationValues);
        f.setArguments(bundle);
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
        textView.setText("핏메이커를 찾으신 이유를 알려주세요!");
        //textView.setLayoutParams();
    }
}
