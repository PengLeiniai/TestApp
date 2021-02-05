package com.pl.testapp;

import android.view.View;

public class ViewHelper {
    public static void clear(View v) {
        v.setAlpha(1f);
        v.setScaleY(1f);
        v.setScaleX(1f);
        v.setTranslationY ( 0f);
        v.setTranslationX ( 0f);
        v.setRotation (0f);
        v.setRotationY ( 0f);
        v.setPivotY (v.getMeasuredHeight() / 2f);
        v.setPivotX ( v.getMeasuredWidth() / 2f);
        v.animate().setInterpolator(null).setStartDelay(0);
        }

}
