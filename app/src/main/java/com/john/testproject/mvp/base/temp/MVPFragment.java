package com.john.testproject.mvp.base.temp;


import com.john.testproject.R;
import com.john.testproject.mvp.base.fragment.BaseMVPFragment;
import com.john.testproject.mvp.base.view.MVPFView;
import com.john.testproject.utils.L;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2017/12/28 0028 09:16
 * <p/>
 * Description:
 */

public class MVPFragment extends BaseMVPFragment<MVPFView,MVPFPresenter> implements MVPFView {

    @Override
    protected int getLayoutId() {
        return R.layout.mvpf_layout;
    }

    @Override
    protected MVPFPresenter createPresenter() {
        return new MVPFPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.fetch("sss");
    }

    @Override
    public void getFString(String str) {
        L.e(str + ">>>>>>>>>>>>>>");
    }
}
