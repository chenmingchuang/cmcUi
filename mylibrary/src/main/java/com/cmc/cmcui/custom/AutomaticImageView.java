package com.cmc.cmcui.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * *添加第三方库
 * implementation 'com.github.bumptech.glide:glide:4.9.0'
 * compile 'jp.wasabeef:glide-transformations:3.0.1'
 * compile 'jp.co.cyberagent.android.gpuimage:gpuimage-library:1.4.1'
 */
@SuppressLint("AppCompatCustomView")
public class AutomaticImageView extends ImageView {

    public AutomaticImageView(Context context) {
        super(context);
    }

    public AutomaticImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutomaticImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @param url  图片网址必须全路径
     * @param type 图片加载方式
     *             本地图片：1正常图片2圆形图片3圆角图片4正常gif图5圆形gif图6圆角gif图
     *             网络图片：1正常图片2圆形图片3圆角图片（如果是如果是网络图片只有123）
     */
    public void setImageUrlType(Object url, int type) {
        /*  */
        if (url instanceof String) {
            String imgUrl = (String) url;
            if (imgUrl.contains("gif") && imgUrl.contains("GIF")) {
                switch (type) {
                    case 1:
                        Glide.with(getContext()).asGif().load(url).into(this);
                        break;
                    case 2:
                        RequestOptions mRequestOptionsgif = RequestOptions.circleCropTransform()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                                .skipMemoryCache(true);
                        Glide.with(getContext()).asGif().load(url).apply(mRequestOptionsgif).into(this);
                        break;
                    case 3:
                        RoundedCorners roundedCornersgif = new RoundedCorners(12);
                        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
                        RequestOptions optionsgit = bitmapTransform(roundedCornersgif).override(300, 300);
                        Glide.with(getContext()).asGif().load(url).apply(optionsgit).into(this);
                        break;
                    default:
                        break;
                }
            } else {
                switch (type) {
                    case 1:
                        Glide.with(getContext()).load(url).into(this);
                        break;
                    case 2:
                        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                                .skipMemoryCache(true);
                        Glide.with(getContext()).load(url).apply(mRequestOptions).into(this);
                        break;
                    case 3:
                        RoundedCorners roundedCorners = new RoundedCorners(12);
                        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
                        RequestOptions options = bitmapTransform(roundedCorners).override(300, 300);
                        Glide.with(getContext()).load(url).apply(options).into(this);
                        break;
                    default:
                        break;
                }
            }
        } else if (url instanceof Integer) {
            switch (type) {
                case 1:
                    Glide.with(getContext()).load(url).into(this);
                    break;
                case 2:
                    RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                            .skipMemoryCache(true);
                    Glide.with(getContext()).load(url).apply(mRequestOptions).into(this);
                    break;
                case 3:
                    RoundedCorners roundedCorners = new RoundedCorners(12);
                    //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
                    RequestOptions options = bitmapTransform(roundedCorners).override(300, 300);
                    Glide.with(getContext()).load(url).apply(options).into(this);
                    break;
                case 4:
                    Glide.with(getContext()).asGif().load(url).into(this);
                    break;
                case 5:
                    RequestOptions mRequestOptionsgif = RequestOptions.circleCropTransform()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                            .skipMemoryCache(true);
                    Glide.with(getContext()).asGif().load(url).apply(mRequestOptionsgif).into(this);
                    break;
                case 6:
                    RoundedCorners roundedCornersgif = new RoundedCorners(12);
                    //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
                    RequestOptions optionsgit = bitmapTransform(roundedCornersgif).override(300, 300);
                    Glide.with(getContext()).asGif().load(url).apply(optionsgit).into(this);
                    break;
                default:
                    break;
            }
        }
    }
}
