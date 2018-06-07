package com.john.testproject.fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.john.testproject.mvp.base.fragment.BaseMVPFragment;
import com.john.testproject.mvp.base.presenter.BasePresenter;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/2/2 16:42
 * Description: 懒加载基类
 */
public abstract class BaseLazyFragment<V, P extends BasePresenter<V>> extends BaseMVPFragment<V,P> {
    /**
     * 懒加载过
     */
    private boolean isLazyLoaded;

    private boolean isPrepared;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        //只有Fragment onCreateView好了，
        //另外这里调用一次lazyLoad(）
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    /**
     * 调用懒加载
     */

    private void lazyLoad() {
        if (getUserVisibleHint() && isPrepared && !isLazyLoaded) {
            onLazyLoad();
            isLazyLoaded = true;
        }
    }

    // @UiThread
    /**
     * 懒加载-等页面显示出来后再加载，该操作只执行一次
     */
    public abstract void onLazyLoad();
}


