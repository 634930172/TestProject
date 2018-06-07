package com.john.testproject.glide;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.stream.HttpUrlGlideUrlLoader;
import com.bumptech.glide.module.GlideModule;
import com.john.testproject.utils.StorageUtils;

import java.io.InputStream;


/**
 * Glide配置信息
 * Created by leichao on 2017/3/4.
 */
public class GlideConfiguration implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 设置缓存路径和允许缓存的最大大小 10MB
        builder.setDiskCache(new DiskLruCacheFactory(StorageUtils.getIndividualCacheDirectory(context,"Glide_Cache").getAbsolutePath(),
                10 * 1024 * 1024));
        // 设置图片为ARGB_888888
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    //这么写的原因是什么---------------遗留的问题,后面解决-----------------------------------------
    @Override
    public void registerComponents(Context context, Glide glide) {
        // 将Glide的内置引擎替换为OkHttp,与Retrofit一致，方便处理  // 使用OkHttp加载网络图片
        glide.register(GlideUrl.class, InputStream.class, new OkHttpModeLoader.Factory());
    }
}
