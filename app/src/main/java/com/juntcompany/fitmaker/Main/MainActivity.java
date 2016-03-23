package com.juntcompany.fitmaker.Main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.juntcompany.fitmaker.Alarm.AlarmProcessingService;
import com.juntcompany.fitmaker.Badge.BadgeActivity;
import com.juntcompany.fitmaker.Curation.CurationActivity;
import com.juntcompany.fitmaker.Curation.Recommend.RecommendActivity;
import com.juntcompany.fitmaker.Data.Project;
import com.juntcompany.fitmaker.Data.ProjectResponseResult;
import com.juntcompany.fitmaker.Data.Structure.MyResult;
import com.juntcompany.fitmaker.Data.Structure.Result;
import com.juntcompany.fitmaker.Data.User;
import com.juntcompany.fitmaker.Friend.FriendListActivity;
import com.juntcompany.fitmaker.Main.CourseDialog.CourseDialogFragment;
import com.juntcompany.fitmaker.Data.Course;
import com.juntcompany.fitmaker.Main.MainSpinner.MainSpinnerAdapter;
import com.juntcompany.fitmaker.Main.NextTime.FinishedDialogFragment;
import com.juntcompany.fitmaker.Main.NextTime.NextTimeDialogFragment;
import com.juntcompany.fitmaker.Main.PlayButton.PlayButtonAdapter;
import com.juntcompany.fitmaker.Main.YoutubeThumbnail.ProjectYoutubeDialogFragment;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.Manager.PropertyManager;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.Main.RankingDialog.RankingDialogFragment;
import com.juntcompany.fitmaker.Setting.NoticeActivity;
import com.juntcompany.fitmaker.Setting.SettingActivity;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static final String MAIN_DIALOG_TAG = "dialog";
    public static final String INTENT_PROJECT_ID = "intent_project_id";
    public static final String INTENT_PROJECT_NAME = "intent_project_name";
    public static final String BADGE_MESSAGE = "badge_message";
    public static final String USER_MESSAGE = "user_message";


    RecyclerView recyclerView;
    PlayButtonAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    MainSpinnerAdapter spinnerAdapter;

    NavigationView navigationView;

    List<Course> courses = new ArrayList<Course>();

    int projectId; // 스피너에서 사용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(MainActivity.this, AlarmProcessingService.class)); // .....서비스

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        View view = getLayoutInflater().inflate(R.layout.toolbar_main, null);
        actionBar.setCustomView(view, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));


        setBackGround(); // 배경사진 바꾸게 하는 메소드

//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinString);
        spinnerAdapter = new MainSpinnerAdapter();
        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);

        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Project p = (Project)parent.getItemAtPosition(position);
                PropertyManager.getInstance().setProjectId(p.projectId);
                Log.i("project", "spinner selected project_id : " + p.projectId);
                callProject(p.projectId); // 프로젝트 아이디를 부르면 해당 프로젝트를 다 가져옴
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Project p = (Project)parent.getItemAtPosition(0);
                Log.i("project", "spinner nothingSelected project_id : " + p.projectId);
                PropertyManager.getInstance().setProjectId(p.projectId);
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


//////////////////////////////////////////////////////////////////////////////////헤더뷰
        Button btn = (Button)findViewById(R.id.btn_ranking);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RankingDialogFragment f = new RankingDialogFragment();
                f.show(getSupportFragmentManager(), MAIN_DIALOG_TAG);
            }
        });

        callMyProfile(); // 메인에서 내 프로필도 네트워크에서 가져옴

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mAdapter = new PlayButtonAdapter();
        mAdapter.setOnItemClickListener(new PlayButtonAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemButtonClick(View view, int position) {
                Course course = mAdapter.getItem(position);
                //  Toast.makeText(getApplicationContext(), "course" + course.courseSeq, Toast.LENGTH_SHORT).show();
                if (course.isSelectable == false && course.isFinish == false) {   // 흰색 플레이 버튼을 누르는 경우
                    NextTimeDialogFragment df = new NextTimeDialogFragment();
                    df.show(getSupportFragmentManager(), MAIN_DIALOG_TAG);
                } else if (course.isFinish == true) { ////....
                    FinishedDialogFragment df = new FinishedDialogFragment();
                    df.show(getSupportFragmentManager(), MAIN_DIALOG_TAG);
//                    for(Course a : courses){
//                        position++;
//                        if(!courses.get(position).isFinish){
//
//                            recyclerView.scrollToPosition(position);
//                        };
//
//                    }


//                    while (course.isFinish) {
//                        position++;
//
//                        if(!course.isFinish){
//                            break;
//                        }
//                    }recyclerView.scrollToPosition(position);
                    // recyclerView.scrollToPosition(); // today 인덱스로 이동
                } else { //빨간색을 누른경우

                    CourseDialogFragment f = new CourseDialogFragment();
                    Bundle args = new Bundle();
                    args.putInt(CourseDialogFragment.PROJECT_ID_MESSAGE, PropertyManager.getInstance().getProjectId());
                    args.putSerializable(CourseDialogFragment.COURSE_DIALOG_MESSAGE, course);
                    f.setArguments(args);
                    f.show(getSupportFragmentManager(), MAIN_DIALOG_TAG);
                }
            }
        });

        recyclerView.setAdapter(mAdapter);

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

//        Intent intent = getIntent(); //SpecificActivity에서 넘어옴
 //       projectId =intent.getIntExtra(INTENT_PROJECT_ID, 0); //0은 default value // 프로젝트 id를 넘겨받음. main에서 다시 서버에 요청해야 함
        //String projectName = intent.getStringExtra(INTENT_PROJECT_NAME);


//        PropertyManager.getInstance().setProjectId(projectId); // 프로젝트 id를 preference에 저장
//        if(projectId == 0){ // intent 못받아오면 100
//            int propertyProjectId = PropertyManager.getInstance().getProjectId();
//            Log.i("project_id", "preference_project_id : " + propertyProjectId);
//
//            callProject(propertyProjectId);
//        }
//
//       else { // 큐레이션 결과에서 받아온 프로젝트 id
//            callProject(projectId); //전 페이지에서 해당 페이지로 넘어올때 생성된 프로젝트 id로 커리큘럼과 코스 생성.
//        }

    }

    private void setBackGround(){
        if(!TextUtils.isEmpty(PropertyManager.getInstance().getBackgroundImage())) {
            File file = new File(PropertyManager.getInstance().getBackgroundImage());
            Uri fileUri = Uri.fromFile(file);
            final RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.background);

            Glide.with(this).load(fileUri).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    Drawable drawable = new BitmapDrawable(resource);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        relativeLayout.setBackground(drawable);
                    }
                }
            });
        }
    }

//////////////////////////////////////////////////////////////////////////////////////oncreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_menu_my_profile_change:
                callGallery();
                break;

            case R.id.main_menu_background_change:
                callBackGround();
                break;

            case R.id.main_menu_share:

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                Cursor c = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    File file = new File(path);
                    Uri fileUri = Uri.fromFile(file);

                    View headerview = navigationView.getHeaderView(0);
                    ImageView imageProfile = (ImageView)headerview.findViewById(R.id.image_profile);
                    Glide.with(this).load(fileUri).into(imageProfile); // 헤더뷰의 내 프로필 이미지 변경
                    setMyProfilePicture(file);
                }

            }
        }else if(requestCode == BACKGROUND_GALLERY){
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                Cursor c = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (c.moveToNext()) {
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));

                    PropertyManager.getInstance().setBackgroundImage(path); // 파일 path를 preference에 저장

                    File file = new File(path);
                    Uri fileUri = Uri.fromFile(file);


                    final RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.background);


                    Glide.with(this).load(fileUri).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            Drawable drawable = new BitmapDrawable(resource);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                relativeLayout.setBackground(drawable);
                            }
                        }
                    }); // 헤더뷰의 내 프로필 이미지 변경
                }

            }
        }
    }

    private void setMyProfilePicture(File file){
        try {
            NetworkManager.getInstance().setMyProfilePicture(getApplicationContext(), file, new NetworkManager.OnResultListener<Result>() {
                @Override
                public void onSuccess(Request request, Result result) {

                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }



    private static final int RC_GALLERY = 1;
    private static final int BACKGROUND_GALLERY = 2;
    private void callGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");

        //   intent.putExtra(MediaStore.EXTRA_OUTPUT, getFileUri()); // 크롭이 아닌경우는 의미가 없음
        startActivityForResult(intent, RC_GALLERY);
    }

    private void callBackGround() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");

        //   intent.putExtra(MediaStore.EXTRA_OUTPUT, getFileUri()); // 크롭이 아닌경우는 의미가 없음
        startActivityForResult(intent, BACKGROUND_GALLERY);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {   /////네비게이션 선택
        int id = item.getItemId();

       if (id == R.id.nav_recommend_curriculum) {
            Intent intent = new Intent(getApplicationContext(), RecommendActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_friend_list) {
            Intent intent = new Intent(getApplicationContext(), FriendListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_my_style) {  // 큐레이션 액티비티의 큐레이션 결과 로 가야함
            boolean flag = true;
            Intent intent = new Intent(getApplicationContext(), CurationActivity.class);
            intent.putExtra(CurationActivity.CURATION_MESSAGE, flag);
            startActivity(intent);
        } else if (id == R.id.nav_setting_curation) {
            Intent intent = new Intent(MainActivity.this, CurationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_ongoing_project) { // 유튜브 보기로 바꿈
            //..프로젝트 하고 있는 리스트가 나오기.....
           ProjectYoutubeDialogFragment df = new ProjectYoutubeDialogFragment();
            List<Project> projects = spinnerAdapter.getItems();
           Bundle argsProject = new Bundle();
           argsProject.putSerializable(ProjectYoutubeDialogFragment.PROJECT_ID_MESSAGE, (Serializable) projects);
           df.setArguments(argsProject);
           df.show(getSupportFragmentManager(), MAIN_DIALOG_TAG);

        }else if(id==R.id.nav_expert_talk){
            Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
            startActivity(intent);
        }else if (id== R.id.nav_setting){
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void callProject(int projectId){ //
        try {
            Log.i("MainActivity","projectId : " + projectId);
            NetworkManager.getInstance().getProject(MainActivity.this, "" + projectId, new NetworkManager.OnResultListener<ProjectResponseResult>() {
                @Override
                public void onSuccess(Request request, ProjectResponseResult result) {
                    Log.i("today", " today : " + result.today.position);
                    List<Project> projects = result.projects;
                    spinnerAdapter.clear();

//                    argsProject.putSerializable(ProjectYoutubeDialogFragment.PROJECT_ID_MESSAGE, (Serializable) projects); // 프로젝트

                    spinnerAdapter.addAll(projects);
                   // spinnerAdapter.insert((Project) spinnerAdapter.getItem(projects.size()-1), 0);
                   // spinnerAdapter.remove((Project) spinnerAdapter.getItem(projects.size()-1));

                    mAdapter.clear();
                    courses = result.courses;
                    recyclerView.scrollToPosition(result.today.position);
                    // ....

                    Log.i("aaa", courses.toString());
                    boolean flag = true;
                    for (int i = 0; i < result.today.position - 1; i++) { // position이 운동을 해야하는날// position은 인덱스 1부터 시작
                        Course c = courses.get(i);

                        c.isFinish = true;
                        c.isSelectable = true;

                        ///
                        Intent intent = getIntent();
                        c.badgeName = intent.getStringExtra(BADGE_MESSAGE);
                        ///..동작 안함..
                    }
                    if (result.today.position < courses.size()) {
                        Course c = courses.get(result.today.position - 1); // courses 인덱스는 0부터 시작하고 today. position는 1부터 시작하므로
                        c.isSelectable = true;
                        if(result.today.check && c.isSelectable){ // 운동하고 나서 리뉴하게 하는 것
                            c.isFinish = true;
                        }
                    }


                    for (int i = courses.size() - 1; i >= 0; i--) {
                        Course c = courses.get(i);
                        mAdapter.add(c);
                        // 마지막부터 스크롤이 시작되도록 화면이 나옴 // 데이터를 넣고 해야함
                        recyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
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

    private void callMyProfile(){
        try {
            NetworkManager.getInstance().getMyProfile(getApplicationContext(), new NetworkManager.OnResultListener<MyResult>() {
                @Override
                public void onSuccess(Request request, MyResult result) {

                     navigationView = (NavigationView) findViewById(R.id.nav_view);
                    navigationView.setNavigationItemSelectedListener(MainActivity.this);

                    final View headerview = navigationView.getHeaderView(0);   //헤더뷰를 가져와서 버튼 눌리게 하는 코드

                    User user = result.user;

                    PropertyManager.getInstance().setCurationType(result.user.curationId); // 네트워크에서 가져온 큐레이셔타입을 Preference에 저장
                    int a = PropertyManager.getInstance().getCurationType();
                    Toast.makeText(getApplicationContext(), "큐레이션 id" + a, Toast.LENGTH_SHORT).show();


                    TextView textBadgeCount = (TextView)headerview.findViewById(R.id.text_badge_count);
                    textBadgeCount.setText("배지 : " + user.badgeCount + "개");
//                    btn_nav.setText(user.badgeCount);
                    TextView textCurationType = (TextView)headerview.findViewById(R.id.text_curation_type);
                    textCurationType.setText(user.curationName);
                    TextView textUserName = (TextView)headerview.findViewById(R.id.text_user_name);
                    textUserName.setText(user.userName);
                    TextView textHours = (TextView)headerview.findViewById(R.id.text_exercise_hour);
                    textHours.setText(user.userExerciseHours + "분");
                    ImageView imageProfile = (ImageView)headerview.findViewById(R.id.image_profile);
                    if(!TextUtils.isEmpty(user.userProfile)){
                        Glide.with(getApplicationContext()).load(user.userProfile).into(imageProfile);

                    }else {
                        imageProfile.setImageResource(R.drawable.default_friend);
                    }
//
//                    int myWidth = 512;
//                    int myHeight = 384;
//
//                    Glide.with(getApplicationContext()).load(user.userProfile).asBitmap().into(new SimpleTarget<Bitmap>(myWidth, myHeight) {
//                        @Override
//                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
//                             Bitmap originalBit = bitmap;
//                            ///이미지 블러드 처리
//
//                            RenderScript rs = RenderScript.create(getApplicationContext());
////                            Bitmap inBitmap = ((BitmapDrawable) ContextCompat.getDrawable(getApplicationContext(), originalBit).getBitmap();
//                            Bitmap outBitmap = Bitmap.createBitmap(originalBit.getWidth(), originalBit.getHeight(), originalBit.getConfig());
//                            Allocation inAllocation = Allocation.createFromBitmap(rs, bitmap);
//                            Allocation outAllocation = Allocation.createTyped(rs, inAllocation.getType());
//
//                            ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
//
//                            blur.setRadius(25.0f);
//                            blur.setInput(inAllocation);
//                            blur.forEach(outAllocation);
//
//                            outAllocation.copyTo(outBitmap);
//
//                            RelativeLayout relativeLayout = (RelativeLayout) headerview.findViewById(R.id.relative);
//                            Drawable drawable = new BitmapDrawable(outBitmap);
//                            relativeLayout.setBackgroundDrawable(drawable);
//                            ///이미지 블러드 처리
//                        }
//                    });


                    imageProfile.setClickable(true);
                    imageProfile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callGallery();
                        }
                    });

//                    btn_nav.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(MainActivity.this, BadgeActivity.class);
//                            startActivity(intent);
//                        }
//                    });
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
    protected void onResume() {
        super.onResume();
        int propertyProjectId = PropertyManager.getInstance().getProjectId();
        Log.i("projectId", "onRusume property projectId : " + propertyProjectId );
        callProject(propertyProjectId);
        setBackGround();
    }



}
