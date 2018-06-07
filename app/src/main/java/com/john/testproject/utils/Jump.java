package com.john.testproject.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2017/12/29 0029 9:33
 * <p/>
 * Description:界面跳转类
 */

public class Jump {

    private static final String BUNDLE = "bundle";

    public static void lunch(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }

    public static void lunchForResult(Activity activity, Class<?> cls, int requestCode) {
        Intent intent = new Intent(activity, cls);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void lunch(Activity activity, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra(BUNDLE, bundle);
        activity.startActivity(intent);
    }

    public static void lunchForResult(Activity activity, Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra(BUNDLE, bundle);
        activity.startActivityForResult(intent, requestCode);
    }

}
