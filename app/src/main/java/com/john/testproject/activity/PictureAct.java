package com.john.testproject.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;

import android.support.annotation.Nullable;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.john.testproject.R;
import com.john.testproject.photo.PhotographLogic;
import com.john.testproject.photo.PickPopupWindow;


import java.io.File;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/2/8 9:35
 * Description:
 */

public class PictureAct extends BaseAct {

    private ImageView imageView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_layout);
        imageView = findViewById(R.id.pic);
    }

    //项目会用到的选取照片的逻辑类
    public void test4(View view) {
        PickPopupWindow pickPopupWindow = new PickPopupWindow(this, new PickPopupWindow.PickBtnListener() {
            @Override
            public void onBtnClick(View view) {
                PhotographLogic.obtain(view, System.currentTimeMillis()+"logo_head.jpeg", true);
            }

        });
        pickPopupWindow.showPopupWindow(view);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotographLogic.onActivityResult(this, requestCode, resultCode, data, new PhotographLogic.PhotographCallback() {

            @Override
            public void obtainCroppedFile(File file, String photoName) {
                Glide.with(PictureAct.this).load(file).into(imageView);
            }

            @Override
            public void obtainBitmap(Bitmap bitmap, String photoName) {

            }
        });
    }


}
