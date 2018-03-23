package com.example.dell.mddemo.motion;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Description：write something
 * <p>
 * Created by Flower.G on 2018/3/23.
 */

public class BezierAnim {

    public static ValueAnimator creatAnimator(View v, PointF startPoint, PointF endPoint, PointF controlPoint) {

        final View view = v;
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierEvaluator(controlPoint), startPoint, endPoint);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                view.setX(point.x);
                view.setY(point.y);

            }
        });
        return valueAnimator;
    }
}
