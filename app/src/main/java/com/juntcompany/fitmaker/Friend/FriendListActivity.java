package com.juntcompany.fitmaker.Friend;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.juntcompany.fitmaker.Data.Friend;
import com.juntcompany.fitmaker.Data.Structure.FriendListResult;
import com.juntcompany.fitmaker.Friend.add.FriendSearchActivity;
import com.juntcompany.fitmaker.Friend.request.FriendRequestActivity;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.R;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import okhttp3.Request;

public class FriendListActivity extends AppCompatActivity {   // MainActivity의 메뉴에서 들어옴

    private static final String ACTIVITY_TITLE = "친구 목록";


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FriendListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle(ACTIVITY_TITLE);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mAdapter = new FriendListAdapter();
        mAdapter.setOnItemClickListener(new FriendListAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemProfileClick(View view, int position) {
                Friend friend = (Friend) mAdapter.getItem(position);
                ////프로필 보기 버튼 .....
            }
        });
        recyclerView.setAdapter(mAdapter);

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        setData();

       // initData();
    }



    private void setData(){
        try {
            NetworkManager.getInstance().getFriendList(getApplicationContext(), new NetworkManager.OnResultListener<FriendListResult>() {
                @Override
                public void onSuccess(Request request, FriendListResult result) {
                    mAdapter.addAll(result.friends);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_friend_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
                finish();
                break;
            }
            case R.id.menu_friend_request:{
                Intent intent = new Intent(FriendListActivity.this, FriendRequestActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_friend_plus: {
                Intent intent = new Intent(FriendListActivity.this, FriendSearchActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
