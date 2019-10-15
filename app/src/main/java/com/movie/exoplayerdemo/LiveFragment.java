package com.movie.exoplayerdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bambuser.broadcaster.BroadcastPlayer;
import com.bambuser.broadcaster.PlayerState;
import com.bambuser.broadcaster.SurfaceViewWithAutoAR;
import com.google.android.exoplayer2.ui.PlayerView;

import okhttp3.OkHttpClient;

public class LiveFragment extends Fragment {

    private static final String APPLICATION_ID = "rt446RntQtb1hIw0boagVA";
    private static final String API_KEY = "ac5yuahlgn70pr70lhsg93vyw";

    final OkHttpClient mOkHttpClient = new OkHttpClient();
    SurfaceView mVideoSurface;
    BroadcastPlayer mBroadcastPlayer;
    MediaController mMediaController;

    private String url;

    public static LiveFragment newInstance(String url) {

        LiveFragment liveFragment = new LiveFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        liveFragment.setArguments(args);
        return liveFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().getString("url") != null) {
            url = getArguments().getString("url");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.live_fragment, container, false);

        mVideoSurface = view.findViewById(R.id.VideoSurfaceView);

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getActionMasked() == MotionEvent.ACTION_UP && mBroadcastPlayer != null && mMediaController != null) {
                    PlayerState state = mBroadcastPlayer.getState();
                    if (state == PlayerState.PLAYING ||
                            state == PlayerState.BUFFERING ||
                            state == PlayerState.PAUSED ||
                            state == PlayerState.COMPLETED) {
                        if (mMediaController.isShowing())
                            mMediaController.hide();
                        else
                            mMediaController.show();
                    } else {
                        mMediaController.hide();
                    }
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initPlayer(url);
    }

    @Override
    public void onPause() {
        super.onPause();
        mOkHttpClient.dispatcher().cancelAll();
        mVideoSurface = null;
        if (mBroadcastPlayer != null)
            mBroadcastPlayer.close();
        mBroadcastPlayer = null;
        if (mMediaController != null)
            mMediaController.hide();
        mMediaController = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        //mVideoSurface = findViewById(R.id.VideoSurfaceView);
        //mPlayerStatusTextView.setText("Loading latest broadcast");
//        getLatestResourceUri();
    }

    private void initPlayer(String resourceUri) {
        if (resourceUri == null) {
            /*if (mPlayerStatusTextView != null)
                mPlayerStatusTextView.setText("Could not get info about latest broadcast");*/
            return;
        }
        if (mVideoSurface == null) {
            // UI no longer active
            return;
        }
        if (mBroadcastPlayer != null)
            mBroadcastPlayer.close();
        mBroadcastPlayer = new BroadcastPlayer(getContext(), resourceUri, APPLICATION_ID, mBroadcastPlayerObserver);
        mBroadcastPlayer.setSurfaceView(mVideoSurface);
        mBroadcastPlayer.load();
    }

    private BroadcastPlayer.Observer mBroadcastPlayerObserver = new BroadcastPlayer.Observer() {
        @Override
        public void onStateChange(PlayerState playerState) {
            /*if (mPlayerStatusTextView != null)
                mPlayerStatusTextView.setText("Status: " + playerState);*/
            if (playerState == PlayerState.PLAYING || playerState == PlayerState.PAUSED || playerState == PlayerState.COMPLETED) {
                if (mMediaController == null && mBroadcastPlayer != null && !mBroadcastPlayer.isTypeLive()) {
                    mMediaController = new MediaController(getContext());
                    mMediaController.setAnchorView(mVideoSurface);
                    mMediaController.setMediaPlayer(mBroadcastPlayer);
                }
                if (mMediaController != null) {
                    mMediaController.setEnabled(true);
                    mMediaController.show();
                }
            } else if (playerState == PlayerState.ERROR || playerState == PlayerState.CLOSED) {
                if (mMediaController != null) {
                    mMediaController.setEnabled(false);
                    mMediaController.hide();
                }
                mMediaController = null;
            }
        }
        @Override
        public void onBroadcastLoaded(boolean live, int width, int height) {
        }
    };
}
