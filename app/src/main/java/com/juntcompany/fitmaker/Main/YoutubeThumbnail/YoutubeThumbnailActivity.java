package com.juntcompany.fitmaker.Main.YoutubeThumbnail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.juntcompany.fitmaker.Data.Content;
import com.juntcompany.fitmaker.Data.Project;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.SpecificCurriculum.YoutubeAdapter;
import com.juntcompany.fitmaker.util.OnItemClickListener;
import com.juntcompany.fitmaker.util.YoutubeActivity;

import java.util.List;

public class YoutubeThumbnailActivity extends AppCompatActivity {


    YoutubeThumbnailAdapter mAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    ImageView imageTitle;

    public static final String PROJECT_MESSAGE = "project_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_thumbnail);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        imageTitle = (ImageView)findViewById(R.id.image_title);
 //       Glide.with(getApplicationContext()).load(curriculum.curriculum_image).into(imageTitle);

        Intent intent = getIntent();
        Project project = (Project)intent.getSerializableExtra(PROJECT_MESSAGE);


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mAdapter = new YoutubeThumbnailAdapter();
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
        recyclerView.setLayoutManager(layoutManager);
   //     List<Content> contents = curriculum.contents;
//        mAdapter.addAll(contents);
        mAdapter.addHeader("커리큘럼 운동 유튜브 영상으로 미리 보기");


    }
}
