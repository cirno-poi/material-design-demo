package com.example.dell.mddemo.motion;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.example.dell.mddemo.R;
import com.example.dell.mddemo.base.BaseFragment;
import com.example.dell.mddemo.utils.CommonUtils;

import butterknife.BindView;

/**
 * Description：write something
 * <p>
 * Created by Flower.G on 2018/3/22.
 */

public class MotionFragment extends BaseFragment {

    private long durationTime = 200;

    private int fabX = 0;
    private int fabY = 0;
    private float openStartRadius = 0f;
    private float openEndRadius = 0f;

    @BindView(R.id.motion_ll)
    LinearLayout motion_ll;
    @BindView(R.id.motion_fab)
    FloatingActionButton motion_fab;

    @Override
    protected int getLayoutId() {
        return R.layout.motion_fragment_layout;
    }

    @Override
    protected void setupView() {
        motion_ll.setVisibility(View.INVISIBLE);

        motion_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revelAnim();
            }
        });

        motion_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAnim();
            }
        });
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
    }

    public void revelAnim() {

//        fabX = motion_fab.getLeft();
//        fabY = motion_fab.getTop();

        fabX = motion_fab.getLeft() + motion_fab.getWidth() / 2;
        fabY = motion_fab.getTop() + motion_fab.getHeight() / 2;

        fabX = (int) (motion_fab.getLeft() + motion_fab.getWidth() / 2 - getResources().getDimension(R.dimen.motion_layout_margin));
        fabY = (int) (motion_fab.getTop() + motion_fab.getHeight() / 2 -  getResources().getDimension(R.dimen.motion_layout_margin));
        openStartRadius = motion_fab.getWidth() / 2;
        openEndRadius = (float) Math.hypot(motion_ll.getWidth(), motion_ll.getHeight());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = ViewAnimationUtils.createCircularReveal(motion_ll, fabX,
                    fabY,
                    openStartRadius, openEndRadius);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    motion_ll.setVisibility(View.VISIBLE);
                    motion_fab.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
//                    motion_fab.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
//            animator.setInterpolator(new AccelerateInterpolator());
            animator.setDuration(durationTime);
            animator.start();
        }
    }

    private void hideAnim() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = ViewAnimationUtils.createCircularReveal(motion_ll, fabX,
                    fabY,
                    openEndRadius, openStartRadius);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    motion_ll.setVisibility(View.INVISIBLE);
                    motion_fab.setVisibility(View.VISIBLE);
//                    motion_fab.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
//            animator.setInterpolator(new AccelerateInterpolator());
            animator.setDuration(durationTime);
            animator.start();
        }
    }
}
