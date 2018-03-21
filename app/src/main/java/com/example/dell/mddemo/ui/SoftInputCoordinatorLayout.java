package com.example.dell.mddemo.ui;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.WindowInsets;

/**
 * Description：重新CoordinatorLayout的onApplyWindowInsets()方法在fitSystemWindows=“true”时不为子View添加padding
 *
 * Created by Flower.G on 2018/3/21.
 */

public class SoftInputCoordinatorLayout extends CoordinatorLayout {
    public SoftInputCoordinatorLayout(Context context) {
        super(context);
    }

    public SoftInputCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SoftInputCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    protected boolean fitSystemWindows(Rect insets) {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            insets.left = 0;
//            insets.top = 0;
//            insets.right = 0;
//        }
//        return super.fitSystemWindows(insets);
//    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return super.onApplyWindowInsets(insets.replaceSystemWindowInsets(0, 0, 0, insets.getSystemWindowInsetBottom()));
        } else {
            return insets;
        }
    }
}
