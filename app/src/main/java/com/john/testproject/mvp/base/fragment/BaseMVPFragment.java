package com.john.testproject.mvp.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.john.testproject.MyApplication;
import com.john.testproject.mvp.base.presenter.BasePresenter;
import com.squareup.leakcanary.RefWatcher;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMVPFragment<V,P extends BasePresenter<V>> extends RxFragment {
    public Activity mActivity;
    protected View mRootView;
    protected P mPresenter;
    private Unbinder unbinder;

    protected abstract int getLayoutId();

    protected abstract P createPresenter();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initView();
        initData();
        initEvent();
    }

    private void init(View view) {
        unbinder = ButterKnife.bind(this, view);
        mActivity = getActivity();
    }


    /**
     * 初始化View
     */
    protected void initView() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 初始化事件
     */
    protected void initEvent() {
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
        //LeakCanary
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

}
