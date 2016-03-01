package com.juntcompany.fitmaker.Curation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.juntcompany.fitmaker.Curation.Result.CurriculumAdapter;
import com.juntcompany.fitmaker.Data.Curriculum;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.SpecificCurriculum.SpecificCurriculumActivity;
import com.juntcompany.fitmaker.util.OnItemClickListener;

import java.util.List;

public class RecommendActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    CurriculumAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle("추천커리큘럼");

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mAdapter = new CurriculumAdapter();
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Curriculum c = (Curriculum) mAdapter.getItem(position);
                Intent intent = new Intent(RecommendActivity.this, SpecificCurriculumActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        NetworkManager.getInstance().getCurriculum(5, new NetworkManager.OnResultListener<List<Curriculum>>() {
            @Override
            public void onSuccess(List<Curriculum> result) {
                for(Curriculum c : result) {
                   mAdapter.add(c);
                }
            }

            @Override
            public void onFailure(int error) {

            }
        });

//        for(int i =0; i<5; i++){
//            Curriculum curriculum = new Curriculum();
//            mAdapter.add(curriculum);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
