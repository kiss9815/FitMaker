package com.juntcompany.fitmaker.Friend.request;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.juntcompany.fitmaker.Data.Friend;
import com.juntcompany.fitmaker.Data.Structure.FriendListResult;
import com.juntcompany.fitmaker.Data.Structure.Result;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.R;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

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
        mAdapter.setOnItemClickListener(new FriendRequestAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemAcceptClick(View view, int position) { ///친구 수락하기
                Friend friend = (Friend)mAdapter.getItem(position);
                int friendId = friend.friendId;
                int state = 1;
                acceptFriend(friendId, state);
            }

            @Override
            public void onAdapterItemRejectClick(View view, int position) {
                Friend friend = (Friend)mAdapter.getItem(position);
                int friendId = friend.friendId;
                int state = -1;
                acceptFriend(friendId,state);
            }
        });
        recyclerView.setAdapter(mAdapter);

        layoutManager = new LinearLayoutManager(FriendRequestActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        setData(); // 친구 리스트를 adapter에 추가
    }



    private void setData(){
        try {
            NetworkManager.getInstance().getFriendRequestList(getApplicationContext(), new NetworkManager.OnResultListener<FriendListResult>() {
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

    private void acceptFriend(int friendId, int state){
        try {
            NetworkManager.getInstance().answerFriendRequest(getApplicationContext(),"" + friendId ,"" + state, new NetworkManager.OnResultListener<Result>() {
                @Override
                public void onSuccess(Request request, Result result) {
                    //친구 수락, 거절을 서버에 보냄
                    Toast.makeText(getApplicationContext(), "서버 성공", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {
                    Toast.makeText(getApplicationContext(), "서버 실패", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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
