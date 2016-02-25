package com.juntcompany.fitmaker.Curation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.juntcompany.fitmaker.Data.Curriculum;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.SpecificCurriculum.SpecificCurriculumActivity;

public class RecommendActivity extends AppCompatActivity {


    ListView listView;
    CurriculumAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle("추천커리큘럼");

        listView = (ListView)findViewById(R.id.listView_recommend);
        mAdapter = new CurriculumAdapter();
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Curriculum c = (Curriculum)listView.getItemAtPosition(position);
                /////
                Intent intent = new Intent(RecommendActivity.this, SpecificCurriculumActivity.class);
                startActivity(intent);
            }
        });

        for(int i =0; i<5; i++){
            Curriculum curriculum = new Curriculum();
            mAdapter.add(curriculum);
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
