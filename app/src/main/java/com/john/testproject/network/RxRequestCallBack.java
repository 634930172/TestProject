package com.john.testproject.network;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.john.testproject.entity.HttpResult;
import com.john.testproject.utils.L;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/12/7 0007
 * <p/>
 * Description:
 */

public abstract class RxRequestCallBack<T> extends Subscriber<HttpResult<T>> implements DialogInterface.OnCancelListener {

    private LoadingDialog dialog;

    /**
     * 网络请求成功的回调方法，必须重写
     */
    public abstract void onSuccess(HttpResult<T> httpResult);

    /**
     * 默认访问请求
     */
    public RxRequestCallBack() {

    }

    /**
     * 请求有进度框
     * @param context 上下文
     */
    public RxRequestCallBack(Context context) {
        super();
        dialog = new LoadingDialog(context);
        dialog.setOnCancelListener(this);
        L.e("dialogshow>>>>>");
    }

    /**
     * 请求有进度框
     * @param context 上下文
     */
    public RxRequestCallBack(Context context,String loadingMsg) {
        super();
        dialog = new LoadingDialog(context,loadingMsg);
        dialog.setOnCancelListener(this);
        L.e("dialogshow>>>>>");
    }


    @Override
    public void onCompleted() {
        dismissDialog();
    }

    @Override
    public void onError(Throwable e) {
//        dismissDialog();
        if (e instanceof SocketTimeoutException) {
            Log.e("TAG", "SocketTimeoutException: 网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            Log.e("TAG", "ConnectException: 网络中断，请检查您的网络状态");
        } else if(e instanceof UnknownHostException){
            Log.e("TAG", "UnknownHostException: 网络中断，请检查您的网络状态");
        } else {
            Log.e("TAG", "onError:其他错误：" + e.getMessage() + "  cause: " + e.getCause());
        }
        e.printStackTrace();
    }

    @Override
    public void onNext(HttpResult<T> tHttpResult) {
        Log.e("TAG", "onNext: >>>>>>code--" + tHttpResult.getCode() + "--msg--" + tHttpResult.getMsg());
        if (tHttpResult.getCode() == 401) {
            Log.e("TAG", "onNext--Json_error");
        } else if (tHttpResult.getCode() != 200) {
            onFailed(tHttpResult);
            Log.e("TAG", "onNext--onFailed");
        } else {
            onSuccess(tHttpResult);
            Log.e("TAG", "onNext--onSuccess");
        }
    }

    private void onFailed(HttpResult<T> tHttpResult) {
    }


    private void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            L.e("dialogdismiss>>>>>");
        }
    }

    /**
     * dialog消失的时候取消订阅
     * @param dialogInterface
     */
    @Override
    public void onCancel(DialogInterface dialogInterface) {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if(dialog!=null){
            dialog.show();
        }
    }
}
