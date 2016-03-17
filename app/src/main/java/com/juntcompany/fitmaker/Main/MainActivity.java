package com.juntcompany.fitmaker.Main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.CornerPathEffect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.juntcompany.fitmaker.Badge.BadgeActivity;
import com.juntcompany.fitmaker.Curation.CurationActivity;
import com.juntcompany.fitmaker.Curation.Recommend.RecommendActivity;
import com.juntcompany.fitmaker.Data.Project;
import com.juntcompany.fitmaker.Data.ProjectResponseResult;
import com.juntcompany.fitmaker.Data.Structure.MyResult;
import com.juntcompany.fitmaker.Data.User;
import com.juntcompany.fitmaker.Friend.FriendListActivity;
import com.juntcompany.fitmaker.Main.CourseDialog.CourseDialogFragment;
import com.juntcompany.fitmaker.Data.Course;
import com.juntcompany.fitmaker.Main.MainSpinner.MainSpinnerAdapter;
import com.juntcompany.fitmaker.Main.NextTime.NextTimeDialogFragment;
import com.juntcompany.fitmaker.Main.PlayButton.PlayButtonAdapter;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.Manager.PropertyManager;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.Main.RankingDialog.RankingDialogFragment;
import com.juntcompany.fitmaker.Setting.SettingActivity;
import com.juntcompany.fitmaker.util.OnItemClickListener;

import java.io.File;
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


    List<Course> courses = new ArrayList<Course>();

    int projectId; // 스피너에서 사용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        View view = getLayoutInflater().inflate(R.layout.toolbar_main, null);
        actionBar.setCustomView(view, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));


//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinString);
        spinnerAdapter = new MainSpinnerAdapter();
        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Project p = (Project)parent.getItemAtPosition(position);

                callProject(p.projectId); // 프로젝트 아이디를 부르면 해당 프로젝트를 다 가져옴
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                }
                else if(course.isFinish == true){
                   // recyclerView.scrollToPosition(); // today 인덱스로 이동
                }else { //빨간색을 누른경우

                    CourseDialogFragment f = new CourseDialogFragment();
                    Bundle args = new Bundle();
                    args.putInt(CourseDialogFragment.PROJECT_ID_MESSAGE, projectId);
                    args.putSerializable(CourseDialogFragment.COURSE_DIALOG_MESSAGE, course);
                    f.setArguments(args);
                    f.show(getSupportFragmentManager(), MAIN_DIALOG_TAG);
                }
            }
        });

                recyclerView.setAdapter(mAdapter);

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        Intent intent = getIntent(); //SpecificActivity에서 넘어옴
        projectId =intent.getIntExtra(INTENT_PROJECT_ID, 100); //0은 default value // 프로젝트 id를 넘겨받음. main에서 다시 서버에 요청해야 함
        //String projectName = intent.getStringExtra(INTENT_PROJECT_NAME);
        callProject(projectId); //전 페이지에서 해당 페이지로 넘어올때 생성된 프로젝트 id로 커리큘럼과 코스 생성.
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
            case R.id.main_menu_picture_change:
                callGallery();
                break;

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
                    Uri fileUri = Uri.fromFile(new File(path));
                   // Glide.with(this).load(fileUri).into();
                }
//                Glide.with(this).load(mFileUri).into(photoView);
            }
        }
    }

    private static final int RC_GALLERY = 1;
    private void callGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");

        //   intent.putExtra(MediaStore.EXTRA_OUTPUT, getFileUri()); // 크롭이 아닌경우는 의미가 없음
        startActivityForResult(intent, RC_GALLERY);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {   /////네비게이션 선택
        int id = item.getItemId();

        if (id == R.id.nav_exercise_play) {
            // Handle the camera action
            //...오늘 날짜로 저절로 클릭되게 하기........
        } else if (id == R.id.nav_recommend_curriculum) {
            Intent intent = new Intent(getApplicationContext(), RecommendActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_friend_list) {
            Intent intent = new Intent(getApplicationContext(), FriendListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_my_style) {  // 큐레이션 액티비티의 큐레이션 결과 로 가야함
            Intent intent = new Intent(getApplicationContext(), CurationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_setting_curation) {
            Intent intent = new Intent(MainActivity.this, CurationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_ongoing_project) {
                //..프로젝트 하고 있는 리스트가 나오기.....
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
            NetworkManager.getInstance().getProject(MainActivity.this, "" + projectId, new NetworkManager.OnResultListener<ProjectResponseResult>() {
                @Override
                public void onSuccess(Request request, ProjectResponseResult result) {
                     List<Project> projects = result.projects;
                    spinnerAdapter.clear();
                    spinnerAdapter.addAll(projects);
                    mAdapter.clear();
                    courses = result.courses;
                    // ....

                    Log.i("aaa", courses.toString()) ;
                    boolean flag = true;
                    for (int i = 0 ; i < result.today.position; i++) { // position이 완료된 날//
                        Course c = courses.get(i);
                        c.isFinish = true;
                        c.isSelectable = true;
                        ///
                        Intent intent = getIntent();
                        c.badgeName = intent.getStringExtra(BADGE_MESSAGE);
                        ///..동작 안함..
                    }
                    if (result.today.position < courses.size()) {
                        Course c = courses.get(result.today.position); // courses 인덱스는 0부터 시작하고 today는 1부터 시작하므로
                        c.isSelectable = true;
                    }


//                    for(int i = 0; i < courses.size(); i++) { //리스트뷰는 위에서 부터 채워지기 때문에 거꾸로 인덱스 시작
//                        Course course = courses.get(i);
//                        course.isSelectable = flag;
//                        if(i+1 == result.today.position) {
//                            course.isFinish = result.today.check;
//                        }
//                        flag = course.isFinish;
//
//                    }

                    for (int i = courses.size() - 1 ; i >= 0; i--) {
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

                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    navigationView.setNavigationItemSelectedListener(MainActivity.this);

                    View headerview = navigationView.getHeaderView(0);   //헤더뷰를 가져와서 버튼 눌리게 하는 코드

                    User user = result.user;

                    PropertyManager.getInstance().setCurationType(result.user.curationId); // 네트워크에서 가져온 큐레이셔타입을 Preference에 저장

                    TextView btn_nav = (TextView)headerview.findViewById(R.id.btn_nav_badge);
//                    btn_nav.setText(user.badgeCount);
                    TextView textCurationType = (TextView)headerview.findViewById(R.id.text_curation_type);
                    textCurationType.setText(user.curationName);
                    TextView textUserName = (TextView)headerview.findViewById(R.id.text_user_name);
                    textUserName.setText(user.userName);
                    TextView textHours = (TextView)headerview.findViewById(R.id.text_exercise_hour);
                    textHours.setText("" + user.userExerciseHours);
                    ImageView imageProfile = (ImageView)headerview.findViewById(R.id.image_profile);

                    Glide.with(getApplicationContext()).load(user.userProfile).into(imageProfile);

                    imageProfile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //......내 프로필 이미지를 눌렀을 때 처리.... 아직 생각중....

                        }
                    });

                    btn_nav.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, BadgeActivity.class);
                            startActivity(intent);
                        }
                    });
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
