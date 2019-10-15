package com.movie.exoplayerdemo;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class CubeOutRotationTransformation implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {

        page.setPivotX( position < 0f ? page.getWidth() : 0f );
        page.setPivotY( page.getHeight() * 0.5f );
        page.setRotationY( 90f * position );

        /*if (position < -1){    // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setAlpha(0);

        }
        else if (position <= 0) {    // [-1,0]
            page.setAlpha(1);
            page.setPivotX(page.getWidth());
            page.setRotationY(-90 * Math.abs(position));

        }
        else if (position <= 1){    // (0,1]
            page.setAlpha(1);
            page.setPivotX(0);
            page.setRotationY(90 * Math.abs(position));

        }
        else {    // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(0);

        }*/
    }
}
