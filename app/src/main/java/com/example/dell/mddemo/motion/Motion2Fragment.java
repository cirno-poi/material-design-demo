package com.example.dell.mddemo.motion;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.example.dell.mddemo.R;
import com.example.dell.mddemo.base.BaseFragment;

import butterknife.BindView;

/**
 * Description：write something
 * <p>
 * Created by Flower.G on 2018/3/22.
 */

public class Motion2Fragment extends BaseFragment {

    private long durationTime = 200;

    private int fabX = 0;
    private int fabY = 0;
    private float openStartRadius = 0f;
    private float openEndRadius = 0f;

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
//                revelAnim();
                arcAnim(v);
            }
        });

        motion2_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                hideAnim();
            }
        });
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
    }

    public void revelAnim() {

//        fabX = motion2_fab.getLeft();
//        fabY = motion2_fab.getTop();

        fabX = motion2_fab.getLeft() + motion2_fab.getWidth() / 2;
        fabY = motion2_fab.getTop() + motion2_fab.getHeight() / 2;

        fabX = (int) (motion2_fab.getLeft() + motion2_fab.getWidth() / 2 - getResources().getDimension(R.dimen.motion_layout_margin));
        fabY = (int) (motion2_fab.getTop() + motion2_fab.getHeight() / 2 - getResources().getDimension(R.dimen.motion_layout_margin));
        openStartRadius = motion2_fab.getWidth() / 2;
        openEndRadius = (float) Math.hypot(motion2_ll.getWidth(), motion2_ll.getHeight());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = ViewAnimationUtils.createCircularReveal(motion2_ll, fabX,
                    fabY,
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
//            animator.setInterpolator(new AccelerateInterpolator());
            animator.setDuration(durationTime);
            animator.start();
        }
    }

    private void hideAnim() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = ViewAnimationUtils.createCircularReveal(motion2_ll, fabX,
                    fabY,
                    openEndRadius, openStartRadius);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    motion2_ll.setVisibility(View.INVISIBLE);
                    motion2_fab.setVisibility(View.VISIBLE);
//                    motion2_fab.setVisibility(View.VISIBLE);
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

    /**
     * 圆弧动画
     *
     * @param v 目标view
     */
    public void arcAnim(View v) {
        final View view = v;
        final float fstartX = view.getLeft();
        final float fstartY = view.getTop();
        PointF startP = new PointF(fstartX, fstartY);

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(durationTime);
        valueAnimator.setObjectValues(new PointF(fstartX, fstartY));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            // fraction = t / duration
            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue) {
//                Log.e(TAG, fraction * 3 + "");
                // x方向200px/s ，则y方向0.5 * 10 * t
                PointF point = new PointF();
                double t = fraction * durationTime;
                point.x = (float) (fstartX - t);
                point.y = (float) (fstartY - Math.sqrt(300 * 300 - Math.pow(t, 2)));
                return point;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                view.setX(point.x);
                view.setY(point.y);

            }
        });
    }
}
