package com.john.testproject.utils;

import android.content.Context;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/11 0011 10:31
 * <p/>
 * Description:屏幕设备转化类
 */

public class ScreenUtils {

    /**
     * 获取设备宽度（px）
     *
     * @param context
     *
     * @return
     */
    public static int deviceWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取设备高度（px）
     *
     * @param context
     *
     * @return
     */
    public static int deviceHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

}
