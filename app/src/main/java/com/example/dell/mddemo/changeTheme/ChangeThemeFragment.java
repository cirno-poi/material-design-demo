package com.example.dell.mddemo.changeTheme;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.dell.mddemo.ColorEvent;
import com.example.dell.mddemo.R;
import com.example.dell.mddemo.base.BaseFragment;
import com.example.dell.mddemo.utils.ColorUtil;
import com.example.dell.mddemo.utils.CommonUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Description：write something
 * <p>
 * Created by Flower.G on 2018/3/21.
 */

public class ChangeThemeFragment extends BaseFragment {

    private static final long DURATION_TIME = 600;//动画持续时间

    @BindView(R.id.background_fl)
    FrameLayout background_fl;
    @BindView(R.id.background_ll)
    LinearLayout background_ll;
    @BindView(R.id.btn_test)
    Button btn_test;
    @BindView(R.id.edt_duration_time)
    EditText edt_duration_time;

    private AppBarLayout main_app_bar_layout;
    private  FrameLayout toolbar_bg;

    private long mDurationTime = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.content_main;
    }

    @Override
    protected void setupView() {

        main_app_bar_layout = getActivity().findViewById(R.id.main_app_bar_layout);
        toolbar_bg = getActivity().findViewById(R.id.main_toolbar_bg);

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_duration_time.getText().toString().equals("") ||
                        edt_duration_time.getText().toString().length() <= 0
                        || edt_duration_time.getText().toString().equals("0")) {
                    mDurationTime = DURATION_TIME;
                } else {
                    mDurationTime = Long.parseLong(edt_duration_time.getText().toString());
                }
                if (CommonUtils.isFastDoubleClick(mDurationTime)) {
                    return;
                }
                int color = Color.parseColor(ColorUtil.random());
                revealAnimTest((int) v.getX() + v.getWidth() / 2,
                        (int) v.getY() + v.getHeight() / 2, color, mDurationTime);
                btn_test.setBackgroundColor(color + 2000);
//                EventBus.getDefault().post(new ColorEvent(color));
            }
        });

    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }

    private float getStartRadius() {
        return (float) btn_test.getHeight() / 2;
    }

    private float getEndRadius() {
        return (float) Math.hypot(background_ll.getWidth(), background_ll.getHeight());
    }


    private Animator revealAnim(int startX, int startY, float startRadius, float endRadius,
                                View frontView, View bgView, final int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            final View fView = frontView;
            final View bView = bgView;

            Animator animator = ViewAnimationUtils.createCircularReveal(fView,
                    startX, startY, startRadius, endRadius);
//            animator.setDuration(mDurationTime);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    fView.setBackgroundColor(color);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    bView.setBackgroundColor(color);
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
                background_ll, background_fl, color);
        Animator animator1 = revealAnim(startX, startY + toolbar_bg.getHeight(), getStartRadius(),
                getEndRadius(), toolbar_bg, main_app_bar_layout, color);

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
