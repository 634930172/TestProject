package com.john.testproject.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.john.testproject.R;
import com.john.testproject.utils.AppUitls;
import com.john.testproject.utils.PermissonCheck;
import com.john.testproject.utils.PhoneUtil;

/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/10/18 0018
 * <p/>
 * Description:
 */

public class PermissionAct extends BaseAct {

    private TextView tv;
    int i = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_layout);
        tv = (TextView) findViewById(R.id.tv);
        tv.setText(String.valueOf("当前模式：" + (AppUitls.isDebug() ? "debug" : "release")));

    }

    public void test2(View view) {
        Log.e("TAG", ">>>>>>>>>>>>>>>>>>>>>>>");
        PermissonCheck.getInstance().requestPermission(this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS}, 0, new PermissonCheck.PermissionsResultListener() {
            @Override
            public void permissionNext(int requestCode) {
                Log.e("TAG", "permissionSuccess: " + requestCode);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //获取信息注意使用子线程
                        //拒绝了的话弹出对话框 通过size再判断是否获取到了  只能通过这样的方法判断 别无他法
                        int constassize = PhoneUtil.getContacts(PermissionAct.this).size();
                        if (constassize != 0) {
                            Log.e("TAG", "已经获取到联系人,条数>>>>>" + constassize);
                        } else {
                            PermissonCheck.getInstance().showTipsDialog(PermissionAct.this);//提示的时候让用户自己去设置界面 部分机型不兼容此类跳转
                        }
                    }
                }).start();
            }

            @Override
            public void permissionFail(int requestCode) {
                Log.e("TAG", "permissionFail: " + requestCode);
            }
        });
    }

    public void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("该操作需要被赋予相应的权限，不开启将无法正常工作！")
                .setPositiveButton("开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i = new Intent();
                        i.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        i.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivity(i);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    public void test1(View view){
        AppUitls.loadApps(this);
    }
}
