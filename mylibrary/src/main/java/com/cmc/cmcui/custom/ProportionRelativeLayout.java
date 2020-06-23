package com.cmc.cmcui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.cmc.cmcui.R;


public class ProportionRelativeLayout extends RelativeLayout {

    public ProportionRelativeLayout(@NonNull Context context) {
        super(context);
        initDate(context, null);
    }

    public ProportionRelativeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDate(context, attrs);
    }

    public ProportionRelativeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDate(context, attrs);
    }

    // 宽高比
    private float ratio;

    /**
     * 设置宽高比
     *
     * @param ratio
     */
    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    /**
     * 设置宽高比
     *
     * @param ratio_width
     * @param ratio_height
     */
    public void setRatio(int ratio_width, int ratio_height) {
        if (ratio_width > 0 && ratio_height >= 0) {
            setRatio((ratio_height * 1.0f) / (ratio_width * 1.0f));
        }
    }

    private void initDate(Context context, AttributeSet attrs) {
        ratio = 0.0f;
        if (attrs != null) {
            //获得attrs文件
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProportionRelativeLayout);
            ratio = array.getFloat(R.styleable.ProportionRelativeLayout_ratio, 0.0f);
            if (ratio <= 0.0f) {
                //获取属性值
                int ratio_width = array.getInteger(R.styleable.ProportionRelativeLayout_ratio_width, 1);
                int ratio_height = array.getInteger(R.styleable.ProportionRelativeLayout_ratio_height, 0);
                if (ratio_width > 0 && ratio_height >= 0) {
                    ratio = (ratio_height * 1.0f) / (ratio_width * 1.0f);
                }
            }
            //使用定义属性
            array.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //存储VIew的宽度和高度
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        //获取View宽度
        int childWidthSize = getMeasuredWidth();
        //获取View高度
        int childHeightSize = getMeasuredHeight();
        //childWidthSize设置View宽度    MeasureSpec.EXACTLY这个View的界限
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        //ratio > 0 ? (int) (childWidthSize * ratio): childHeightSize计算设置View的高度
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(ratio > 0 ? (int) (childWidthSize * ratio)
                : childHeightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
