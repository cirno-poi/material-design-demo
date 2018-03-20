package com.example.dell.mddemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dell.mddemo.utils.ColorUtil;
import com.example.dell.mddemo.utils.CommonUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button btn_test;
    private Button btn_test_1;
    private RelativeLayout background_rl;
    private FrameLayout background_fl;

    private FrameLayout toolbar_bg;//toolbar背景

    private Toolbar toolbar;
    private AppBarLayout app_bar_layout;

    private long mDurationTime = 800;//动画持续时间

//    private int touchX = 0;
//    private int touchY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        AndroidBug5497Workaround.assistActivity(findViewById(R.id.background_fl));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * 设置标题栏高度沉浸到状态栏
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getLayoutParams().height = CommonUtils.dip2px(this, 56) +
                    getStatusBarHeight();
            toolbar.setPadding(toolbar.getPaddingLeft(),
                    getStatusBarHeight(),
                    toolbar.getPaddingRight(),
                    toolbar.getPaddingBottom());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        background_rl = findViewById(R.id.background_rl);
        background_rl.setBackgroundColor(getResources().getColor(R.color.white));

        background_fl = findViewById(R.id.background_fl);
        background_fl.setBackgroundColor(getResources().getColor(R.color.white));

        app_bar_layout = findViewById(R.id.app_bar_layout);
        toolbar_bg = findViewById(R.id.toolbar_bg);

        /**
         * 沉浸式状态栏，去掉灰色覆盖层
         */
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setNavigationBarColor(mColor);   //这里动态修改底部颜色
        window.setStatusBarColor(Color.TRANSPARENT);   //这里动态修改状态栏颜色


        btn_test = findViewById(R.id.btn_test);

        final MainAnimHelper mainAnimHelper = new MainAnimHelper(this);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isFastDoubleClick(MainAnimHelper.DURATION_TIME)) {
                    return;
                }
//                animTest((int) v.getX() + v.getWidth() / 2, (int) v.getY() + v.getHeight() / 2);
                int color = Color.parseColor(ColorUtil.random());
                mainAnimHelper.revealAnimTest((int) v.getX() + v.getWidth() / 2,
                        (int) v.getY() + v.getHeight() / 2, color);
                btn_test.setBackgroundColor(color + 2000);
            }
        });


    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Toast.makeText(this,"clicked camera",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    private void animTest(int startX, int startY) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            //开始与结束时的半径
//            float startRadius = (float) btn_test.getHeight() / 2;
//            float endRadius = (float) Math.hypot(background_rl.getWidth(), background_rl.getHeight());
//
//            final int mColor = Color.parseColor(ColorUtil.random());
//
//            btn_test.setBackgroundColor(mColor + 2000);
//
//            Animator animator = ViewAnimationUtils.createCircularReveal(background_rl,
//                    startX, startY, startRadius, endRadius);
//            animator.setDuration(mDurationTime);
//            animator.addListener(new Animator.AnimatorListener() {
//                @Override
//                public void onAnimationStart(Animator animation) {
//                    background_rl.setBackgroundColor(mColor);
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    background_fl.setBackgroundColor(mColor);
//                }
//
//                @Override
//                public void onAnimationCancel(Animator animation) {
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animator animation) {
//
//                }
//            });
//            animator.setInterpolator(new AccelerateInterpolator());
////            animator.start();
//
//            Animator animator1 = ViewAnimationUtils.createCircularReveal(toolbar_bg,
//                    startX, startY + toolbar_bg.getHeight(), startRadius, endRadius);
//
//            animator1.setDuration(mDurationTime);
//            animator1.addListener(new Animator.AnimatorListener() {
//                @Override
//                public void onAnimationStart(Animator animation) {
//                    toolbar_bg.setBackgroundColor(mColor);
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    app_bar_layout.setBackgroundColor(mColor);
//                }
//
//                @Override
//                public void onAnimationCancel(Animator animation) {
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animator animation) {
//
//                }
//            });
//            animator1.setInterpolator(new AccelerateInterpolator());
//
//
//            AnimatorSet animatorSet = new AnimatorSet();
//            animatorSet.playTogether(animator, animator1);
//            animatorSet.start();
//        }
//    }
}
