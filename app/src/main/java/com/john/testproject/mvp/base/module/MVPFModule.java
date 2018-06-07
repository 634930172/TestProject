package com.john.testproject.mvp.base.module;

import com.john.testproject.entity.HttpResult;
import com.john.testproject.network.AppService;
import com.john.testproject.network.HttpClient;
import com.john.testproject.network.RxRequestCallBack;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/1/30 9:07
 * Description:Module数据交互类
 */

public class MVPFModule extends BaseModule implements MVPModule {

    public String setLocalDatas(String str){
        return str+"从MVPFModule转了一圈";
    }


    @Override
    public void getDatas(String strs, final LoadingCallBack callBack) {
        addSubscribe(HttpClient.getService(AppService.class).simpleGet(), new RxRequestCallBack<String>() {
            @Override
            public void onSuccess(HttpResult<String> httpResult) {
                callBack.LoadingCompleted(httpResult.getData());
            }
        });
    }
}
