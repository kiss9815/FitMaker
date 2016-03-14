package com.juntcompany.fitmaker.Friend.Profile;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juntcompany.fitmaker.Data.Badge;
import com.juntcompany.fitmaker.Data.Friend;
import com.juntcompany.fitmaker.Data.Structure.FriendResult;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.R;

import java.io.UnsupportedEncodingException;
import java.util.List;

import okhttp3.Request;

public class FriendProfileActivity extends AppCompatActivity {

    public static final String FRIEND_MESSAGE = "friend_message";

    ImageView imageFriendProfile;
    TextView textFriendExerciseHour, textFriendExctypeName, textFriendName, textFriendBadgeCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        View view = getLayoutInflater().inflate(R.layout.toolbar_friend_profile, null);
        actionBar.setCustomView(view, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));

        imageFriendProfile = (ImageView)findViewById(R.id.image_friend_profile);
        textFriendExerciseHour = (TextView)findViewById(R.id.friend_exercise_hour);
        textFriendExctypeName = (TextView)findViewById(R.id.friend_exctype_name);
        textFriendName = (TextView)findViewById(R.id.friend_name);
        textFriendBadgeCount = (TextView)findViewById(R.id.friend_badge_count);


        Intent intent = getIntent();
        int friendId = intent.getIntExtra(FRIEND_MESSAGE, 0);

        callFriendProfile(friendId);

    }


    private void callFriendProfile(int friendId){
        try {
            NetworkManager.getInstance().getFriendProfile(getApplicationContext(), "" + friendId, new NetworkManager.OnResultListener<FriendResult>() {
                @Override
                public void onSuccess(Request request, FriendResult result) {
                        Friend friend = result.friend;
                        List<Badge> badges = result.friendBadges;
                    Glide.with(getApplicationContext()).load(friend.friendProfile).into(imageFriendProfile);
                    textFriendExerciseHour.setText(""+friend.friendExerciseHour);
                    textFriendExctypeName.setText(friend.curationType);
                    textFriendName.setText(friend.friendName);
                    textFriendBadgeCount.setText(""+badges.size());

                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
