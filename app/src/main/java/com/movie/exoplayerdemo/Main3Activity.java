package com.movie.exoplayerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {
    
    //View Pager
    private ViewPager view_pager;
    private VideoAdapter videoAdapter;
    private ArrayList<String> arrayList;
    private PlayerManager playerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        playerManager = new PlayerManager();
        view_pager = findViewById(R.id.view_pager);
        view_pager.setPageTransformer(true, new CubeOutRotationTransformation());
        view_pager.setOffscreenPageLimit(5);
        arrayList = new ArrayList<>();

        arrayList.add("https://www.radiantmediaplayer.com/media/bbb-360p.mp4");
        arrayList.add("https://www.radiantmediaplayer.com/media/bbb-360p.mp4");
        arrayList.add("https://www.radiantmediaplayer.com/media/bbb-360p.mp4");
        arrayList.add("https://www.radiantmediaplayer.com/media/bbb-360p.mp4");
        arrayList.add("https://www.radiantmediaplayer.com/media/bbb-360p.mp4");
        videoAdapter = new VideoAdapter(getSupportFragmentManager(), 0, this, arrayList);
        view_pager.setAdapter(videoAdapter);

        view_pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }
}
