package com.cmc.cmcui.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;


import com.cmc.cmcui.util.CAPPUtil;

import zm.bus.event.BusProvider;

public abstract class BasePoP implements View.OnClickListener {
    public Activity activity;
    OnClickDialogListener onClickDialogListener;
    /**
     * 弹窗布局
     */
    public int layoutId;

    public BasePoP(Context context) {
        this(context, null);
    }

    private PopupWindow mPopUpWindow;
    /**
     * 监听回调
     */
    protected OnClickDialogListener onCallbackListener;

    public void setOnCallbackListener(OnClickDialogListener onCallbackListener) {
        this.onCallbackListener = onCallbackListener;
    }

    public BasePoP(Context context, OnClickDialogListener onCallbackListener) {
        this(context, onCallbackListener, 0);
    }

    public BasePoP(Context context, OnClickDialogListener onCallbackListener, int layoutId) {
        this.activity = (Activity) context;
        this.onCallbackListener = onCallbackListener;
        this.layoutId = layoutId;
        BusProvider.getInstance().register(this);
        mPopUpWindow = initDialog(context);
        mPopUpWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                CAPPUtil.backgroundAlter(activity, false);
            }
        });
    }

    protected abstract PopupWindow initDialog(Context context);

    public PopupWindow setPopup(View view) {
        PopupWindow mPopUpWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopUpWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopUpWindow.setOutsideTouchable(true);
        mPopUpWindow.setFocusable(true);
        return mPopUpWindow;
    }

    /**
     * 显示在中间
     */
    public void showAtLocation() {
        CAPPUtil.backgroundAlter(activity, true);
        mPopUpWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }
    public void dismissDialog(){
        mPopUpWindow.dismiss();
    }
    @Override
    public void onClick(View v) {

    }
}
