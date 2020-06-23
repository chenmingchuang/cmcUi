package com.cmc.cmcui.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.cmc.cmcui.R;


public abstract class CApp extends MultiDexApplication {

    public static Context context;

    /**
     * 用于跳转动画的颜色
     */
    public static int starColor;

    /**
     * 用于logcat打印开关
     */
    public static boolean logcat;

    @Override
    public void onCreate() {
        super.onCreate();
        // Shortbread.create(this);
        context = getApplicationContext();

        if (setStarColor() != 0) {
            starColor = setStarColor();
        } else {
            starColor = R.color.gold;
        }
        logcat = LogCat();
        setCreate();
    }

    /**
     * 设置跳转的用的颜色
     */
    public abstract int setStarColor();

    /**
     * 初始化
     */
    public abstract void setCreate();

    /**
     * logcat打印开关
     *
     * @return
     */
    public abstract boolean LogCat();
}
