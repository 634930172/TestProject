package com.john.testproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.john.testproject.R;
import com.john.testproject.glide.GlideUtil;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/1/31 9:48
 * Description:
 */

public class GlideAct extends BaseAct {

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glide_layout);
        imageView = findViewById(R.id.img);
    }

    public void test1(View view) {
        GlideUtil.setImage(imageView, R.mipmap.ic_launcher);
    }


}
