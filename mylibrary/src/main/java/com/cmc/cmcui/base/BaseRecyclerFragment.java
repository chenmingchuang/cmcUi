package com.cmc.cmcui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cmc.cmcui.R;
import com.cmc.cmcui.util.CAPPUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


import zm.bus.event.BusProvider;

public abstract class BaseRecyclerFragment<C> extends Fragment {

    /**
     * 全局上下文
     */
    public Activity mActivity;

    /**
     * 列表
     */
    public RecyclerView recycler;

    /**
     * 刷新加载控件
     */
    public SmartRefreshLayout refreshLayout;

    /**
     * 页码
     */
    public int pagination = 1;

    /**
     * 用于接收数据
     */
    public List<C> message = new ArrayList<>();

    /**
     * 基类适配器
     */
    public BaseQuickAdapter<C, BaseViewHolder> adapter;

    /**
     * 数据为空的View
     */
    public View emptyView;

    /**
     * 头布局
     */
    public View headView;

    /**
     * 底布局
     */
    public View bottomView;

    /**
     * 设置第一个条目
     */
    public View firstView;

    /**
     * 根布局
     */
    public RelativeLayout relativeLayout;

    /**
     * 置顶布局
     */
    public FrameLayout endLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BusProvider.getInstance().register(getContext());
        mActivity = getActivity();
        View view = initFragment();
        initDate();
        return view;
    }

    public View initFragment() {
        relativeLayout = new RelativeLayout(getContext());
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        mActivity = getActivity();
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_base_recycler, null);
        recycler = view.findViewById(R.id.rv);
        recycler.setLayoutManager(initLayoutManager());
        refreshLayout = view.findViewById(R.id.refreshLayout);
        adapter = initAdapter();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                pagination = 1;
                message.clear();
                initDate();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
                pagination = pagination + 1;
                initDate();
            }
        });
        headView = headView();

        /**
         * 设置头布局
         */

        if (headView != null) {
            layout.addView(headView, new LinearLayout.LayoutParams(-1, -2));
        }
        /**
         * 设置内容布局
         */
        layout.addView(view, new LinearLayout.LayoutParams(-1, 0, 1.0f));

        /**
         * 设置底布局
         */
        bottomView = bottomView();
        if (bottomView != null) {
            layout.addView(bottomView, new LinearLayout.LayoutParams(-1, -2));
        }

        if (adapter != null) {
            firstView = firstView();
            emptyView = emptyView();
            adapter.setHeaderFooterEmpty(true, true);
            //设置空布局
            if (emptyView != null) {
                adapter.setEmptyView(emptyView);
            }
            //设置第一条条目
            if (firstView != null) {
                adapter.addHeaderView(firstView);
            }
        }
        endLayout = new FrameLayout(getActivity());
        int padding = CAPPUtil.dip2px(getActivity(), 16.0f);
        endLayout.setPadding(padding, padding, padding, padding);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        relativeLayout.addView(endLayout, params);
        relativeLayout.addView(layout);
        recycler.setAdapter(adapter);
        return relativeLayout;
    }

    public void initDate() {
        message.addAll(getData());
        if (adapter != null) {
            adapter.setNewData(message);
        }

    }

    /**
     * 设置数据为空时显示的布局
     *
     * @return
     */
    public View emptyView() {
        return null;
    }

    /**
     * 设置头布局
     *
     * @return
     */
    public View headView() {
        return null;
    }

    /**
     * 设置底布局
     *
     * @return
     */
    public View bottomView() {
        return null;
    }

    public View firstView() {
        return null;
    }

    /**
     * 设置列表展示方式
     */
    public abstract RecyclerView.LayoutManager initLayoutManager();

    /**
     * 创建适配器
     */
    public abstract BaseQuickAdapter<C, BaseViewHolder> initAdapter();

    /**
     * 解析网络数据
     *
     * @return
     */
    public abstract List<C> getData();

    /**
     * 刷新
     */
    public void setRefresh() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(getContext());
    }
}
