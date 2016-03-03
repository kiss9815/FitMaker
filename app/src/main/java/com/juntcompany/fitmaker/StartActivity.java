package com.juntcompany.fitmaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.juntcompany.fitmaker.Curation.CurationActivity;
import com.juntcompany.fitmaker.Curation.Recommend.RecommendActivity;
import com.juntcompany.fitmaker.Main.MainActivity;

public class StartActivity extends AppCompatActivity { // 로딩 되고 나오는 페이지로 처음 시작하는 유저만 나오는 페이지

    private static final String ACTIVITY_TITLE = "반갑습니다!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle(ACTIVITY_TITLE);

        Button btn = (Button)findViewById(R.id.btn_curation); // 시작하기 버튼
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, CurationActivity.class));
            }
        });
        btn = (Button)findViewById(R.id.btn_recommend); // 우선은 둘러불게요 ~~
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, RecommendActivity.class));
            }
        });
        btn = (Button)findViewById(R.id.button_test);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }


}
