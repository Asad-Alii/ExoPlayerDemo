package com.movie.exoplayerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.ui.PlayerView;

public class Main2Activity extends AppCompatActivity {

    private PlayerView playerView;
    private PlayerManager playerManager;

    public static ProgressBar video_spinner;

    String videoUrl = "https://www.radiantmediaplayer.com/media/bbb-360p.mp4";
    //String videoUrl = "https://movie2030.com/2030.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        playerView = findViewById(R.id.playerView);
        video_spinner = findViewById(R.id.video_spinner);

        playerManager = new PlayerManager();

        playerManager.initializePlayer(this, playerView, true);

        playerManager.play(videoUrl);
    }
}
