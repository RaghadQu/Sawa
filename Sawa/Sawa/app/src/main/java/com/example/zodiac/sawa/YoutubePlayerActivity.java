package com.example.zodiac.sawa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YoutubePlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    EditText editText;
    public static String api_key = "AIzaSyAa3QEuITB2WLRgtRVtM3jZwziz9Fc5EV4";
    public String video_id = "rzLKwtC5q1k";
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtune);
        youTubePlayerView.setVisibility(View.INVISIBLE);
        editText = (EditText) findViewById(R.id.edit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void afterTextChanged(Editable editable) {

                String pattern = "https://m.youtube.com/watch?v=";
                String s = String.valueOf(editText.getText());
                int i = s.indexOf(pattern);
                Log.d("II", "" + i);

            /*    if (i == 0) {
                    String[] split = s.split("v=");
                    video_id = split[1];
                    youTubePlayerView.setVisibility(View.VISIBLE);
                    youTubePlayerView.initialize(api_key, YoutubePlayerActivity.this);
                } else {
                    youTubePlayerView.setVisibility(View.INVISIBLE);

                }*/


            }
        });

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        youTubePlayer.cueVideo(video_id);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
    private PlaybackEventListener playbackEventListener = new PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    boolean isEmailValid(CharSequence c) {
        Log.d("isEmailValid", "");
        String pattern = "https://www.youtube.com/watch?v=";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(c);
        boolean b = m.matches();
        if (b) {
            return true;
        } else {
            return false;
        }

    }

}
