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
 * Created by dell on 2018/3/19.
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

    /**
     * 设置边缘效应颜色，已作废
     *
     * @param view  view
     * @param color color
     */
    public static void setEdgeGlowColor(@NonNull View view, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                Field edgeTop = View.class.getDeclaredField("mLeftEdge");
                edgeTop.setAccessible(true);
                Field edgeRight = View.class.getDeclaredField("mRightEdge");
                edgeRight.setAccessible(true);
                EdgeEffectCompat ee = (EdgeEffectCompat) edgeTop.get(view);
                if (ee != null) {
                    setEdgeGlowColor(ee, color);
                }
                ee = (EdgeEffectCompat) edgeRight.get(view);
                if (ee != null) {
                    setEdgeGlowColor(ee, color);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void setEdgeGlowColor(@NonNull EdgeEffectCompat edgeEffect, @ColorInt int color) throws Exception {
        if (Build.VERSION.SDK_INT >= 21) {
            Field field = EdgeEffectCompat.class.getDeclaredField("mEdgeEffect");
            field.setAccessible(true);
            EdgeEffect effect = (EdgeEffect) field.get(edgeEffect);
            if (effect != null) {
                effect.setColor(color);
            }
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static void setToolbarHeight(Context context, Toolbar toolbar) {
        //设置标题栏高度沉浸到状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getLayoutParams().height =
                    (int) (context.getResources().getDimension(R.dimen.tools_bar) +
                            CommonUtils.getStatusBarHeight(context));
            toolbar.setPadding(toolbar.getPaddingLeft(),
                    CommonUtils.getStatusBarHeight(context),
                    toolbar.getPaddingRight(),
                    toolbar.getPaddingBottom());
        }
    }

}
