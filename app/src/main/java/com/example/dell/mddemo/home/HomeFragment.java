package com.example.dell.mddemo.home;

import android.os.Bundle;
import android.util.Log;

import com.example.dell.mddemo.R;
import com.example.dell.mddemo.base.BaseFragment;

/**
 * Description：write something
 * <p>
 * Created by Flower.G on 2018/3/21.
 */

public class HomeFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.content_main2;
    }

    @Override
    protected void setupView() {
//        Log.d("233333", "------HomeFragment: " + getPageName());
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }
}
