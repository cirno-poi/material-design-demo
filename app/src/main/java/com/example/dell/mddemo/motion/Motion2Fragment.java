package com.example.dell.mddemo.motion;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.example.dell.mddemo.R;
import com.example.dell.mddemo.base.BaseFragment;
import com.example.dell.mddemo.utils.AnimUtils;
import com.example.dell.mddemo.utils.CommonUtils;

import butterknife.BindView;

/**
 * Description：write something
 * <p>
 * Created by Flower.G on 2018/3/22.
 */

public class Motion2Fragment extends BaseFragment {

    private long durationTime = 300;

    private int fabX = 0;
    private int fabY = 0;
    private float openStartRadius = 0f;
    private float openEndRadius = 0f;

    private PointF startPoint;
    private PointF endPoint;
    private PointF controlPoint;

    @BindView(R.id.motion2_ll)
    LinearLayout motion2_ll;
    @BindView(R.id.motion2_fab)
    FloatingActionButton motion2_fab;

    @Override
    protected int getLayoutId() {
        return R.layout.motion2_fragment_layout;
    }

    @Override
    protected void setupView() {
        motion2_ll.setVisibility(View.INVISIBLE);

        motion2_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isFastDoubleClick(durationTime)) {
                    return;
                }
//                revelAnim();
                openAnim(v);
            }
        });

        motion2_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isFastDoubleClick(durationTime)) {
                    return;
                }
//                hideAnim();
                closeAnim();
            }
        });
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
    }

    public Animator revelAnim() {

        fabX = (int) (motion2_fab.getLeft() + motion2_fab.getWidth() / 2 - getResources().getDimension(R.dimen.motion_layout_margin));
        fabY = (int) (motion2_fab.getTop() + motion2_fab.getHeight() / 2 - getResources().getDimension(R.dimen.motion_layout_margin));
        openStartRadius = motion2_fab.getWidth() / 2;
        openEndRadius = (float) Math.hypot(motion2_ll.getWidth(), motion2_ll.getHeight());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = ViewAnimationUtils.createCircularReveal(motion2_ll, (int) (endPoint.x + getResources().getDimension(R.dimen.motion_layout_margin)),
                    (int) (endPoint.y + getResources().getDimension(R.dimen.motion_layout_margin)),
                    openStartRadius, openEndRadius);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    motion2_ll.setVisibility(View.VISIBLE);
                    motion2_fab.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
//                    motion2_fab.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            return animator;
        }
        return null;
    }

    private Animator hideAnim() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = ViewAnimationUtils.createCircularReveal(motion2_ll, (int) (endPoint.x + getResources().getDimension(R.dimen.motion_layout_margin)),
                    (int) (endPoint.y + getResources().getDimension(R.dimen.motion_layout_margin)),
                    openEndRadius, openStartRadius);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    motion2_ll.setVisibility(View.INVISIBLE);
                    motion2_fab.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            return animator;
        }
        return null;
    }

    /**
     * 开始动画
     *
     * @param v 目标view
     */
    private void openAnim(View v) {
        final float fstartX = v.getLeft();
        final float fstartY = v.getTop();
        startPoint = new PointF(fstartX, fstartY);
        controlPoint = new PointF(motion2_ll.getLeft() + motion2_ll.getWidth() / 2, fstartY);
        endPoint = new PointF(motion2_ll.getLeft() + motion2_ll.getWidth() / 2 - motion2_fab.getWidth() / 2,
                motion2_ll.getTop() + motion2_ll.getHeight() / 2 - motion2_fab.getHeight() / 2);
        ValueAnimator valueAnimator = AnimUtils.creatBezierAnimator(v, startPoint, endPoint, controlPoint);
        Animator animator = revelAnim();

        valueAnimator.setDuration(durationTime / 2);
        animator.setDuration(durationTime / 2);
        animator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(valueAnimator, animator);
        animatorSet.start();
    }

    /**
     * 关闭动画
     */
    private void closeAnim() {
        ValueAnimator valueAnimator = AnimUtils.creatBezierAnimator(motion2_fab, endPoint, startPoint, controlPoint);
        Animator animator = hideAnim();
        if (animator != null && valueAnimator != null) {
            valueAnimator.setDuration(durationTime / 2);
            animator.setDuration(durationTime / 2);
            animator.setInterpolator(new AccelerateInterpolator());
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(animator, valueAnimator);
            animatorSet.start();
        }
    }
}
