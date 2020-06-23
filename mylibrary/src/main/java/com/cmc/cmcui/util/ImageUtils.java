package com.cmc.cmcui.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * 添加第三方库
 * implementation 'com.github.bumptech.glide:glide:4.9.0'
 * compile 'jp.wasabeef:glide-transformations:3.0.1'
 * compile 'jp.co.cyberagent.android.gpuimage:gpuimage-library:1.4.1'
 */
public class ImageUtils {

    /**
     * 加载正常图片
     *
     * @param context   上下文
     * @param url       图片地址
     * @param imageView 控件
     */
    public static void getNormal(Context context, Object url, ImageView imageView) {
        if (url instanceof String) {
            String imgUrl = (String) url;
            if (imgUrl.contains("gif") && imgUrl.contains("GIF")) {
                Glide.with(context).asGif().load(url).into(imageView);
            } else {
                Glide.with(context).load(url).into(imageView);
            }
        } else {
            Glide.with(context).load(url).into(imageView);
        }
    }

    /**
     * 加载圆形图片
     *
     * @param context   上下文
     * @param url       图片地址
     * @param imageView 控件
     */
    public static void getRoundness(Context context, Object url, ImageView imageView) {
        if (context == null && url == null && imageView == null) {
            return;
        }
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);
        if (url instanceof String) {
            String imgUrl = (String) url;
            if (imgUrl.contains("gif") && imgUrl.contains("GIF")) {
                Glide.with(context).asGif().load(url).apply(mRequestOptions).into(imageView);
            } else {
                Glide.with(context).load(url).apply(mRequestOptions).into(imageView);
            }
        } else {
            Glide.with(context).load(url).apply(mRequestOptions).into(imageView);
        }
    }

    /**
     * 切圆角
     *
     * @param context   上下文
     * @param url       图片地址
     * @param de        圆角度数
     * @param imageView 控件
     */
    public static void getRounded(Context context, Object url, int de, ImageView imageView) {
        RoundedCorners roundedCorners = new RoundedCorners(de);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = bitmapTransform(roundedCorners).override(300, 300);
        if (url instanceof String) {
            String imgUrl = (String) url;
            if (imgUrl.contains("gif") && imgUrl.contains("GIF")) {
                Glide.with(context).asGif().load(url).apply(options).into(imageView);
            } else {
                Glide.with(context).load(url).apply(options).into(imageView);
            }
        } else {
            Glide.with(context).load(url).apply(options).into(imageView);
        }

    }

    /**
     * 毛玻璃效果图片
     *
     * @param context   上下文
     * @param url       图片地址
     * @param de        模糊度
     * @param imageView 控件
     */
    public static void getGround(Context context, Object url, int de, ImageView imageView) {
        if (url instanceof String) {
            String imgUrl = (String) url;
            if (imgUrl.contains("gif") && imgUrl.contains("GIF")) {
                Glide.with(context).asGif().load(url)
                        .apply(bitmapTransform(new BlurTransformation(de)))
                        .into(imageView);
            } else {
                Glide.with(context).load(url)
                        .apply(bitmapTransform(new BlurTransformation(de)))
                        .into(imageView);
            }
        } else {
            Glide.with(context).load(url)
                    .apply(bitmapTransform(new BlurTransformation(de)))
                    .into(imageView);
        }
    }

    /**
     * View转Bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap getViewBitmap(View view) {
        if (view == null) return null;
        Bitmap shareBitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(shareBitmap);
        view.draw(c);
        return shareBitmap;
    }

    /**
     * Bitmap图片保存到本地
     *
     * @param context 上下文
     * @param bmp     Bitmap对象
     */
    public static void saveImageToGallery(final Context context, final Bitmap bmp) {
        // TODO: 2017/2/20 android6.0权限申请https://github.com/anthonycr/Grant
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            File appDir = new File(Environment.getExternalStorageDirectory(), ".png");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            File file = new File(appDir, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        file.getAbsolutePath(), fileName, null);
                CAPPUtil.showPrompt(context, "保存成功");
                String parent = file.getParent();
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(parent)));
                //dismissProgressDialog();
            } catch (FileNotFoundException e) {
                CAPPUtil.showPrompt(context, "保存失败");
                e.printStackTrace();
            }
        } else {
            CAPPUtil.showPrompt(context, "请允许存储权限");
        }
    }

    /**
     * 根据图片的url路径获得Bitmap对象(必须放到子线程)
     * <p>
     * 可配合saveImageToGallery（）方法实现图片保存到本地
     *
     * @param url
     * @return
     */
    public static Bitmap returnBitmap(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * bitmap转file
     *
     * @param bitmap
     * @return
     */
    public static File bitmapToFile(Bitmap bitmap) {
        File file = null;
        file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/DCIM/Camera", System.currentTimeMillis() + ".jpeg");
        file.getParentFile().mkdirs();
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * file转Bitmap
     *
     * @param file
     * @param width
     * @param height
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Bitmap fileToBitmap(File file, int width, int height) {
        Bitmap bitmap = null;
        if (file != null && file.exists()) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file.getPath(), opts);
                // 计算图片缩放比例
                final int minSideLength = Math.min(width, height);
                opts.inSampleSize = computeSampleSize(opts, minSideLength, width * height);
                opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try {
                bitmap = BitmapFactory.decodeFile(file.getPath(), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    public static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128
                : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * bitmap转byte[]
     *
     * @param bitmap
     * @return
     */
    public static byte[] bitmapToBytes(Bitmap bitmap) {
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        bitmap.recycle();
        bytes = baos.toByteArray();
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * byte[]转bitmap
     *
     * @param bytes
     * @return
     */
    public static Bitmap bytesToBitmap(byte[] bytes) {
        Bitmap bitmap = null;
        if (bytes != null && bytes.length != 0) {
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return bitmap;
    }

    /**
     * bitmap转Drawable
     *
     * @param bitmap
     * @return
     */
    public static Drawable bitmapToDrawable(Context context, Bitmap bitmap) {
        Drawable drawable = null;
        if (context != null) {
            Resources res = context.getResources();
            drawable = new BitmapDrawable(res, bitmap);
        }
        return drawable;
    }

    /**
     * drawable转Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 可能会有点耗时，可以在子线程调用
     *
     * @param srcFile 要压缩的图片文件
     * @param path    压缩后的图片文件路径
     * @return File 压缩成功后的图片文件
     */
    public static File bitmapCompress(File srcFile, String path, int tagWidth, int tagHeight) {

        if (srcFile == null || !srcFile.exists()) {
            throw new RuntimeException("图片文件不存在");
        }
        if (TextUtils.isEmpty(path)) {
            return null;
        } else {
            if (path.contains(".")) {
                path = path.substring(0, path.lastIndexOf("."));
                path = path + ".jpg";//jpg格式
            } else {
                path = path + ".jpg";//jpg格式
            }
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//设置为true，不会申请内存，可以得到原生的宽和高
        Bitmap bitmap = BitmapFactory.decodeFile(srcFile.getAbsolutePath(), options);
        int outWidth = options.outWidth;//原生的宽
        int outHeight = options.outHeight;//原生的高
        /**
         * 图片大小（分辨率）压缩
         * options.inSampleSize  这是压缩比率，实际压缩比率根据自己需求通过算法计算
         */
        options.inSampleSize = getSampleSize(outWidth, outHeight, tagWidth, tagHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap2 = BitmapFactory.decodeFile(srcFile.getAbsolutePath(), options);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        /**
         * 这里是图片质量压缩，第二个参数表示压缩率，100表示不压缩，0表示最大压缩
         */
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 60, stream);
        bitmap2.recycle();

        FileOutputStream outputStream = null;
        File tagFile = new File(path);
        try {
            if (!tagFile.exists()) {
                tagFile.createNewFile();
            }
            outputStream = new FileOutputStream(tagFile);
            outputStream.write(stream.toByteArray());
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tagFile;
    }

    /**
     * 压缩比率 每次减少0.5倍
     *
     * @param srcWidth  原生的宽
     * @param srcHeight 原生的高
     * @param dstWidth  目标宽
     * @param dstHeight 目标高
     * @return
     */
    public static int getSampleSize(int srcWidth, int srcHeight, int dstWidth, int dstHeight) {

        int widthSize = 0;
        int heightSize = 0;

        while (srcWidth > dstWidth) {
            widthSize += 2;
            srcWidth = srcWidth / 2;
        }

        while (srcHeight > dstHeight) {
            heightSize += 2;
            srcHeight = (srcHeight / 2);
        }

        if (widthSize > heightSize) {
            return widthSize;
        } else {
            return heightSize;
        }
    }

    /**
     * 获取file
     *
     * @param phon 图片路径
     * @return
     */
    public static File setPhon(String phon) {
        Bitmap bitmap = BitmapFactory.decodeFile(phon);
        return compressImage(bitmap);
    }

    /**
     * 压缩图片（质量压缩）
     *
     * @param bitmap
     */
    public static File compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(), filename + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        return file;
    }

    /**
     * 截取视频某一帧
     *
     * @param filePath 视频路径
     * @param kind     第几帧
     * @return
     */
    public static Bitmap createVideoThumbnail(String filePath, int kind) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (filePath.startsWith("http://")
                    || filePath.startsWith("https://")
                    || filePath.startsWith("widevine://")) {
                retriever.setDataSource(filePath, new Hashtable<String, String>());
            } else {
                retriever.setDataSource(filePath);
            }
            bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC); //retriever.getFrameAtTime(-1);
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
            ex.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
                ex.printStackTrace();
            }
        }

        if (bitmap == null) {
            return null;
        }

        if (kind == MediaStore.Images.Thumbnails.MINI_KIND) {//压缩图片 开始处
            // Scale down the bitmap if it's too large.
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int max = Math.max(width, height);
            if (max > 512) {
                float scale = 512f / max;
                int w = Math.round(scale * width);
                int h = Math.round(scale * height);
                bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
            }//压缩图片 结束处
        } else if (kind == MediaStore.Images.Thumbnails.MICRO_KIND) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap,
                    96,
                    96,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }
}
