package com.john.testproject.network;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: John
 * E-mail: hbh@erongdu.com
 * Date: 2016/4/22 19:31
 * <p/>
 * Description: 网络请求回调封装类
 */
public abstract class RequestCallBack<T> implements Callback<T> {
    public abstract void onSuccess(Call<T> call, Response<T> response);

    private Context context;

    public RequestCallBack(Context context) {
        this.context = context;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        Log.e("TAG", "onResponse: >>>>>>>>>>>>>>>code--"+response.code()+"--msg--"+response.message());
        if (response.isSuccessful() && response.body() != null) {
            Log.e("TAG", "onResponse--Succeed: >>>>>>>>>>>>>>>");
            onSuccess(call, response);
        } else {
            onFailed(call, response);
        }
    }

    private void onFailed(Call<T> call, Response<T> response) {
        Log.e("TAG", "onResponse--onFailed: >>>>>>>>>>>>>>>");
        //[200,300)返回true;
        if (response.code() >= 400) {
            Log.e("TAG", "onFailed:code--" + response.code() + "--msg" + response.message());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.e("TAG", "onFailure: >>>>>>>>>>>>>>>>>>>");
        if (t instanceof ApiException) {
            ExceptionHandling.operate(((ApiException) t).getResult());
        }
        if (t instanceof JSONException) {
            Log.e("TAG", "onFailure: >>>>>>>>>>>JSON解析异常");
        }
        if (t instanceof IOException) {
            Log.e("TAG", "onFailure: >>>>>>>>>>>网络连接错误");
        }
        t.printStackTrace();
    }
}
