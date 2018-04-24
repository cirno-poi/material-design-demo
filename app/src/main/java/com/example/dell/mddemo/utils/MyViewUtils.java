package com.example.dell.mddemo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EdgeEffect;

import java.lang.reflect.Field;

/**
 * Description：Viewc操作工具类
 * <p>
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


    /**
     * 单纯的设置状态栏颜色，与toolbar不关联
     *
     * @param activity activity
     * @param color    颜色
     */
    public static void setStatusBarColor(Activity activity, @ColorInt int color) {

        Window window = activity.getWindow();
        window.setStatusBarColor(color);   //这里修改状态栏为color
    }

    /**
     * 设置状态栏全沉浸，无半透明遮罩，与上面方法不同的是这里color设置透明的话就与toolbar一致了
     *
     * @param activity activity
     * @param color    颜色
     */
    public static void setStatusBarColorTranslucent(Activity activity, @ColorInt int color) {

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


    /**
     * 设置状态栏半透明，不单独设置颜色，颜色与toolbar一致，有半透明遮罩，但是其实也是增加toolbar的paddingTop
     * 需要和setToolbarHeight一起使用
     *
     * @param activity activity
     */
    public static void setStatusBarAlpha(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }


    /**
     * 为滑动返回界面设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */
    public static void setColorForSwipeBack(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            ViewGroup contentView = ((ViewGroup) activity.findViewById(android.R.id.content));
            View rootView = contentView.getChildAt(0);
            int statusBarHeight = getStatusBarHeight(activity);
            if (rootView != null && rootView instanceof CoordinatorLayout) {
                final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) rootView;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    coordinatorLayout.setFitsSystemWindows(false);
                    contentView.setBackgroundColor(color);
                    boolean isNeedRequestLayout = contentView.getPaddingTop() < statusBarHeight;
                    if (isNeedRequestLayout) {
                        contentView.setPadding(0, statusBarHeight, 0, 0);
                        coordinatorLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                coordinatorLayout.requestLayout();
                            }
                        });
                    }
                } else {
                    coordinatorLayout.setStatusBarBackgroundColor(color);
                }
            } else {
                contentView.setPadding(0, statusBarHeight, 0, 0);
                contentView.setBackgroundColor(color);
            }
            setTransparentForWindow(activity);
        }
    }

    /**
     * 设置透明
     */
    private static void setTransparentForWindow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


}
