package com.john.testproject.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.john.testproject.R;
import com.john.testproject.utils.ContextHolder;
import com.john.testproject.utils.L;
import com.john.testproject.utils.StorageUtils;

import java.io.File;


/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/12/15 0015
 * <p/>
 * Description:
 */

public class FileAct extends BaseAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_layout);
    }

    public void test1(View view) {
        String fileStoreDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testProject";//放到外部存储的共有目录，应用被删除了此文件也不会被删除，
        //  而且最好在共有目录下再建一个新的目录，因为最外层的目录是所有应用共享的
        File externalFilesDir = ContextHolder.getContext().getExternalFilesDir(null);//放到外部存储的私有目录，应用删除后此文件也会被删除，位置在files目录下面
        File fileDir = ContextHolder.getContext().getFilesDir();//内部文件存储，应用删除，文件也会被删除
        File cacheDir = ContextHolder.getContext().getCacheDir();//内部文件存储，应用删除，文件也会被删除
        Log.e("TAG", "外部存储共有目录: " + fileStoreDir.toString());
        Log.e("TAG", "外部存储私有目录: " + externalFilesDir.toString());
        Log.e("TAG", "内部存储文件: " + fileDir.toString());
        Log.e("TAG", "内部存储缓存: " + cacheDir.toString());
    }

    //文件缓存在SD卡私有目录或者系统目录，应用被删除后此方法生成的文件也被删除
    public void test2(View view) {
        L.e(StorageUtils.getIndividualCacheDirectory(this, "File_Cache").getAbsolutePath());
    }

    //文件缓存在SD卡公共目录或系统目录，1.在SD卡公共目录，应用删除后该目录还是会保留
    //2.在系统目录，应用删除后文件也被删除
    public void test3(View view) {
        L.e(StorageUtils.getOwnCacheDirectory(this, getPackageName() + "/No_Cache4").getAbsolutePath());
    }


    //没有用到StorageUtils 所以生成文件时判断是否生成文件，没有生成文件则调用file.mkdir();
    public void test4(View view) {
        String fileStoreDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TestPPP2";//放到外部存储的共有目录，应用被删除了此文件也不会被删除，
        //  而且最好在共有目录下再建一个新的目录，因为最外层的目录是所有应用共享的
        File file = new File(fileStoreDir);
        if (!file.exists()) {
            L.e("文件不存在");
            if (!file.mkdir()) {
                L.e("创建不成功");
            }else {
                L.e("创建成功");
            }
        }else {
            L.e("文件已存在");
        }
    }

}
