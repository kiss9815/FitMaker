package com.juntcompany.fitmaker.Curation;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.juntcompany.fitmaker.Curation.Recommend.RecommendActivity;
import com.juntcompany.fitmaker.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurationQuestion1Fragment extends Fragment implements View.OnClickListener {


    public CurationQuestion1Fragment() {
        setHasOptionsMenu(true); // 옵션메뉴 만들기
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_curation_question1, container, false);


        Button btn = (Button)view.findViewById(R.id.btn_1);
        btn.setOnClickListener(this);
        btn = (Button)view.findViewById(R.id.btn_2);
        btn.setOnClickListener(this);



        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_curation_gender, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_curation_skip){
            Intent intent = new Intent(getContext(), RecommendActivity.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    private int btnMessage;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_1: {
                ///////////////////////////////////////////////////////////////////////////////아직 디자인 안나와서 바꿔야함
                 btnMessage = 1;
                break;
            }

            case R.id.btn_2: {
                 btnMessage = 2;
                break;
            }
        }
        ArrayList<Integer> curationValues = new ArrayList<Integer>();
        curationValues.add(btnMessage);
        int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();
        Fragment f = new CurationQuestion2Fragment();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(CurationQuestion2Fragment.FRAGMENT_CURATION_KEY, curationValues);
        f.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
        ft.replace(R.id.curation_container, f);
        ft.addToBackStack(""+count);
        ft.commit();    //어떤 버튼이 눌리던 백스택에 해당 프래그먼트가 저장됨

    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = ((CurationActivity) getActivity()).getSupportActionBar();
        View view = actionBar.getCustomView();
        TextView textView = (TextView)view.findViewById(R.id.text_toolbar);
        textView.setText("성별은 어떻게 되시나요?");
        //textView.setLayoutParams();
    }


}
