package com.cmc.cmcui.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.cmc.cmcui.R;
import com.cmc.cmcui.app.CApp;

import java.util.Timer;
import java.util.TimerTask;




/**
 * 动画util
 */
public class AnimationUtil {
    /**
     * 图片倒计时同于设置倒计时显示的图片
     *
     * @param view
     * @param then
     */
    public static void getZoom(ImageView view, int then, int[] arrayId) {
        //图片资源文件地址
        view.setImageResource(arrayId[then]);

        //设置图片的缩放比例
        view.setScaleX(0);
        view.setScaleY(0);

        //设置x方向上的缩放动画
        ObjectAnimator oa1 = ObjectAnimator.ofFloat(view, "scaleX", 0, 1);
        oa1.setDuration(500);

        //设置y方向上的缩放动画
        ObjectAnimator oa2 = ObjectAnimator.ofFloat(view, "scaleY", 0, 1);
        oa2.setDuration(500);

        //创建动画师集合
        AnimatorSet set = new AnimatorSet();

        //设置所有的动画一起播放
        set.playTogether(oa1, oa2);
        //播放动画
        set.start();
    }

    /**
     * 图片渐变消失动画
     *
     * @param imageView
     */
    public static void startAlphaAnimation(View imageView) {
        /**
         * @param fromAlpha 开始的透明度，取值是0.0f~1.0f，0.0f表示完全透明， 1.0f表示和原来一样
         * @param toAlpha 结束的透明度，同上
         */
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        //设置动画持续时长
        alphaAnimation.setDuration(3000);
        //设置动画结束之后的状态是否是动画的最终状态，true，表示是保持动画结束时的最终状态
        alphaAnimation.setFillAfter(true);
        //设置动画结束之后的状态是否是动画开始时的状态，true，表示是保持动画开始时的状态
        alphaAnimation.setFillBefore(true);
        //设置动画的重复模式：反转REVERSE和重新开始RESTART
        alphaAnimation.setRepeatMode(AlphaAnimation.REVERSE);
        //设置动画播放次数
        alphaAnimation.setRepeatCount(AlphaAnimation.INFINITE);
        //开始动画
        imageView.startAnimation(alphaAnimation);
        //清除动画
        imageView.clearAnimation();
        //同样cancel()也能取消掉动画
        alphaAnimation.cancel();
    }

    /**
     * 平移动画
     *
     * @param view
     * @param toXdeita   到X轴的那
     * @param fromYdeita 到Y轴的那
     */
    public static void getTranslation(View view, int toXdeita, int fromYdeita) {
        Animation translateAnimation = new TranslateAnimation(0, toXdeita, 0, fromYdeita);//平移动画  从0,0,平移到100,100
        translateAnimation.setDuration(1500);//动画持续的时间为1.5s
        view.setAnimation(translateAnimation);//给imageView添加的动画效果
        translateAnimation.setFillEnabled(true);//使其可以填充效果从而不回到原地
        translateAnimation.setFillAfter(true);//不回到起始位置
        //如果不添加setFillEnabled和setFillAfter则动画执行结束后会自动回到远点
        translateAnimation.startNow();//动画开始执行 放在最后

    }

    /**
     * 淡入淡出动画方法
     *
     * @param v
     */
    public static void alpha(View v) {
        // 创建透明度动画，第一个参数是开始的透明度，第二个参数是要转换到的透明度
        AlphaAnimation alphaAni = new AlphaAnimation(0.2f, 1);

        //设置动画执行的时间，单位是毫秒
        alphaAni.setDuration(1000);

        // 设置动画结束后停止在哪个状态（true表示动画完成后的状态）
        // alphaAni.setFillAfter(true);

        // true动画结束后回到开始状态
        // alphaAni.setFillBefore(true);

        // 设置动画重复次数
        // -1或者Animation.INFINITE表示无限重复，正数表示重复次数，0表示不重复只播放一次
        alphaAni.setRepeatCount(10);

        // 设置动画模式（Animation.REVERSE设置循环反转播放动画,Animation.RESTART每次都从头开始）
        alphaAni.setRepeatMode(Animation.REVERSE);

        // 启动动画
        v.startAnimation(alphaAni);
    }

    /**
     * 缩放动画
     *
     * @param v
     * @param fromX       x轴的初始值
     * @param toX         x轴收缩后的值
     * @param fromY       y轴的初始值
     * @param toY         y轴收缩后的值
     * @param pivotXValue x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
     * @param PivotYValue y轴的值，0.5f表明是以自身这个控件的一半长度为x轴
     */
    public static void Scale(View v, int fromX, int toX, int fromY, int toY, int pivotXValue, int PivotYValue) {
        ScaleAnimation scaleAni = new ScaleAnimation(fromX, toX, fromY, toY,
                Animation.RELATIVE_TO_SELF, pivotXValue, Animation.RELATIVE_TO_SELF,
                PivotYValue);

        //设置动画执行的时间，单位是毫秒
        scaleAni.setDuration(100);

        // 设置动画重复次数
        // -1或者Animation.INFINITE表示无限重复，正数表示重复次数，0表示不重复只播放一次
        scaleAni.setRepeatCount(10);

        // 设置动画模式（Animation.REVERSE设置循环反转播放动画,Animation.RESTART每次都从头开始）
        scaleAni.setRepeatMode(Animation.REVERSE);

        // 启动动画
        v.startAnimation(scaleAni);
    }

    /**
     * 旋转动画
     *
     * @param v
     * @param fromeDegrees 从哪个旋转角度开始,0表示从0度开始
     * @param toDegrees    转到什么角度,360表示旋转360度
     * @param pivotXValue  轴的值，0.5f表明是以自身这个控件的一半长度为x轴
     * @param poivotYValue y轴的值，0.5f表明是以自身这个控件的一半长度为x轴
     */
    public static void Rotate(View v, int fromeDegrees, int toDegrees, int pivotXValue, int poivotYValue) {
        RotateAnimation rotateAni = new RotateAnimation(fromeDegrees, toDegrees,
                Animation.RELATIVE_TO_SELF, pivotXValue, Animation.RELATIVE_TO_SELF,
                poivotYValue);

        //设置动画执行的时间，单位是毫秒
        rotateAni.setDuration(1000);

        // 设置动画重复次数
        // -1或者Animation.INFINITE表示无限重复，正数表示重复次数，0表示不重复只播放一次
        rotateAni.setRepeatCount(10);

        // 设置动画模式（Animation.REVERSE设置循环反转播放动画,Animation.RESTART每次都从头开始）
        rotateAni.setRepeatMode(Animation.REVERSE);

        // 启动动画
        v.startAnimation(rotateAni);
    }


    /**
     * 动画平移
     *
     * @param v
     */
    public static void Translate(View v) {
        /*
         * TranslateAnimation translateAni = new TranslateAnimation(fromXType,
         * fromXValue, toXType, toXValue, fromYType, fromYValue, toYType,
         * toYValue);
         */
        TranslateAnimation translateAni = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
                0.3f, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0.3f);

        //设置动画执行的时间，单位是毫秒
        translateAni.setDuration(1000);

        // 设置动画重复次数
        // -1或者Animation.INFINITE表示无限重复，正数表示重复次数，0表示不重复只播放一次
        translateAni.setRepeatCount(10);

        // 设置动画模式（Animation.REVERSE设置循环反转播放动画,Animation.RESTART每次都从头开始）
        translateAni.setRepeatMode(Animation.REVERSE);

        // 启动动画
        v.startAnimation(translateAni);
    }

    public static void TranslationUpload(View v) {
        TranslateAnimation translateAni = new TranslateAnimation(0, 0, 0, 80);//平移动画  从0,0,平移到100,100
        //设置动画执行的时间，单位是毫秒
        translateAni.setDuration(1000);

        // 设置动画重复次数
        // -1或者Animation.INFINITE表示无限重复，正数表示重复次数，0表示不重复只播放一次
        translateAni.setRepeatCount(Animation.INFINITE);

        // 设置动画模式（Animation.REVERSE设置循环反转播放动画,Animation.RESTART每次都从头开始）
        translateAni.setRepeatMode(Animation.REVERSE);

        // 启动动画
        v.startAnimation(translateAni);
    }

    /**
     * 组合动画（缩放和旋转组合）
     *
     * @param v
     */
    public static void Combo(View v) {
        // Animation.RELATIVE_TO_SELF, 0.5f表示绕着自己的中心点进行动画
        ScaleAnimation scaleAni = new ScaleAnimation(0.2f, 3.0f, 0.2f, 3.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);

        //设置动画执行的时间，单位是毫秒
        scaleAni.setDuration(100);

        // 设置动画重复次数
        // -1或者Animation.INFINITE表示无限重复，正数表示重复次数，0表示不重复只播放一次
        scaleAni.setRepeatCount(-1);

        // 设置动画模式（Animation.REVERSE设置循环反转播放动画,Animation.RESTART每次都从头开始）
        scaleAni.setRepeatMode(Animation.REVERSE);

        // 0表示从0度开始,360表示旋转360度
        // Animation.RELATIVE_TO_SELF, 0.5f表示绕着自己的中心点进行动画
        RotateAnimation rotateAni = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);

        // 设置动画完成的时间（速度），单位是毫秒，1000是1秒内完成动画
        rotateAni.setDuration(1000);

        // 设置动画重复次数
        // -1或者Animation.INFINITE表示无限重复，正数表示重复次数，0表示不重复只播放一次
        rotateAni.setRepeatCount(-1);

        // 设置动画模式（Animation.REVERSE设置循环反转播放动画,Animation.RESTART每次都从头开始）
        rotateAni.setRepeatMode(Animation.REVERSE);

        // 将缩放动画和旋转动画放到动画插值器
        AnimationSet as = new AnimationSet(false);
        as.addAnimation(scaleAni);
        as.addAnimation(rotateAni);

        // 启动动画
        v.startAnimation(as);
    }

    /**
     * 动画用于item进场动画
     *
     * @param context 上下文
     * @param anim    动画
     * @param delay   时长
     * @param view    用于显示的列表
     */
    public static void itemEntrance(Context context, int anim, float delay, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, anim);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(delay);
        view.setAnimation(animation);
    }

    /**
     * @param view        需要设置动画的View
     * @param startOffset 时长
     */
    public static void hideView(ViewGroup view, int startOffset) {
        //改为属性动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, 180);
        animator.setDuration(500);
        animator.setStartDelay(startOffset);
        animator.start();
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight());
    }

    /**
     * @param view        设置动画的View
     * @param startOffset 设置时长
     */
    public static void showView(ViewGroup view, int startOffset) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 180, 360);
        animator.setDuration(500);
        animator.setStartDelay(startOffset);
        animator.start();
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight());
    }

    /**
     * 弹跳动画
     *
     * @param v
     * @param
     */
    public static void startTranslateAnim(View v) {
        if (v == null) {
            return;
        }
        AnimationSet animup = new AnimationSet(true);
        TranslateAnimation mytranslateanimup0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.4f);
        mytranslateanimup0.setDuration(100);
        TranslateAnimation mytranslateanimup1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, +0.4f);
        mytranslateanimup1.setDuration(100);
        mytranslateanimup1.setStartOffset(100);
        animup.addAnimation(mytranslateanimup0);
        animup.addAnimation(mytranslateanimup1);
        v.startAnimation(animup);
    }

    /**
     * 无回调
     *
     * @param intent   意向
     * @param view     用于跳转的View
     * @param activity 上下文
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void startActivity(final Intent intent, final View view, final Activity activity) {
        int cxs = (view.getLeft() + view.getRight()) / 2;
        int cys = (view.getTop() + view.getBottom()) / 2;
        float finalRadiuss = (float) Math.hypot(view.getWidth(), view.getHeight());
        final Animator anims = ViewAnimationUtils.createCircularReveal(view, cxs, cys, 0, finalRadiuss);
        view.setBackgroundColor(activity.getResources().getColor(CApp.starColor));
        anims.setDuration(1500);
        anims.setInterpolator(new AccelerateDecelerateInterpolator());
        anims.start();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                activity.startActivity(intent);
            }
        }, 1400, 1400);
        anims.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setBackgroundColor(activity.getResources().getColor(R.color.green_lucency));
                anims.cancel();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });

    }

    /**
     * 带回调参数的跳转
     *
     * @param intent   意向
     * @param view     用于跳转的view
     * @param activity 上下文
     * @param result   回调
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void startActivity(final Intent intent, final View view, final Activity activity, final int result) {
        int cxs = (view.getLeft() + view.getRight()) / 2;
        int cys = (view.getTop() + view.getBottom()) / 2;
        float finalRadiuss = (float) Math.hypot(view.getWidth(), view.getHeight());
        final Animator anims = ViewAnimationUtils.createCircularReveal(view, cxs, cys, 0, finalRadiuss);
        view.setBackgroundColor(activity.getResources().getColor(CApp.starColor));
        anims.setDuration(1500);

        anims.setInterpolator(new AccelerateDecelerateInterpolator());
        anims.start();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                activity.startActivityForResult(intent, result);
            }
        }, 1400, 1400);
        anims.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setBackgroundColor(activity.getResources().getColor(R.color.green_lucency));
                anims.cancel();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
    }

    /**
     * 无回调
     *
     * @param intent   意向
     * @param view     显示布局的View
     * @param activity 上下文
     * @param color    颜色
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void startColorActivity(final Intent intent, final View view, final Activity activity, int color) {
        int cxs = (view.getLeft() + view.getRight()) / 2;
        int cys = (view.getTop() + view.getBottom()) / 2;
        float finalRadiuss = (float) Math.hypot(view.getWidth(), view.getHeight());
        final Animator anims = ViewAnimationUtils.createCircularReveal(view, cxs, cys, 0, finalRadiuss);
        view.setBackgroundColor(activity.getResources().getColor(color));
        anims.setDuration(1500);
        anims.setInterpolator(new AccelerateDecelerateInterpolator());
        anims.start();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                activity.startActivity(intent);
            }
        }, 1400, 1400);
        anims.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setBackgroundColor(activity.getResources().getColor(R.color.green_lucency));
                anims.cancel();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
    }

    /**
     * 有回调
     *
     * @param intent   意图
     * @param view     显示的View
     * @param activity 上下文
     * @param color    颜色值
     * @param result   回调值
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void startColorActivity(final Intent intent, final View view, final Activity activity, int color, final int result) {
        int cxs = (view.getLeft() + view.getRight()) / 2;
        int cys = (view.getTop() + view.getBottom()) / 2;
        float finalRadiuss = (float) Math.hypot(view.getWidth(), view.getHeight());
        final Animator anims = ViewAnimationUtils.createCircularReveal(view, cxs, cys, 0, finalRadiuss);
        view.setBackgroundColor(activity.getResources().getColor(color));
        anims.setDuration(1500);
        anims.setInterpolator(new AccelerateDecelerateInterpolator());
        anims.start();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                activity.startActivityForResult(intent, result);
            }
        }, 1400, 1400);
        anims.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setBackgroundColor(activity.getResources().getColor(R.color.green_lucency));
                anims.cancel();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
    }
}
