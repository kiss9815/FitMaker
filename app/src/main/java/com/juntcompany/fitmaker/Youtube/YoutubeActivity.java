package com.juntcompany.fitmaker.Youtube;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.juntcompany.fitmaker.R;

public class YoutubeActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerSupportFragment fragment;
    private static final String DEVELOPER_KEY = "AIzaSyBD-HF6LpFUKCZ_3P2L-QPVKvUNmjG9XqM";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    public static final String CONTENT_YOUTUBE_MESSAGE = "content_youtube_message";

    String youtube;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        fragment = (YouTubePlayerSupportFragment)getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        fragment.initialize(DEVELOPER_KEY, this);

        Intent intent = getIntent();
        youtube = intent.getStringExtra(CONTENT_YOUTUBE_MESSAGE);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo("zHMPpb8_H-E");
            //0fzg1kL0Y2U
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            YouTubePlayerSupportFragment fragment = this.fragment;
            fragment.initialize(DEVELOPER_KEY, this);
        }
    }


}

