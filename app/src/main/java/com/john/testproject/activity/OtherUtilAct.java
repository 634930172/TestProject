package com.john.testproject.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.john.testproject.R;
import com.john.testproject.utils.FileUtil;
import com.john.testproject.utils.ImaZipUtil;
import com.john.testproject.utils.Jump;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/9 0009 11:28
 * <p/>
 * Description:
 */


public class OtherUtilAct extends BaseAct {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_layout);
    }

    public void test1(View view) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.splash);//本地图片转为bitmap
        ImaZipUtil.bitmapToBytes(bitmap);//bitmap转化为byte
        ImaZipUtil.bitmapToBytes(ImaZipUtil.zipPic(bitmap));//bitmap转化为byte;
    }

    public void test2(View view) {
//        FileUtil.createFilePath("test_Project");
    }

    //高效文件流操作
    public void test3(View view) throws IOException {
        FileInputStream inputStream = new FileInputStream("f://滑板//HEEL_FLIP.mp4");
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        FileOutputStream outputStream = new FileOutputStream("HEEL_FLIP.mp4");
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        int len;
        byte[] bs = new byte[1024];
        // 开始时间
        long begin = System.currentTimeMillis();
        while ((len = bis.read(bs)) != -1) {
            bos.write(bs, 0, len);
        }
        // 用时毫秒
        System.out.println(System.currentTimeMillis() - begin);// 78
        bis.close();
        bos.flush();//当输出流是bufferedOutputStream的时候，调用flush刷新，触发磁盘写入
        bos.close();
    }

    //日志打印类
    public void test4(View view) {
        Intent intent = new Intent(this, LogToastAct.class);
        startActivity(intent);
    }

    //Fragment切换
    public void test5(View view) {
        Intent intent = new Intent(this, SelectorAct.class);
        startActivity(intent);
    }


    //加密工具类
    public void test6(View view) {
        Intent intent = new Intent(this, EncryptionAct.class);
        startActivity(intent);
    }

    //取图片工具类
    public void test7(View view){
        Jump.lunch(this,PictureAct.class);
    }

}
