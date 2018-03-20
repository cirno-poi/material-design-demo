package com.example.dell.mddemo;

import android.support.annotation.ColorInt;

import com.example.dell.mddemo.base.BaseEvent;

/**
 * Created by dell on 2018/3/20.
 */

public class ColorEvent extends BaseEvent {
    private @ColorInt int color;

    public ColorEvent(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
