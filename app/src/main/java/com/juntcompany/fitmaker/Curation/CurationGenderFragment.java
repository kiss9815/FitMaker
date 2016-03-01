package com.juntcompany.fitmaker.Curation;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.juntcompany.fitmaker.Curation.Result.CurationResultFragment;
import com.juntcompany.fitmaker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurationGenderFragment extends Fragment implements View.OnClickListener {


    public CurationGenderFragment() {
        setHasOptionsMenu(true); // 옵션메뉴 만들기
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_curation_gender, container, false);


        Button btn = (Button)view.findViewById(R.id.btn_woman);
        btn.setOnClickListener(this);
        btn = (Button)view.findViewById(R.id.btn_man);
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            getActivity().setTitle("성별은 어떻게 되시나요?");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_woman: {
                ///////////////////////////////////////////////////////////////////////////////아직 디자인 안나와서 바꿔야함
                Toast.makeText(getContext(), "여자", Toast.LENGTH_SHORT).show();
                int count = getActivity().getSupportFragmentManager().getBackStackEntryCount(); // 백스택 개수
                Fragment f = new CurationResultFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.curation_container, f);
                ft.addToBackStack(""+count);
                ft.commit();
                break;
            }

            case R.id.btn_man: {
                Toast.makeText(getContext(), "남자", Toast.LENGTH_SHORT).show();
                Fragment f = new CurationResultFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.curation_container, f);
                ft.commit();
                break;
            }
        }
    }


}
