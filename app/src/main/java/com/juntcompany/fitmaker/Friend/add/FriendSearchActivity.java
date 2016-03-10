package com.juntcompany.fitmaker.Friend.add;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.juntcompany.fitmaker.Data.Friend;
import com.juntcompany.fitmaker.Data.Structure.FriendSearchResult;
import com.juntcompany.fitmaker.Data.Structure.Result;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.R;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class FriendSearchActivity extends AppCompatActivity {

    private static final String ACTIVITY_TITLE = "친구 요청";


    RecyclerView recyclerView;
    FriendSearchAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    EditText editSearch;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle(ACTIVITY_TITLE);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mAdapter = new FriendSearchAdapter();
        mAdapter.setOnItemClickListener(new FriendSearchAdapter.OnAdapterItemClickListener() {
            @Override
            public void onAdapterItemAddClick(View view, int position) {
                //...친구 추가....
                Friend friend = (Friend)mAdapter.getItem(position);
                sendFriend(""+friend.friendId); // 네트워크 메소드 사용, 친구에게 친구요청하기
            }
        });

        recyclerView.setAdapter(mAdapter);

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        editSearch = (EditText)findViewById(R.id.edit_search);
         email = editSearch.getText().toString();
        Button btn = (Button)findViewById(R.id.btn_search);
        btn.setOnClickListener(new View.OnClickListener() { //친구 검색
            @Override
            public void onClick(View v) {
                callFriend(email); //서버에서 친구를 부르는 네트워크 메소드 호출
            }
        });


    }

    private void callFriend(String email){
        try {
            NetworkManager.getInstance().searchFriend(getApplicationContext(), email, new NetworkManager.OnResultListener<FriendSearchResult>() {
                @Override
                public void onSuccess(Request request, FriendSearchResult result) {
                    mAdapter.clear();
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

    private void sendFriend(String friendId){
        try {
            NetworkManager.getInstance().sendFriendRequest(getApplicationContext(), friendId, new NetworkManager.OnResultListener<Result>() {
                @Override
                public void onSuccess(Request request, Result result) {
                    //친구 요청 post
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
