package com.john.testproject.mvp.base.temp;

import com.john.testproject.mvp.base.module.MVPFModuleImp;
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
    private MVPModule mvpfModule;
    //面向接口编程
    MVPFPresenter(){
        mvpfModule=new MVPFModuleImp();

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
