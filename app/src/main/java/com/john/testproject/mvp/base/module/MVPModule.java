package com.john.testproject.mvp.base.module;

import com.john.testproject.entity.HttpResult;
import com.john.testproject.mvp.base.bean.GirlsParser;
import com.john.testproject.network.RxRequestCallBack;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/1/30 15:08
 * Description:Module回调
 */

public interface MVPModule {

    void getDatas(String strs, LoadingCallBack callBack);

    interface LoadingCallBack{
        void LoadingCompleted(String data);
    }

}
