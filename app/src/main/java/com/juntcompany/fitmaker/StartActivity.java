package com.juntcompany.fitmaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.juntcompany.fitmaker.Curation.CurationActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle("스타트다");

        Button btn = (Button)findViewById(R.id.btn_curation);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, CurationActivity.class));
            }
        });
        btn = (Button)findViewById(R.id.btn_recommend);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, RecommendActivity.class));
            }
        });
    }


}
