package com.example.dell.mddemo.motion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.dell.mddemo.R;
import com.example.dell.mddemo.base.BaseActivity;
import com.example.dell.mddemo.changeTheme.ChangeThemeActivity;
import com.example.dell.mddemo.utils.CommonUtils;
import com.example.dell.mddemo.utils.MyViewUtils;

import butterknife.BindView;

public class MotionActivity extends BaseActivity {

    @BindView(R.id.motion_toolbar)
    Toolbar motion_toolbar;

    public static void actionSatrt(Activity activity) {
        Intent intent = new Intent(activity, MotionActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_motion;
    }

    @Override
    protected void setupView() {
        setSupportActionBar(motion_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        motion_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isFastDoubleClick()) {
                    return;
                }
                finish();
            }
        });
        MyViewUtils.setStatusBarColorTranslucent(this, Color.TRANSPARENT);
//        MyViewUtils.setToolbarHeight(this, motion_toolbar);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }
}
