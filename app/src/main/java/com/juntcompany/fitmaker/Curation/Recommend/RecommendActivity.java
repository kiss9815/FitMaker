package com.juntcompany.fitmaker.Curation.Recommend;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.juntcompany.fitmaker.Curation.Result.CurriculumAdapter;
import com.juntcompany.fitmaker.Data.Curriculum;
import com.juntcompany.fitmaker.Data.Structure.TypeCurriculumResult;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.Manager.PropertyManager;
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

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        View view = getLayoutInflater().inflate(R.layout.toolbar_recommend, null);
        actionBar.setCustomView(view, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));

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

        int curationId = PropertyManager.getInstance().getCurationType(); //default 가 0
        if(curationId != 0) {
            callCurriculum(curationId);
        }
        else { // curation 타입이 없는 상태로 앱을 제일 먼저 들어왔을때
            callCurriculum();
        }


    }

    private void callCurriculum() {
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

    private void callCurriculum(int curationId){
        try {
            NetworkManager.getInstance().getCurriculumByCuration(getApplicationContext(), ""+curationId, new NetworkManager.OnResultListener<TypeCurriculumResult>() {
                @Override
                public void onSuccess(Request request, TypeCurriculumResult result) {
                    mAdapter.addAll(result.curriculums);
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
