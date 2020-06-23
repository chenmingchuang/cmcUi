package com.cmc.cmcui.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmc.cmcui.R;
import com.cmc.cmcui.app.CApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CAPPUtil {

    /**
     * dp转px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        if (context == null)
            return 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 常用权限
     *
     * @return
     */
    public static String[] getPerms() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        return perms;
    }

    /**
     * 保留一位小数
     *
     * @param point
     * @return
     */
    public static String get1Point(double point) {
        String format = new DecimalFormat("#.0").format(point);
        if (format.startsWith(".")) {
            return "0" + format;
        }
        return format;
    }

    /**
     * 保留一位小数
     *
     * @param pointStr
     * @return
     */
    public static String get1Point(String pointStr) {
        try {
            return get1Point(Double.parseDouble(pointStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pointStr;
    }

    /**
     * 保留两位小数
     *
     * @param point
     * @return
     */
    public static String get2Point(double point) {
        String format = new DecimalFormat("#.00").format(point);
        if (format.startsWith(".")) {
            return "0" + format;
        }
        return format;
    }

    /**
     * 保留两位小数
     *
     * @param pointStr
     * @return
     */
    public static String get2Point(String pointStr) {
        try {
            return get2Point(Double.parseDouble(pointStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pointStr;
    }

    /**
     * 显示提示
     *
     * @param context 上下文
     * @param str     提示内容
     */
    public static void showPrompt(Context context, String str) {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.setGravity(81, 0, 176);
        toast.show();
    }


    /**
     * 获取项目缓存
     *
     * @param context 上下文
     * @return
     * @throws Exception
     */
    public static String setThecache(Context context) throws Exception {
        if (context == null)
            return "";
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 清除缓存
     *
     * @param textView 用于显示缓存的TextView
     * @param context  上下文
     * @param cache    展示图片
     */
    public static void setRemoveThecache(TextView textView, Context context, int cache) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
        Toast toast = Toast.makeText(context, "清除成功啦！", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(cache);
        toast.setView(imageCodeProject);
        toast.show();
        try {
            textView.setText(setThecache(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取版本号
     *
     * @param context 上下文
     * @return
     */
    public static int setObtainVersionNumber(Context context) {
        if (context == null)
            return 0;
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        int version = -1;
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionCode;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }


    /**
     * 打开浏览器
     *
     * @param mActivity
     * @param url
     */
    public static void openBrowser(Activity mActivity, String url) {
        if (mActivity == null)
            return;
        if (!TextUtils.isEmpty(url) && url.startsWith("http")) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            mActivity.startActivity(intent);
        }
    }

    /**
     * 复制文本
     *
     * @param mActivity
     * @param text
     */
    public static void copyText(Activity mActivity, String text) {
        if (mActivity == null || TextUtils.isEmpty(text))
            return;
        ClipboardManager clipboard = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText(null, text));
        showPrompt(mActivity, "复制成功");
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        if (context == null)
            return 0;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (context == null)
            return 0;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = 0;
        if (context != null) {
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
                statusHeight = context.getResources().getDimensionPixelSize(height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /**
     * 是否是手机号
     *
     * @param phone
     * @return
     */
    public static boolean isMobile(String phone) {
        boolean isMobile = false;
        if (!TextUtils.isEmpty(phone) && phone.length() == 11 && phone.startsWith("1")) {
            isMobile = true;
        }
        return isMobile;
    }

    /**
     * 删除文件夹内文件
     *
     * @param dir
     * @return
     */
    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        if (file != null) {
            try {
                File[] fileList = file.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    // 如果下面还有文件
                    if (fileList[i].isDirectory()) {
                        size = size + getFolderSize(fileList[i]);
                    } else {
                        size = size + fileList[i].length();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * @param context 上下文
     * @param color   颜色值
     * @return
     */
    public static int TextColor(Context context, int color) {
        return context.getResources().getColor(color);
    }

    /**
     * long milliseconds ：震动的时长，单位是毫秒
     * long[] pattern  ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    @SuppressLint("MissingPermission")
    /**
     * 直接震动
     */
    public static void Vibrate(final Context context) {
        long milliseconds = 100;
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    @SuppressLint("MissingPermission")
    /**
     * 自定义设置震动时长
     */
    public static void Vibrate(final Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    @SuppressLint("MissingPermission")
    /**
     * 是否反复震动   pattern每次震动的时长
     */
    public static void Vibrate(final Context context, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);//-1为反复震动
    }

    /**
     * @param activity 需要截屏的activity
     * @return
     */
    public static Bitmap captureWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 将手机号显示前三位后四位其他用*显示
     *
     * @param phone 手机号
     * @return
     */
    public static String encryptionPhone(String phone) {
        String phoneNumbe = "";
        if (!TextUtils.isEmpty(phone) && phone.length() == 11) {
            phoneNumbe = phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
        }
        return phoneNumbe;
    }

    /**
     * 银行卡号加密
     *
     * @param bank 银行卡号
     * @return
     */
    public static String encryptionBank(String bank) {
        if (checkBankCard(bank)) {
            return bank;
        }

        int length = bank.length();
        int beforeLength = 4;
        int afterLength = 4;
        //替换字符串，当前使用“*”
        String replaceSymbol = "*";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i < beforeLength || i >= (length - afterLength)) {
                sb.append(bank.charAt(i));
            } else {
                sb.append(replaceSymbol);
            }
        }

        return sb.toString();
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId 银行卡号
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 身份证加密中间用*显示
     *
     * @param idcard 身份证号
     * @return
     */
    public static String encryptionIdcard(String idcard) {
        return idcard.substring(0, 3) + " ***** ***** " + idcard.substring(11);
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    public static String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), "");
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    @NonNull
    public static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 安卓apk
     *
     * @param activity    当前activity
     * @param apkFilePath 安卓包路径
     */
    public static void installApk(Activity activity, String apkFilePath) {
        if (TextUtils.isEmpty(apkFilePath)) {
            return;
        }
        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.parse(new File(apkFilePath).toString()), "application/vnd.android.package-archive");
        activity.startActivity(intent);*/
    }

    /**
     * 显示提示
     *
     * @param context 上下文
     * @param text    提示内容
     * @param isLong  时长
     */
    public static void showToast(Context context, @StringRes int text, boolean isLong) {
        showToast(context, context.getString(text), isLong);
    }

    /**
     * 显示提示
     *
     * @param context 上下文
     * @param text    提示内容
     * @param isLong  时长
     */
    public static void showToast(Context context, String text, boolean isLong) {
        Toast.makeText(context, text, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置顶部间距(沉浸式)
     *
     * @param context
     * @param view
     */
    public static void setPaddingTop(Context context, View view) {
        if (Build.VERSION.SDK_INT >= 19 && view != null) {
            view.setPadding(0, getStatusHeight(context), 0, 0);
        }
    }

    public static boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(context, "搜索到摄像头硬件", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "不具备摄像头硬件", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 获取随机文字
     *
     * @param quantity 随机文字数量
     * @return
     */
    public static String getRandomNick(int quantity) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < quantity; i++) {
            builder.append(getRandomSingleCharacter());
        }
        return builder.toString();
    }

    /**
     * 获取随机单个汉字
     *
     * @return 随机单个汉字
     */
    public static String getRandomSingleCharacter() {
        String str = "";
        int heightPos;
        int lowPos;
        Random rd = new Random();
        heightPos = 176 + Math.abs(rd.nextInt(39));
        lowPos = 161 + Math.abs(rd.nextInt(93));
        byte[] bt = new byte[2];
        bt[0] = Integer.valueOf(heightPos).byteValue();
        bt[1] = Integer.valueOf(lowPos).byteValue();
        try {
            str = new String(bt, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void e(String tag, String message) {
        if (CApp.logcat) {

        }
    }

    /**
     * 改变背景颜色
     *
     * @param activity
     * @param bo       true变暗 false正常
     */
    public static void backgroundAlter(Activity activity, boolean bo) {
        if (bo) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = 0.6f;
            activity.getWindow().setAttributes(lp);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = 1.0f;
            activity.getWindow().setAttributes(lp);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }
}
