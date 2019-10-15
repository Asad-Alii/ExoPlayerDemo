package com.movie.exoplayerdemo;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class PlayerManager implements AdsMediaSource.MediaSourceFactory, Player.EventListener {

    private DataSource.Factory dataSourceFactory;

    private SimpleExoPlayer player;
    private long contentPosition;

    private View view;

    public void initializePlayer(Context context, PlayerView playerView, boolean b) {

        dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, ""));

        player = ExoPlayerFactory.newSimpleInstance(context);
        playerView.setPlayer(player);
        playerView.setUseController(b);
        player.addListener(this);

    }

    public void play(String contentUrl) {
        if (player != null) {

            MediaSource contentMediaSource = buildMediaSource(Uri.parse(contentUrl));

            player.seekTo(contentPosition);
            player.prepare(contentMediaSource);
            //player.setPlayWhenReady(true);
        }
    }

    @Override
    public MediaSource createMediaSource(Uri uri) {
        return buildMediaSource(uri);
    }

    @Override
    public int[] getSupportedTypes() {
        // IMA does not support Smooth Streaming ads.
        return new int[] {C.TYPE_DASH, C.TYPE_SS, C.TYPE_HLS, C.TYPE_OTHER};
    }

    private MediaSource buildMediaSource(Uri uri) {
        @C.ContentType int type = Util.inferContentType(uri);
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_SS:
                return new SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_OTHER:
                return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            default:
                throw new IllegalStateException("Unsupported type: " + type);
        }
    }

    public void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public void pausePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    public void resumePlayer() {
        if (player != null) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
        }
    }

    public void getView(View view){
        this.view = view;
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case Player.STATE_BUFFERING:
                //VideoFragment.video_spinner.setVisibility(View.VISIBLE);
                view.setVisibility(View.VISIBLE);
                break;
            case Player.STATE_ENDED:
                // Activate the force enable
                break;
            case Player.STATE_IDLE:

                break;
            case Player.STATE_READY:
                //VideoFragment.video_spinner.setVisibility(View.GONE);
                view.setVisibility(View.GONE);

                break;
            default:
                // status = PlaybackStatus.IDLE;
                break;
        }
    }
}
