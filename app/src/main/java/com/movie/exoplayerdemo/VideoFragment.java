package com.movie.exoplayerdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.ui.PlayerView;

public class VideoFragment extends Fragment {

    private PlayerView playerView;
    private PlayerManager playerManager;

    private ProgressBar video_spinner;
    private String url;


    public static VideoFragment newInstance(String url) {

        VideoFragment videoFragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        videoFragment.setArguments(args);
        return videoFragment;
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
        final View view = inflater.inflate(R.layout.video_fragment, container, false);

        playerView = view.findViewById(R.id.playerView);
        video_spinner = view.findViewById(R.id.video_spinner);

        playerManager = new PlayerManager();
        playerManager.getView(video_spinner);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playerManager.initializePlayer(getContext(), playerView, true);
        playerManager.play(url);

    }

    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getContext(), "Paused", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getContext(), "Resumed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        //setUserVisibleHint(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (this.isVisible())
        {
            if (!isVisibleToUser)   // If we are becoming invisible, then...
            {
                //pause or stop video
                playerManager.pausePlayer();
            }

        }
    }
}
