package com.john.testproject.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/12 0012 15:14
 * <p/>
 * Description:图片压缩工具类
 */

public class ImaZipUtil {


    /**
     * @param image
     * @return 如果以文件的形式进行压缩 质量压缩是有效果的
     * @Description 质量压缩方法
     * @author XiongJie
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        L.e("尺寸压缩后的size:" + baos.toByteArray().length / 1024 + "kb");
        int options = 100;
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 20) {
            // 重置baos即清空baos
            L.e("size:" + baos.toByteArray().length / 1024 + " options:" + options);
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.PNG, options, baos);
            // 每次都减少10
            options -= 10;
            if (options == 0) {
                break;
            }
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        return BitmapFactory.decodeStream(isBm, null, null);
    }

    /**
     * 只进行分辨率压缩，不进行图片的质量压缩
     *
     * @param sourceBm
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static Bitmap zipPicWithoutCompress(Bitmap sourceBm, float targetWidth, float targetHeight) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        // 可删除
        newOpts.inPurgeable = true;
        // 可共享
        newOpts.inInputShareable = true;
        // 转成数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        sourceBm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] temp = baos.toByteArray();
        // 此时返回bm为空
        Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = targetHeight;
        float ww = targetWidth;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        // be=1表示不缩放
        int be = 1;
        if (w > h && w > ww) {
            // 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            // 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) {
            be = 1;
        }
        // 设置缩放比例
        newOpts.inSampleSize = be;
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length, newOpts);
        // 压缩好比例大小后再进行质量压缩
        return bitmap;
    }


    /**
     * 压缩图片到指定宽高，并进行质量压缩，最终大小保持在100K以下
     *
     * @param sourceBm
     * @return
     */
    public static Bitmap zipPic(Bitmap sourceBm) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        // 可删除
        newOpts.inPurgeable = true;
        // 可共享
        newOpts.inInputShareable = true;
        // 转成数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        sourceBm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] temp = baos.toByteArray();
        // 此时返回bm为空
        Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float ww = 800;
        float hh = 480;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        // 如果宽度大的话根据宽度固定大小缩放
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            // 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) {
            be = 1;
        }
        // 设置缩放比例
        newOpts.inSampleSize = be;
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length, newOpts);
//        return compressImage(bitmap);//质量压缩意义不大
        return bitmap;
    }

    /**
     * 压缩图片到指定宽高，并进行质量压缩，最终大小保持在100K以下
     */
    public static Bitmap zipPic(Bitmap sourceBm, float targetWidth, float targetHeight) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        // 可删除
        newOpts.inPurgeable = true;
        // 可共享
        newOpts.inInputShareable = true;
        // 转成数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        sourceBm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] temp = baos.toByteArray();
        // 此时返回bm为空
        Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        // 如果宽度大的话根据宽度固定大小缩放
        if (w > h && w > targetWidth) {
            be = (int) (newOpts.outWidth / targetWidth);
        } else if (w < h && h > targetHeight) {
            // 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / targetHeight);
        }
        if (be <= 0) {
            be = 1;
        }
        // 设置缩放比例
        newOpts.inSampleSize = be;
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;

        bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length, newOpts);
        // 压缩好比例大小后再进行质量压缩
        return compressImage(bitmap);
    }

    /**
     * Bitmap转byte[]
     *
     * @param bitmap Bitmap
     * @return
     */
    public static byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        L.e("位图大小：" + baos.toByteArray().length / 1024 + "kb");
        return baos.toByteArray();
    }


    /**
     * byte[]转Bitmap
     *
     * @param bytes bytes
     * @return
     */
    public static Bitmap byteToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    /**
     * 将像素减半以读取资源文件
     *
     * @param context 上下文
     * @param resId   资源ID
     * @return bitmap
     */
    public static Bitmap readBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        return BitmapFactory.decodeResource(context.getResources(), resId, opt);
    }
}
