package com.example.dell.mddemo.motion;

import android.animation.TypeEvaluator;
import android.graphics.Point;
import android.graphics.PointF;

/**
 * Description：二阶贝塞尔曲线
 * <p>
 * Created by Flower.G on 2018/3/23.
 */

public class BezierEvaluator implements TypeEvaluator<PointF> {


    private PointF controlPoint;

    public BezierEvaluator(PointF controlPoint) {
        this.controlPoint = controlPoint;
    }

    @Override
    public PointF evaluate(float t, PointF startValue, PointF endValue) {

        float x = (float) ((1 - t) * (1 - t) * startValue.x + 2 * t * (1 - t) * controlPoint.x + t * t * endValue.x);
        float y = (float) ((1 - t) * (1 - t) * startValue.y + 2 * t * (1 - t) * controlPoint.y + t * t * endValue.y);
        return new PointF(x, y);
    }
}
