package com.juntcompany.fitmaker.SpecificCurriculum;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juntcompany.fitmaker.Data.Content;
import com.juntcompany.fitmaker.Data.Curriculum;
import com.juntcompany.fitmaker.Data.ProjectRequestResult;
import com.juntcompany.fitmaker.Main.MainActivity;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.Manager.PropertyManager;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.util.YoutubeActivity;
import com.juntcompany.fitmaker.util.OnItemClickListener;

import java.io.UnsupportedEncodingException;
import java.util.List;

import okhttp3.Request;


public class SpecificCurriculumActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager;
    SpecificCurriculumViewPagerAdapter pagerAdapter;
    YoutubeAdapter mAdapter;
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;

    ImageView imageTitle, imageLevel;
    TextView textName;

    public static final String CURRICULUM_MESSAGE = "intent_curriculum_result";
    private static final String CURRICULUM_ID_NUMBER = "curriculum_id_number";
    private static final String PROJECT_ID_NUMBER = "project_id_number";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_curriculum);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        View view = getLayoutInflater().inflate(R.layout.toolbar_specific, null);
        actionBar.setCustomView(view, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

//        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
//        viewPager = (ViewPager)findViewById(R.id.pager);
//        pagerAdapter = new SpecificCurriculumViewPagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(pagerAdapter);
//
//        tabLayout.setupWithViewPager(viewPager);
//
//        tabLayout.removeAllTabs();
//
//        tabLayout.addTab(tabLayout.newTab().setText("초급자"));
//        tabLayout.addTab(tabLayout.newTab().setText("중급자"));
//        tabLayout.addTab(tabLayout.newTab().setText("상급자"));


        final Intent intent = getIntent();
        final Curriculum curriculum =(Curriculum)intent.getSerializableExtra(CURRICULUM_MESSAGE); // 큐레이션 결과에서 선택한 커리큘럼 객체를 가져옴
        imageTitle = (ImageView)findViewById(R.id.image_title);
        imageLevel = (ImageView)findViewById(R.id.image_level);
        textName = (TextView)findViewById(R.id.text_name);
        Glide.with(getApplicationContext()).load(curriculum.curriculum_image).into(imageTitle);

        textName.setText(curriculum.curriculumName);

        if(curriculum.curriculumLevel.equals("초급")){
            imageLevel.setImageResource(R.drawable.ic_curriculum_level_1);
        }else if(curriculum.curriculumLevel.equals("중급")){
            imageLevel.setImageResource(R.drawable.ic_curriculum_level_2);
        }else if(curriculum.curriculumLevel.equals("상급")){
            imageLevel.setImageResource(R.drawable.ic_curriculum_level_3);
        }



        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mAdapter = new YoutubeAdapter();
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Content content = (Content) mAdapter.getItem(position);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(content.contentYoutube));
                Intent intent = new Intent(getApplicationContext(), YoutubeActivity.class);
                intent.putExtra(YoutubeActivity.CONTENT_YOUTUBE_MESSAGE, content.contentYoutube);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);

        //layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getItemViewType(position)){
                    case YoutubeAdapter.VIEW_TYPE_HEADER:
                        return 2;
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);

        List<Content> contents = curriculum.contents;
        mAdapter.addAll(contents);
        mAdapter.addHeader("커리큘럼 운동 유튜브 영상으로 미리 보기");

        Log.i(CURRICULUM_ID_NUMBER, curriculum.curriculumId + curriculum.curriculumName);

        Button btn = (Button)findViewById(R.id.btn_start);
        btn.setOnClickListener(new View.OnClickListener() { // 운동 시작하기 버튼을 누르면 프로젝트가 생성되고 메인에 id 값을 보내야함.
            @Override
            public void onClick(View v) { // 운동을 시작하면 내가 가지고 있는 커리큘럼 id를 보내고 project id를 받음

                try { //두번째 파라미터에 ""+curriculum.curriculumId 해야 함. 아직 더미
                    NetworkManager.getInstance().setProject(SpecificCurriculumActivity.this, ""+curriculum.curriculumId , new NetworkManager.OnResultListener<ProjectRequestResult>() {
                        @Override
                        public void onSuccess(Request request, ProjectRequestResult result) {
                            int projectId =result.projectId;
                            PropertyManager.getInstance().setProjectId(projectId);
                            Log.i(PROJECT_ID_NUMBER , "" + projectId);
                            //String projectName =curriculum.curriculumName; // 선택한 커리큘럼 네임이 프로젝트 네임이 됨
                            Intent intent = new Intent(SpecificCurriculumActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // 메인을 들어가면 메인 전에 실행했던 TASK 를 모두 삭제
                            intent.putExtra(MainActivity.INTENT_PROJECT_ID, projectId);
//                            intent.putExtra(MainActivity.INTENT_PROJECT_NAME, projectName);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {

                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
