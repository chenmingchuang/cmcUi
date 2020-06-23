package com.cmc.cmcui.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.cmc.cmcui.R;
import com.cmc.cmcui.custom.TitleBarView;
import com.cmc.cmcui.util.CAPPUtil;

import zm.bus.event.BusProvider;

public abstract class BaseActivity<P extends BasePrensenter> extends AppCompatActivity
        implements IBaseView, View.OnClickListener {
    /**
     * p层对象
     */
    public P presenter;

    /**
     * 上下文
     */
    public Activity mActivity;



    /**
     * fragment加载器
     */
    public FragmentManager fragmentManager;

    /**
     * 标题栏
     */
    TitleBarView titleBarView;

    /**
     * 底部View
     */
    public View bootonView;

    /**
     * 根布局
     */
    public LinearLayout layout;
    public RelativeLayout startLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        //沉浸式
        if (Build.VERSION.SDK_INT >= 19) {
            CAPPUtil.getStatusHeight(mActivity);
        }
        RelativeLayout relativeLayout = new RelativeLayout(mActivity);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, -1);
        relativeLayout.setLayoutParams(params);
        layout = new LinearLayout(this);
        LinearLayout.LayoutParams params1=new LinearLayout.LayoutParams(-1,-1);
        layout.setLayoutParams(params1);
        layout.setOrientation(LinearLayout.VERTICAL);
        titleBarView = initTitleBar();
        if (titleBarView != null) {
            layout.addView(titleBarView, new LinearLayout.LayoutParams(-1, -2));
        }
        if (layoutId() == 0) {
            View inflate = LayoutInflater.from(mActivity).inflate(R.layout.activity_fragment, null);
            layout.addView(inflate, new LinearLayout.LayoutParams(-1, -1, 1.0f));
        } else {
            View view = LayoutInflater.from(mActivity).inflate(layoutId(), null);
            if (view != null) {
                layout.addView(view, new LinearLayout.LayoutParams(-1, -1, 1.0f));
            } else {
                View inflate = LayoutInflater.from(mActivity).inflate(R.layout.activity_fragment, null);
                layout.addView(inflate, new LinearLayout.LayoutParams(-1, -1, 1.0f));
            }
        }
        bootonView = setbootonView();
        if (bootonView != null) {
            layout.addView(bootonView, new LinearLayout.LayoutParams(-1, -2));
        }
        relativeLayout.addView(layout);
        startLayout = new RelativeLayout(mActivity);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        startLayout.setLayoutParams(layoutParams);
        relativeLayout.addView(startLayout);
        setContentView(relativeLayout);
        /**
         * 注册时间分发总线
         */
        BusProvider.getInstance().register(this);
        fragmentManager = getSupportFragmentManager();
        initView();
        presenter = setPresenter();
        initDate();
    }

    @Override
    public void showToLoad(String title) {

    }

    @Override
    public void closeDialog() {

    }

    /**
     * 是否为横屏
     */
    public boolean isScreenOrientationLandscape() {
        return false;
    }

    /**
     * 设置标题栏
     *
     * @return
     */
    public TitleBarView initTitleBar() {
        return null;
    }

    /**
     * @return 设置底布局
     */
    public View setbootonView() {
        return null;
    }

    /**
     * 布局Id
     *
     * @return
     */
    public abstract int layoutId();

    /**
     * 获得Presenter对象
     *
     * @return
     */
    public abstract P setPresenter();

    /**
     * 获得控件Id
     */
    public abstract void initView();

    /**
     * 获得控件Id
     */
    public abstract void initDate();


    @Override
    protected void onDestroy() {
        /**
         * 注销事件分发总线
         */
        BusProvider.getInstance().unregister(this);
        if (presenter != null) {
            presenter.OnCancel();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }

}
