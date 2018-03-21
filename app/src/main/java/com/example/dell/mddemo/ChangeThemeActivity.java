package com.example.dell.mddemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dell.mddemo.base.BaseActivity;
import com.example.dell.mddemo.utils.ColorUtil;
import com.example.dell.mddemo.utils.CommonUtils;
import com.example.dell.mddemo.utils.MyViewUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Description：write something
 *
 * Created by Flower.G on 2018/3/19.
 */

public class ChangeThemeActivity extends BaseActivity {

//    private Button btn_test;
    //    private RelativeLayout background_rl;
//    private FrameLayout background_fl;

//    private FrameLayout toolbar_bg;//toolbar背景

//    private Toolbar theme_toolbar;
//    private AppBarLayout app_bar_layout;

    @BindView(R.id.background_rl)
    RelativeLayout background_rl;
    @BindView(R.id.background_fl)
    FrameLayout background_fl;
    @BindView(R.id.btn_test)
    Button btn_test;
    @BindView(R.id.app_bar_layout)
    AppBarLayout app_bar_layout;
    @BindView(R.id.toolbar_bg)
    FrameLayout toolbar_bg;
    @BindView(R.id.theme_toolbar)
    Toolbar theme_toolbar;
    @BindView(R.id.edt_duration_time)
    EditText edt_duration_time;

    private long mDurationTime = 0;

    public static void actionSatrt(Activity activity) {
        Intent intent = new Intent(activity, ChangeThemeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_theme;
    }

    @Override
    protected void setupView() {
        setSupportActionBar(theme_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        theme_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isFastDoubleClick()) {
                    return;
                }
                finish();
            }
        });
        MyViewUtils.setToolbarHeight(this, theme_toolbar);
//        CommonUtils.setToolbarHeight(this, theme_toolbar);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }


        final RevealAnimHelper revealAnimHelper = new RevealAnimHelper(this);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_duration_time.getText().toString().equals("") ||
                        edt_duration_time.getText().toString().length() <= 0
                        || edt_duration_time.getText().toString().equals("0")) {
                    mDurationTime = RevealAnimHelper.DURATION_TIME;
                } else {
                    mDurationTime = Long.parseLong(edt_duration_time.getText().toString());
                }
                if (CommonUtils.isFastDoubleClick(mDurationTime)) {
                    return;
                }
                int color = Color.parseColor(ColorUtil.random());
                revealAnimHelper.revealAnimTest((int) v.getX() + v.getWidth() / 2,
                        (int) v.getY() + v.getHeight() / 2, color, mDurationTime);
                btn_test.setBackgroundColor(color + 2000);
                EventBus.getDefault().post(new ColorEvent(color));
            }
        });

    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

