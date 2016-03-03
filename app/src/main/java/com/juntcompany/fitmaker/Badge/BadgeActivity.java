package com.juntcompany.fitmaker.Badge;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.BaseAdapter;

import com.juntcompany.fitmaker.Data.Badge;
import com.juntcompany.fitmaker.Main.MainActivity;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.util.MyDecoration;

public class BadgeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BadgeAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mAdapter = new BadgeAdapter();

        recyclerView.setAdapter(mAdapter);

        layoutManager = new GridLayoutManager(getApplicationContext(),3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new MyDecoration(BadgeActivity.this, MyDecoration.GRIDVIEW_VERTICAL_LIST));
        initData();
    }

    private void initData(){
        for(int i = 0; i<14; i ++){
            Badge badge = new Badge();
//            badge.badge_text = "" + i;
            mAdapter.add(badge);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
