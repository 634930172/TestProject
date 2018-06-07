package com.john.testproject.activity;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.john.testproject.R;
import com.john.testproject.mvp.base.activity.BaseMVPActivity;
import com.john.testproject.mvp.base.temp.MVPFragment;
import com.john.testproject.mvp.base.temp.MVPPresenter;
import com.john.testproject.mvp.base.view.MVPView;
import com.john.testproject.utils.Jump;
import com.john.testproject.utils.L;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2017/12/27 0027 10:02
 * <p/>
 * Description:
 */

public class MVPAct extends BaseMVPActivity<MVPView,MVPPresenter> implements MVPView {


    @BindView(R.id.tv)
    Button tv;
    @BindView(R.id.jp)
    Button jp;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.fl)
    FrameLayout fl;
    private MVPFragment fragment;

    @Override
    protected int getLayoutId() {
        return R.layout.mvp_layout;
    }

    @Override
    protected MVPPresenter createPresenter() {
        return new MVPPresenter();
    }


    @OnClick({R.id.tv, R.id.ll,R.id.jp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv:
                L.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                if (!fragment.isAdded()) {
                    getSupportFragmentManager().beginTransaction().add(R.id.fl, fragment, fragment.getClass().getSimpleName()).show(fragment).commitAllowingStateLoss();
                }
                break;
            case R.id.ll:
                Jump.lunch(this,MVPAct.class);
                break;
            case R.id.jp:
                Jump.lunch(this,MVPAct.class);
                break;
        }
    }

    @Override
    protected void initData() {
        fragment = new MVPFragment();
        mPresenter.fetch("我的");
    }

    @Override
    public void mvpActgetString(String str) {
        L.e("MVP回调过来的数据>>>"+str);
    }

    @Override
    public void mvpActgetLocal(String str) {
        L.e("MVP回调过来的本地数据"+str);
    }

}
