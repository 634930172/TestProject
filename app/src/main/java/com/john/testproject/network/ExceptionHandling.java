package com.john.testproject.network;

import android.util.Log;

import com.john.testproject.entity.HttpResult;


/**
 * Author: John
 * E-mail: xth@erongdu.com
 * Date: 2016/5/30 11:53
 * <p/>
 * Description: 异常处理
 */
@SuppressWarnings("unchecked")
final class ExceptionHandling {

    static void operate(final HttpResult result) {
        Log.e("TAG", "ExceptionHandling: "+"--code"+result.getCode()+"--msg"+result.getMsg());
//        if (result.getCode() != 414 && result.getCode() != 410 && result.getCode() != 413 && result.getCode() != 412 && result.getCode() != 411) {
//            Log.e("TAG", "operate: "+"--code");
//        }

    }
}
