package com.juntcompany.fitmaker.Curation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.juntcompany.fitmaker.Data.Curriculum;
import com.juntcompany.fitmaker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurationResultFragment extends Fragment {


    public CurationResultFragment() {
        setHasOptionsMenu(true); // 옵션 메뉴 만들기


    }

    ListView listView;
    CurriculumAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_curation_result, container, false);
        listView = (ListView)view.findViewById(R.id.listView);
        mAdapter = new CurriculumAdapter();

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Curriculum c = (Curriculum) listView.getItemAtPosition(position);
                Intent intent = new Intent();
            }
        });
        ////////////////////////////임시 데이터라 지울 코드임
        for(int i = 0; i<3 ; i ++) {
            Curriculum curriculum = new Curriculum();
            mAdapter.add(curriculum);
        }
////////////////////////////////
        return view;
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
