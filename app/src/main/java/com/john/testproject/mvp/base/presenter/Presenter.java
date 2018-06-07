package com.john.testproject.mvp.base.presenter;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
