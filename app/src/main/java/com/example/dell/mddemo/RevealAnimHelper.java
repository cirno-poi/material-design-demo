package com.example.dell.mddemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;

/**
 * Description：write something
 *
 * Created by Flower.G on 2018/3/19.
 */


class RevealAnimHelper {

    public static final long DURATION_TIME = 600;//动画持续时间

    private Activity activity;

    private Button btn_test;

    private RelativeLayout background_rl;
    private FrameLayout background_fl;

    private FrameLayout toolbar_bg;//toolbar背景

    private Toolbar toolbar;
    private AppBarLayout app_bar_layout;

    private float startRadius = 0;
    private float endRadius = 0;

    RevealAnimHelper(Activity activity) {
        this.activity = activity;
        if (activity != null) {
            toolbar = (Toolbar) activity.findViewById(R.id.theme_toolbar);
            background_rl = activity.findViewById(R.id.background_rl);
            background_rl.setBackgroundColor(activity.getResources().getColor(R.color.white));

            background_fl = activity.findViewById(R.id.background_fl);
            background_fl.setBackgroundColor(activity.getResources().getColor(R.color.white));

            app_bar_layout = activity.findViewById(R.id.app_bar_layout);
            toolbar_bg = activity.findViewById(R.id.toolbar_bg);
            btn_test = activity.findViewById(R.id.btn_test);
        }
    }

    private float getStartRadius() {
        return (float) btn_test.getHeight() / 2;
    }

    private float getEndRadius() {
        return (float) Math.hypot(background_rl.getWidth(), background_rl.getHeight());
    }


    private Animator revealAnim(int startX, int startY, float startRadius, float endRadius,
                                final View frontView, final View bgView, final int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Animator animator = ViewAnimationUtils.createCircularReveal(frontView,
                    startX, startY, startRadius, endRadius);
//            animator.setDuration(mDurationTime);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    frontView.setBackgroundColor(color);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    bgView.setBackgroundColor(color);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.setInterpolator(new AccelerateInterpolator());
//            animator.start();
            return animator;
        }
        return null;
    }

    void revealAnimTest(int startX, int startY, int color, long durationTime) {
        Animator animator = revealAnim(startX, startY, getStartRadius(), getEndRadius(),
                background_rl, background_fl, color);
        Animator animator1 = revealAnim(startX, startY + toolbar_bg.getHeight(), getStartRadius(),
                getEndRadius(), toolbar_bg, app_bar_layout, color);

        if (animator != null && animator1 != null) {
            if (durationTime <= 0) {
                durationTime = DURATION_TIME;
            }
            animator.setDuration(durationTime);
            animator1.setDuration(durationTime);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animator, animator1);
            animatorSet.start();
        }
    }

}
