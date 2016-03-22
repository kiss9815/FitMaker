package com.juntcompany.fitmaker.Main.YoutubeThumbnail;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juntcompany.fitmaker.Data.Content;
import com.juntcompany.fitmaker.Data.Project;
import com.juntcompany.fitmaker.Data.Structure.YoutubeResult;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.SpecificCurriculum.YoutubeAdapter;
import com.juntcompany.fitmaker.util.OnItemClickListener;
import com.juntcompany.fitmaker.util.YoutubeActivity;

import java.io.UnsupportedEncodingException;
import java.util.List;

import okhttp3.Request;

public class YoutubeThumbnailActivity extends AppCompatActivity {


    YoutubeThumbnailAdapter mAdapter;
    RecyclerView recyclerView;
   GridLayoutManager layoutManager;

    ImageView imageTitle;
    TextView textName;
    public static final String PROJECT_MESSAGE = "project_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_thumbnail);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        View view = getLayoutInflater().inflate(R.layout.toolbar_youtube_thumbnail, null);
        actionBar.setCustomView(view, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        imageTitle = (ImageView)findViewById(R.id.image_title);
        textName = (TextView)findViewById(R.id.text_name);
 //       Glide.with(getApplicationContext()).load(curriculum.curriculum_image).into(imageTitle);

        Intent intent = getIntent();
        Project project = (Project)intent.getSerializableExtra(PROJECT_MESSAGE);

        getYoutubeList(project.projectId);
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


        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getItemViewType(position)){
                    case YoutubeThumbnailAdapter.VIEW_TYPE_HEADER:
                        return 2;
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);

   //     List<Content> contents = curriculum.contents;
//        mAdapter.addAll(contents);
        mAdapter.addHeader("커리큘럼 운동 유튜브 영상으로 미리 보기");


    }

    private void getYoutubeList(int projectId){
        try {
            NetworkManager.getInstance().getYoutube(getApplicationContext(), "" + projectId, new NetworkManager.OnResultListener<YoutubeResult>() {
                @Override
                public void onSuccess(Request request, YoutubeResult result) {
                    mAdapter.addAll(result.curriculum.contents);
                    Glide.with(getApplicationContext()).load(result.curriculum.curriculum_image).into(imageTitle);
                    textName.setText(result.curriculum.curriculumName);
                    ImageView imageLevel = (ImageView)findViewById(R.id.image_level);
                    if(result.curriculum.curriculum_level.equals("초급")){
                        imageLevel.setImageResource(R.drawable.ic_curriculum_level_1);
                    }else if(result.curriculum.curriculum_level.equals("중급")){
                        imageLevel.setImageResource(R.drawable.ic_curriculum_level_2);
                    }else if(result.curriculum.curriculum_level.equals("상급")){
                        imageLevel.setImageResource(R.drawable.ic_curriculum_level_3);
                    }
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


}
