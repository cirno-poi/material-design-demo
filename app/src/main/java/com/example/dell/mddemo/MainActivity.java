package com.example.dell.mddemo;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dell.mddemo.base.BaseActivity;
import com.example.dell.mddemo.base.BaseFragment;
import com.example.dell.mddemo.changeTheme.ChangeThemeActivity;
import com.example.dell.mddemo.changeTheme.ChangeThemeFragment;
import com.example.dell.mddemo.home.HomeFragment;
import com.example.dell.mddemo.motion.Motion2Fragment;
import com.example.dell.mddemo.motion.MotionFragment;
import com.example.dell.mddemo.utils.MyViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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

    private long lastClickTime = 0;
    FragmentManager fragmentManager = getSupportFragmentManager();

    private BaseFragment mCurrentFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupView() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        MyViewUtils.setToolbarHeight(this, toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
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
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_slideshow) {
            mCurrentFragment = new Motion2Fragment();
            replaceFragment(mCurrentFragment);
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_manage) {
//            ChangeThemeActivity.actionSatrt(this);
//            return true;
            replaceFragment(new ChangeThemeFragment());
            fab.setVisibility(View.GONE);
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
        //setStatusBarColor(color);
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

                break;
            default:
                break;
        }
    }
}
