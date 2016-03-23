package com.juntcompany.fitmaker.play.result.reward;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juntcompany.fitmaker.Data.Badge;
import com.juntcompany.fitmaker.Data.Structure.BadgeResult;
import com.juntcompany.fitmaker.Main.MainActivity;
import com.juntcompany.fitmaker.Manager.NetworkManager;
import com.juntcompany.fitmaker.R;

import java.io.UnsupportedEncodingException;

import okhttp3.Request;

public class RewardActivity extends AppCompatActivity {

    private static final String ACTIVITY_TITLE = "미션 완료";
    public static final String BADGE_ID_MESSAGE = "badge_id_message";

    ImageView image_badge, image_reward_card;
    TextView textBadgeComment;
    Badge badge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        View view = getLayoutInflater().inflate(R.layout.toolbar_reward, null);
        actionBar.setCustomView(view, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        image_badge = (ImageView)findViewById(R.id.image_badge);
        image_reward_card = (ImageView)findViewById(R.id.image_reward_card);
        textBadgeComment = (TextView)findViewById(R.id.text_reward_coment);

        Intent intent = getIntent();
        int badgeId = intent.getIntExtra(BADGE_ID_MESSAGE, 0);
        setBadgeData(badgeId);


        Button btn = (Button)findViewById(R.id.btn_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                //intent.putExtra(MainActivity.BADGE_MESSAGE, badge.badgeName);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setBadgeData(int badgeId){
        try {
            NetworkManager.getInstance().getBadge(getApplicationContext(), ""+ badgeId, new NetworkManager.OnResultListener<BadgeResult>() {
                @Override
                public void onSuccess(Request request, BadgeResult result) {
                    if(result.badge != null) {
                        badge = result.badge;
                        textBadgeComment.setText(badge.badgeName);

                        Glide.with(getApplicationContext()).load(badge.badgeImage).into(image_badge);

                        Log.i("aaa", badge.badgeName);
                    }

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
