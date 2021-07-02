package com.utils;

import android.content.Context;
import android.view.View;

/**
 * 1. dp转px {@link #dp2px(Context, float)}
 * 1. px转dp {@link #px2dp(Context, float)}
 */
public class DimensionUtils {
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }


    public static int[] getMeasureSize(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int witdh = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        return new int[]{witdh, height};
    }
}
