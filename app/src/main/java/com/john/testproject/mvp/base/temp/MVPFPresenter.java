package com.john.testproject.mvp.base.temp;

import com.john.testproject.entity.HttpResult;
import com.john.testproject.mvp.base.bean.GirlsParser;
import com.john.testproject.mvp.base.module.MVPFModule;
import com.john.testproject.mvp.base.module.MVPModule;
import com.john.testproject.mvp.base.presenter.BasePresenter;
import com.john.testproject.mvp.base.view.MVPFView;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2017/12/27 0027 18:16
 * <p/>
 * Description:
 */

class MVPFPresenter extends BasePresenter<MVPFView> {
    private MVPFModule mvpfModule;

    MVPFPresenter(){
        mvpfModule=new MVPFModule();

    }

    void fetch(String s) {
//        mMvpView.get().getFString(s+"sssss");
        //回调的正确姿势
        mvpfModule.getDatas(s, new MVPModule.LoadingCallBack() {
            @Override
            public void LoadingCompleted(String data) {
                mMvpView.get().getFString(data);
            }
        });
    }


}
