package com.john.testproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.john.testproject.R;
import com.john.testproject.utils.Jump;

public class MainActivity extends BaseAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //提交到git  更改了姓名
    }

    /**
     * 权限管理
     * @param view
     */
    public void test1(View view) {
        Intent intent = new Intent(this, PermissionAct.class);
        startActivity(intent);
        //该IP了
    }

    /**
     * 网络请求
     * @param view
     */
    public void test2(View view) {
        Intent intent = new Intent(this, NetWorkAct.class);
        startActivity(intent);
    }

    /**
     * 屏幕适配
     * @param view
     */
    public void test3(View view) {
        Intent intent = new Intent(this, ScreenFitAct.class);
        startActivity(intent);
    }

    /**
     * 文件操作
     * @param view
     */
    public void test4(View view) {
        Intent intent = new Intent(this, FileAct.class);
        startActivity(intent);
    }

    /**
     * Webview框架
     * @param view
     */
    public void test5(View view) {
        Intent intent = new Intent(this, WebAct.class);
        startActivity(intent);
    }

    /**
     * 自定义 view库
     * @param view
     */
    public void test6(View view) {
        Jump.lunch(this,WidgetAct.class);
    }

    /**
     * 日志打印工具类
     * @param view
     */
    public void test7(View view) {

    }

    /**
     * Glide图片管理类
     * @param view
     */
    public void test8(View view) {
        Jump.lunch(this,GlideAct.class);
    }

    /**
     * MVP架构类
     * @param view
     */
    public void test9(View view) {
        Intent intent = new Intent(this, MVPAct.class);
        startActivity(intent);
    }

    /**
     * 刷新框架
     * @param view
     */
    public void test10(View view) {
        Jump.lunch(this,RefreshAct.class);
    }

    /**
     * 启动优化
     * @param view
     */
    public void test11(View view) {
        Jump.lunch(this,LunchImproveAct.class);
    }


    public void test12(View view){
        Jump.lunch(this,OtherUtilAct.class);

    }

    /**
     * 数据库
     */
    public void test13(View view){
        Jump.lunch(this,GreenDaoAct.class);
    }




    public void test14(View view){
        Jump.lunch(this,MaterialAct.class);
    }


    /**
     * 自定义键盘
     */
    public void test15(View view){
        Jump.lunch(this,KeyBoardAct.class);
    }

}
