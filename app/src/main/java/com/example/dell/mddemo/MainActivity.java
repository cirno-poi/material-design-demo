package com.example.dell.mddemo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.dell.mddemo.base.BaseActivity;
import com.example.dell.mddemo.base.BaseFragment;
import com.example.dell.mddemo.changeTheme.ChangeThemeActivity;
import com.example.dell.mddemo.home.HomeFragment;
import com.example.dell.mddemo.motion.Motion2Fragment;
import com.example.dell.mddemo.motion.MotionActivity;
import com.example.dell.mddemo.motion.MotionFragment;
import com.example.dell.mddemo.utils.MyViewUtils;
import com.jaeger.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Description：write something
 * <p>
 * Created by Flower.G on 2018/3/19.
 */

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = "233333";

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @BindView(R.id.nav_view)
    NavigationView nav_view;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.home_tabLayout)
    TabLayout home_tabLayout;
    @BindView(R.id.main_app_bar_layout)
    AppBarLayout main_app_bar_layout;

    private long lastClickTime = 0;
    FragmentManager fragmentManager = getSupportFragmentManager();

    private BaseFragment mCurrentFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupView() {
        setSupportActionBar(toolbar);
//        MyViewUtils.setToolbarHeight(this, toolbar);
        MyViewUtils.setStatusBarAlpha(this);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        home_tabLayout.addTab(home_tabLayout.newTab().setText("Tab 1"));
        home_tabLayout.addTab(home_tabLayout.newTab().setText("Tab 2"));
        home_tabLayout.addTab(home_tabLayout.newTab().setText("Tab 3"));

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        MyViewUtils.removeNavigationViewScrollbar(navigationView);
        navigationView.getMenu().getItem(0).setChecked(true);

        mCurrentFragment = new HomeFragment();
        replaceFragment(mCurrentFragment);

//        if (mCurrentFragment instanceof HomeFragment) {
//            Log.d(TAG, "当前fragment是: HomeFragment");
//        }
//            if (fragmentList.get(0) instanceof HomeFragment) {
//                Log.d("233333", "当前fragment为HomeFragment ");
//            }


        EventBus.getDefault().register(this);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() - lastClickTime > 2000) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.double_click_exit),
                        Toast.LENGTH_SHORT).show();
                lastClickTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
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
            mCurrentFragment = new HomeFragment();
            replaceFragment(mCurrentFragment);
            fab.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_gallery) {
            mCurrentFragment = new MotionFragment();
            replaceFragment(mCurrentFragment);
//            MotionActivity.actionStart(this);
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_slideshow) {
            mCurrentFragment = new Motion2Fragment();
            replaceFragment(mCurrentFragment);
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_manage) {
            ChangeThemeActivity.actionStart(this);
            return true;
//            replaceFragment(new ChangeThemeFragment());
//            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.change_theme) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setThemeColor(@ColorInt int color) {
        fab.setBackgroundTintList(ColorStateList.valueOf(color));
        main_app_bar_layout.setBackgroundColor(color);
//        StatusBarUtil.setColorForDrawerLayout(this, drawer_layout, color);

//        StatusBarUtil.setTranslucent(this);
//        StatusBarUtil.setColor(this, color);
        //setStatusBarColor(color);
//        MyViewUtils.setColorForSwipeBack(this,color);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ColorEvent colorEvent) {
        setThemeColor(colorEvent.getColor());
    }

    private void replaceFragment(BaseFragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment_layout, fragment, fragment.getPageName());
        transaction.commit();
    }

    private void replaceFragmentToBackStack(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

//                startActivity(new Intent(this,MotionActivity.class),
//                        ActivityOptions.makeSceneTransitionAnimation(this,fab,"fab").toBundle());

                break;
            default:
                break;
        }
    }
}
