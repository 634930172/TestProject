package com.john.testproject.mvp.base.presenter;
import java.lang.ref.WeakReference;



public abstract class BasePresenter<V> implements Presenter<V> {

    protected WeakReference<V> mMvpView;

    @Override
    public void attachView(V mvpView) {
        this.mMvpView = new WeakReference<>(mvpView);

    }

    @Override
    public void detachView() {
        if (mMvpView != null) {
            mMvpView.clear();
            mMvpView = null;
        }
    }




}
