package com.john.testproject.mvp.base.temp;

import com.john.testproject.mvp.base.module.MVPFModuleImp;
import com.john.testproject.mvp.base.module.MVPModule;
import com.john.testproject.mvp.base.presenter.BasePresenter;
import com.john.testproject.mvp.base.view.MVPView;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2017/12/27 0027 18:16
 * <p/>
 * Description:
 */

public class MVPPresenter extends BasePresenter<MVPView> {

    private MVPModule mvpfModule;

    public MVPPresenter() {
        mvpfModule = new MVPFModuleImp();
    }

    public void fetch(String s) {
//        mvpfModule.addSubscribe(HttpClient.getService(AppService.class).simpleGet(), new RxRequestCallBack<String>() {
//            @Override
//            public void onSuccess(HttpResult<String> httpResult) {
//                L.e("回调成功了" + httpResult.getData());
//                mMvpView.get().mvpActgetString(httpResult.getData());
//            }
//        });
//
//        mMvpView.get().mvpActgetLocal(mvpfModule.setLocalDatas(s));

        mvpfModule.getDatas(s, new MVPModule.LoadingCallBack() {
            @Override
            public void LoadingCompleted(String data) {
                mMvpView.get().mvpActgetString(data);
            }
        });

    }

}
