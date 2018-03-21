package com.example.dell.mddemo.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EdgeEffect;

import java.lang.reflect.Field;

/**
 * Description：Viewc操作工具类
 *
 * Created by Flower.G on 2018/3/21.
 */

public class MyViewUtils {

    /**
     * 去除的NavigationView的Scrollbar
     *
     * @param navigationView 目标navigationView
     */
    public static void removeNavigationViewScrollbar(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
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
     * @param context 目标上下文
     * @return 高度int
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
        //toolbar.getLayoutParams().height =
        //        (int) (context.getResources().getDimension(R.dimen.tools_bar) +
        //                getStatusBarHeight(context));
            toolbar.setPadding(toolbar.getPaddingLeft(),
                    getStatusBarHeight(context),
                    toolbar.getPaddingRight(),
                    toolbar.getPaddingBottom());
        }
    }

    public static void setStatusBarColor(Activity activity, @ColorInt int color) {

        //沉浸式状态栏，去掉灰色覆盖层
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setNavigationBarColor(mColor);   //这里动态修改底部颜色
        window.setStatusBarColor(color);   //这里修改状态栏为color
    }

}
