package com.john.testproject.utils;

import android.widget.Toast;

/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/12/15 0015
 * <p/>
 * Description:
 */

public class T {

    public static void s(String msg) {
        Toast.makeText(ContextHolder.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void s(int id) {
        Toast.makeText(ContextHolder.getContext(), ContextHolder.getContext().getString(id), Toast.LENGTH_SHORT).show();
    }
}
