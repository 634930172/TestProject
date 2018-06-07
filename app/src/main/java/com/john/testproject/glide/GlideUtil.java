package com.john.testproject.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.john.testproject.utils.ContextHolder;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/1/31 9:52
 * Description:Glide工具类
 */

public class GlideUtil {

    /**
     * 基本使用
     */
    public static void setImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    /**
     * 动画效果
     */
    public static void setImagewithAnim(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).dontAnimate().into(imageView);
    }

    /**
     * 动画效果
     */
    public static void setImagewithAnim(ImageView imageView, int resId,int anim) {
        Glide.with(imageView.getContext()).load(resId).animate(anim).into(imageView);
    }

    /**
     * 基本使用
     */
    public static void setImage(ImageView imageView, int resId) {
        Glide.with(imageView.getContext()).load(resId).into(imageView);
    }

    /**
     * 加载圆形图片
     */
    public static void loadCircleImage(ImageView imageView, int resId) {
        Context context = imageView.getContext();
        Glide.with(context).load(resId).transform(new TransformCircle(context)).into(imageView);
    }

    /**
     * 加载图片，并将图片变为圆形
     *
     * @param url 网络路径或本地路径
     */
    public static void loadCircleDefaullt(ImageView imageView, int defaultResId, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).transform(new TransformCircle(context))
                .placeholder(defaultResId)
                .error(defaultResId)
                .into(imageView);
    }

    /**
     * 加载图片，并将图片变为圆角
     *
     * @param dp 圆角的大小，单位dp
     */
    public static void loadRoundImage(ImageView imageView, byte[] data, int dp, RequestListener<? super byte[], GlideDrawable> listener) {
        Context context = imageView.getContext();
        Glide.with(context).load(data).transform(new TransformRound(context, dp)).listener(listener).into(imageView);
    }

    public static void loadRoundImage(ImageView imageView, int resId, int dp) {
        Context context = imageView.getContext();
        Glide.with(context).load(resId).transform(new TransformRound(context, dp)).into(imageView);
    }

    /**
     * 清除缓存
     */
    public static void clearCache(){
        Glide.get(ContextHolder.getContext()).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
        Glide.get(ContextHolder.getContext()).clearMemory();//清理内存缓存 可以在UI主线程中进行
    }

}
