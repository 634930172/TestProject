package com.john.testproject.mvp.base.module;


import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/1/30 10:02
 * Description:module基类 获取数据等操作
 */

public class BaseModule {

    public <T> void addSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
