package com.example.dell.mddemo.motion;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dell.mddemo.R;
import com.example.dell.mddemo.base.BaseActivity;
import com.example.dell.mddemo.changeTheme.ChangeThemeActivity;
import com.example.dell.mddemo.utils.CommonUtils;
import com.example.dell.mddemo.utils.MyViewUtils;

import butterknife.BindView;

public class MotionActivity extends BaseActivity implements View.OnClickListener {

    public static final String INTENT_IMAGE = "image";

    @BindView(R.id.motion_toolbar)
    Toolbar motion_toolbar;
    @BindView(R.id.motion_image)
    ImageView motion_image;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MotionActivity.class);
        activity.startActivity(intent);
    }

    public static void actionStart(Activity activity, View view, String sharedName, int imageId) {
        Intent intent = new Intent(activity, MotionActivity.class);
//        activity.startActivity(intent);
        intent.putExtra(INTENT_IMAGE, imageId);
        activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity,
                view, sharedName).toBundle());
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

        int imageId = getIntent().getIntExtra(INTENT_IMAGE, R.mipmap.flandre);
//        Glide.with(this).load(imageId).into(motion_image);
        motion_image.setImageResource(imageId);

//        MyViewUtils.setToolbarHeight(this, motion_toolbar);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.motion_image) {
//            finish();
            onBackPressed();
        }

    }
}
