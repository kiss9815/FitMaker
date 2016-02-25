package com.juntcompany.fitmaker.SpecificCurriculum;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.juntcompany.fitmaker.Main.MainActivity;
import com.juntcompany.fitmaker.R;


public class SpecificCurriculumActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager;
    SpecificCurriculumViewPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_curriculum);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle("추천 커리큘럼");

        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager)findViewById(R.id.pager);
        mAdapter = new SpecificCurriculumViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.removeAllTabs();

        tabLayout.addTab(tabLayout.newTab().setText("초급자"));
        tabLayout.addTab(tabLayout.newTab().setText("중급자"));
        tabLayout.addTab(tabLayout.newTab().setText("상급자"));


        Button btn = (Button)findViewById(R.id.btn_start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
