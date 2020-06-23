package com.cmc.cmcui.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmc.cmcui.R;
import com.cmc.cmcui.util.CAPPUtil;

import java.io.Serializable;




public class TitleBarView extends FrameLayout {

    /**
     * 左边view
     */
    private TextView tv_finshi;
    /**
     * 中间标题
     */
    private TextView tv_bar_title;
    /**
     * 右边view
     */
    private TextView tv_bar_mune;

    public Context context;
    /**
     * 底层布局
     */
    private RelativeLayout rl_title_bar;

    public TitleBarView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public TitleBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TitleBarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.title_bar_view, null);
        tv_finshi = view.findViewById(R.id.tv_finshi);
        tv_bar_title = view.findViewById(R.id.tv_bar_title);
        tv_bar_mune = view.findViewById(R.id.tv_bar_mune);
        rl_title_bar = view.findViewById(R.id.rl_title_bar);
        this.addView(view, new LinearLayout.LayoutParams(-1, CAPPUtil.dip2px(context, 49)));
    }


    public static class Builder implements Serializable {

        TitleBarView titleBar;
        Context context;

        public Builder(Context context) {
            this.context = context;
            titleBar = new TitleBarView(context);
        }

        /**
         * @param left 设置左边文字
         * @return
         */
        public Builder setLeftText(String left) {
            titleBar.tv_finshi.setText(left);
            return this;
        }

        /**
         * @param color 设置左边字体颜色
         * @return
         */
        public Builder setLeftTextColor(int color) {
            titleBar.tv_finshi.setTextColor(context.getResources().getColor(color));
            return this;
        }

        /**
         * @param img 设置左边图片
         * @return
         */
        public Builder setLeftImage(int img) {
            titleBar.tv_finshi.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0);
            return this;
        }

        /**
         * @param onClickListement 左边view点击事件
         * @return
         */
        public Builder setLeftOnClickListement(final OnLeftClickListement onClickListement) {
            titleBar.tv_finshi.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListement != null) {
                        onClickListement.OnLeftClick(v);
                    }
                }
            });
            return this;
        }

        /**
         * @param cloro 设置标题栏颜色
         * @return
         */
        public Builder setViewBackground(int cloro) {
            titleBar.rl_title_bar.setBackgroundColor(context.getResources().getColor(cloro));
            return this;
        }

        /**
         * @param title 设置中间标题
         * @return
         */
        public Builder setTitle(String title) {
            titleBar.tv_bar_title.setText(title);
            return this;
        }

        /**
         * @param color 设置中间字体颜色
         * @return
         */
        public Builder setTitleColor(int color) {
            titleBar.tv_bar_title.setTextColor(context.getResources().getColor(color));
            return this;
        }

        /**
         * @param mune 设置右边文字
         * @return
         */
        public Builder setRightText(String mune) {
            titleBar.tv_bar_mune.setText(mune);
            return this;
        }

        /**
         * @param color 设置左边字体颜色
         * @return
         */
        public Builder setRightTextColor(int color) {
            titleBar.tv_finshi.setTextColor(context.getResources().getColor(color));
            return this;
        }

        /**
         * @param img 设置右边图片
         * @return
         */
        public Builder setRightTextImg(int img) {
            titleBar.tv_bar_mune.setCompoundDrawablesWithIntrinsicBounds(0, 0, img, 0);
            return this;
        }

        /**
         * @param onClickListement 设置右边点击事件
         * @return
         */
        public Builder setRightMuneOnClickListement(final OnRightClickListement onClickListement) {
            titleBar.tv_bar_mune.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListement != null) {
                        onClickListement.OnRightClick(v);
                    }
                }
            });
            return this;
        }

        /**
         * @return 返回设置好的控件
         */
        public TitleBarView complete() {
            return titleBar;
        }
    }

    public interface OnLeftClickListement {
        void OnLeftClick(View view);
    }

    public interface OnRightClickListement {
        void OnRightClick(View view);
    }
}
