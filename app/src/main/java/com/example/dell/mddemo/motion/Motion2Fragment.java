package com.example.dell.mddemo.motion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
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

    private static final String TAG = "Motion2Fragment";

    private long durationTime = 200;

    private float openStartRadius = 0f;
    private float openEndRadius = 0f;

    private PointF startPoint;
    private PointF endPoint;
    private PointF controlPoint;
    private PointF centerPoint;

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
                openAnim();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ViewAnimationUtils.createCircularReveal(motion2_ll, (int) centerPoint.x, (int) centerPoint.y,
                    openStartRadius, openEndRadius);
        }
        return null;
    }

    private Animator hideAnim() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ViewAnimationUtils.createCircularReveal(motion2_ll, (int) centerPoint.x,
                    (int) centerPoint.y, openEndRadius, openStartRadius);
        }
        return null;
    }

    /**
     * 开始动画
     */
    private void openAnim() {
        openStartRadius = motion2_fab.getWidth() / 2;
        openEndRadius = (float) Math.hypot(motion2_ll.getWidth() / 2, motion2_ll.getHeight() / 2) + openStartRadius;

        // Coordinates of circle initial point
        final ViewGroup parent = (ViewGroup) motion2_ll.getParent();
        final Rect bounds = new Rect();
        final Rect maskBounds = new Rect();

        motion2_fab.getDrawingRect(bounds);
        motion2_ll.getDrawingRect(maskBounds);
        parent.offsetDescendantRectToMyCoords(motion2_fab, bounds);
        parent.offsetDescendantRectToMyCoords(motion2_ll, maskBounds);

        centerPoint = new PointF(maskBounds.centerX(), maskBounds.centerY());

        final float c0X = bounds.centerX() - maskBounds.centerX();
        final float c0Y = bounds.centerY() - maskBounds.centerY();

        startPoint = new PointF(c0X, c0Y);
        endPoint = new PointF(motion2_ll.getLeft(), motion2_ll.getTop());
        controlPoint = new PointF(endPoint.x, startPoint.y);

        // Put Mask view at circle initial points
        motion2_ll.setX(bounds.left - maskBounds.centerX());
        motion2_ll.setY(bounds.top - maskBounds.centerY());

        motion2_ll.setVisibility(View.VISIBLE);
        motion2_fab.setVisibility(View.GONE);

        final float elevation = motion2_ll.getElevation();
        motion2_ll.setElevation(0);

        ValueAnimator valueAnimator = AnimUtils.createBezierAnimator(motion2_ll, startPoint, endPoint, controlPoint);
        Animator animator = revelAnim();

        valueAnimator.setDuration((long) (durationTime * 0.5));
        animator.setDuration(durationTime);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setInterpolator(new DecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (motion2_ll != null) {
                    motion2_ll.clearAnimation();
                    motion2_ll.setElevation(elevation);
                }
            }

        });
        animatorSet.playTogether(valueAnimator, animator);
        animatorSet.start();
    }

    /**
     * 关闭动画
     */
    private void closeAnim() {
        ValueAnimator pathAnimator = AnimUtils.createBezierAnimator(motion2_ll, endPoint, startPoint, controlPoint);
        Animator animator = hideAnim();
        if (animator != null && pathAnimator != null) {
//            pathAnimator.setDuration(durationTime);
//            animator.setDuration((long) (durationTime));
            animator.setInterpolator(new DecelerateInterpolator());
            pathAnimator.setInterpolator(new AccelerateInterpolator());
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(durationTime);

            final float elevation = motion2_ll.getElevation();
            motion2_ll.setElevation(0);

            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (motion2_ll != null && motion2_fab != null) {
                        motion2_ll.setVisibility(View.GONE);
                        motion2_fab.setVisibility(View.VISIBLE);
                        motion2_ll.setElevation(elevation);
                        motion2_ll.clearAnimation();
                    }
                }
            });
            animatorSet.playTogether(animator, pathAnimator);
            animatorSet.start();
        }
    }
}
