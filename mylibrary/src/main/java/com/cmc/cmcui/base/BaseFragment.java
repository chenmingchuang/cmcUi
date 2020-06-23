package com.cmc.cmcui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zm.bus.event.BusProvider;

public abstract class BaseFragment<P extends BasePrensenter> extends Fragment {

    /**
     * 获取布局
     *
     * @return
     */
    public abstract int layoutId();

    /**
     * 获取 p 层
     *
     * @return
     */
    public abstract P setPresenter();

    /**
     * 获取activity上下文
     */
    public Activity mActivity;

    /**
     * @param view 获取布局对象
     */
    public abstract void initView(View view);

    /**
     * 作初始化操作
     */
    public abstract void initDate();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        BusProvider.getInstance().register(this);
        View view = LayoutInflater.from(mActivity).inflate(layoutId(), container, false);
        initView(view);
        initDate();
        return view;
    }

    @Override
    public void onDestroy() {
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }
}
