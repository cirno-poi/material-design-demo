package com.example.dell.mddemo.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EdgeEffect;

import com.example.dell.mddemo.R;

import java.lang.reflect.Field;

/**
 * Description：write something
 *
 * Created by Flower.G on 2018/3/19.
 */

public class CommonUtils {

    private static long lastClickTime = 0;

    public final static long DEFAULT_DURATION_TIME = 500;
    public final static long CLICK_DEFAULT_TIME = 1000;

    //判断距上次点击事件是否在1s内
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(CLICK_DEFAULT_TIME);
    }

    //判断距上次点击事件是否在durationTime内
    public static boolean isFastDoubleClick(long clickTime) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < clickTime) {
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
