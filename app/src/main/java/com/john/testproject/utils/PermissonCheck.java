package com.john.testproject.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/11/9 0009
 * <p/>
 * Description:权限管理工具类
 */

public class PermissonCheck {

    private int REQUEST_CODE_PERMISSION = 0x00099;

    private static final PermissonCheck ourInstance = new PermissonCheck();

    public static PermissonCheck getInstance() {
        return ourInstance;
    }

    private PermissonCheck() {
    }

    private PermissionsResultListener listener;

    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     * @param requestCode 请求权限的请求码
     */
    public PermissonCheck requestPermission(Activity activity, String[] permissions, int requestCode, PermissionsResultListener listener) {
        this.REQUEST_CODE_PERMISSION = requestCode;
        this.listener = listener;
        if (checkPermissions(activity, permissions)) {
            Log.e("TAG", "requestPermission: 权限已授权或4.0以下");
            if (listener != null) {
                listener.permissionNext(REQUEST_CODE_PERMISSION);
            }
        } else {
            Log.e("TAG", "requestPermission:没有权限");
            List<String> needPermissions = getDeniedPermissions(activity, permissions);
            ActivityCompat.requestPermissions(activity, needPermissions.toArray(new String[needPermissions.size()]), REQUEST_CODE_PERMISSION);
        }
        return this;
    }

    /**
     * 检测所有的权限是否都已授权
     *
     * @param permissions
     * @return
     */
    public boolean checkPermissions(Activity activity, String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    private List<String> getDeniedPermissions(Activity activity, String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }


    /**
     * 系统请求权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (verifyPermissions(grantResults)) {
                //授权成功回调
                Log.e("TAG", "onRequestPermissionsResult: 回调成功" );
                if (listener != null) {
                    listener.permissionNext(REQUEST_CODE_PERMISSION);
                }
            } else {
                //授权失败回调
                Log.e("TAG", "onRequestPermissionsResult: 回调失败" );
                if (listener != null) {
                    listener.permissionFail(REQUEST_CODE_PERMISSION);
                }
                if (shouldShowDialog(activity, permissions)) {
                    showTipsDialog(activity);
                }
            }
        }
    }

    /**
     * 确认所有的权限是否都已授权
     *
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示对话框
     */
    public void showTipsDialog(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("提示信息")
                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings(activity);
                    }
                }).show();
    }

    /**
     * 启动当前应用设置页面
     */
    private void startAppSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
    }


    private boolean shouldShowDialog(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) !=
                    PackageManager.PERMISSION_GRANTED && !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }

    public interface PermissionsResultListener {
        void permissionNext(int requestCode);

        void permissionFail(int requestCode);
    }
}
