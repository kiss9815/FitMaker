package com.juntcompany.fitmaker.Curation.Recommend;

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

import java.io.UnsupportedEncodingException;
import java.util.List;

import okhttp3.Request;

public class RecommendActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecommendAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    Curriculum curriculum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle("추천커리큘럼");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new RecommendAdapter();
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                curriculum = (Curriculum) mAdapter.getItem(position);
                Intent intent = new Intent(RecommendActivity.this, SpecificCurriculumActivity.class);
                intent.putExtra(SpecificCurriculumActivity.CURRICULUM_MESSAGE, curriculum);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        setData();


    }

    private void setData() {
        try {
            NetworkManager.getInstance().getCurriculum(getApplicationContext(), new NetworkManager.OnResultListener<List<Curriculum>>() {
                @Override
                public void onSuccess(Request request, List<Curriculum> result) {

                    mAdapter.addAll(result);

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
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
