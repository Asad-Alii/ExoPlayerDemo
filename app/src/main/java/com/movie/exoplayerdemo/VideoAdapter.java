package com.movie.exoplayerdemo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class VideoAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private ArrayList<String> arrayList ;

    public VideoAdapter(FragmentManager fm, int behavior, Context context, ArrayList<String> arrayList) {
        super(fm, behavior);
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return VideoFragment.newInstance(arrayList.get(position));
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
}
