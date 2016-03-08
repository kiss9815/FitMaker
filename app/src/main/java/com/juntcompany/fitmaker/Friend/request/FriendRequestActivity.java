package com.juntcompany.fitmaker.Friend.request;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.juntcompany.fitmaker.Data.Friend;
import com.juntcompany.fitmaker.R;

public class FriendRequestActivity extends AppCompatActivity {

    private static final String ACTIVITY_TITLE = "친구 수락";

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FriendRequestAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle(ACTIVITY_TITLE);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mAdapter = new FriendRequestAdapter();
        recyclerView.setAdapter(mAdapter);

        layoutManager = new LinearLayoutManager(FriendRequestActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        initData();
    }

    private void initData(){
        for(int i =0; i<6; i ++) {
            Friend friend = new Friend();
            friend.userName = "이름" +i;
            mAdapter.add(friend);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
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
