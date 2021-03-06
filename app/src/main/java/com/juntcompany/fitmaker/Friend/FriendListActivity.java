package com.juntcompany.fitmaker.Friend;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.juntcompany.fitmaker.Data.Friend;
import com.juntcompany.fitmaker.Data.Structure.FriendListResult;
import com.juntcompany.fitmaker.Friend.Profile.FriendProfileActivity;
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
    SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);

        View view = getLayoutInflater().inflate(R.layout.toolbar_friend_list, null);
        actionBar.setCustomView(view, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshlayout);


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mAdapter = new FriendListAdapter();
        mAdapter.setOnItemClickListener(new FriendListAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemProfileClick(View view, int position) {
                Friend friend = (Friend) mAdapter.getItem(position);
                ////프로필 보기 버튼 .....
                Intent intent = new Intent(getApplicationContext(), FriendProfileActivity.class);
                intent.putExtra(FriendProfileActivity.FRIEND_MESSAGE, friend.friendId);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setData();
            }
        });
        refreshLayout.setColorSchemeColors(Color.YELLOW, Color.RED, Color.GREEN);

        setData();

       // initData();
    }


    private void setData(){
        try {
            NetworkManager.getInstance().getFriendList(getApplicationContext(), new NetworkManager.OnResultListener<FriendListResult>() {
                @Override
                public void onSuccess(Request request, FriendListResult result) {
                    mAdapter.clear();
                    mAdapter.addAll(result.friends);
                    refreshLayout.setRefreshing(false);
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
