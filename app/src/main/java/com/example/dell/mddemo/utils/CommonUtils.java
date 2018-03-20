package com.example.dell.mddemo.utils;

import android.app.Activity;
import android.content.Context;

/**
 * Created by dell on 2018/3/19.
 */

public class CommonUtils {

    private static long lastClickTime = 0;

    public final static long DEFAULT_DURATION_TIME = 500;

    //判断距上次点击事件是否在durationTime内
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(DEFAULT_DURATION_TIME);
    }

    //判断距上次点击事件是否在durationTime内
    public static boolean isFastDoubleClick(long durationTime) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < durationTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
