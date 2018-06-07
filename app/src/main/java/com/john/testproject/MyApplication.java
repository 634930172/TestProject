package com.john.testproject;

import android.content.Context;
import android.support.annotation.NonNull;

import com.john.testproject.constans.BaseParmas;
import com.john.testproject.greendao.helper.GreenDaoHelper;
import com.john.testproject.utils.AppUitls;
import com.john.testproject.utils.ContextHolder;
import com.john.testproject.utils.SharedInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/10/31 0031
 * <p/>
 * Description:实际项目的初始化
 */

public class MyApplication extends BaseApplication {



    @Override
    public void onCreate() {
        super.onCreate();
        AppUitls.syncIsDebug(this);
        ContextHolder.init(this);
        SharedInfo.init(BaseParmas.SP_NAME);
        refWatcher = LeakCanary.install(this);//LeakCanary初始化
        GreenDaoHelper.initDatabase(this);//greenDao初始化
        //InitializeService.start(this);启动优化服务
    }


    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.green_normal, android.R.color.white);//全局设置主题颜色
                layout.setHeaderHeight(55);//头部高度
                layout.setHeaderTriggerRate(0.7f);//触发刷新距离 与 HeaderHieght 的比率1.0.4
                return new ClassicsHeader(context).setDrawableSize(16);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                layout.setFooterHeight(50);
                return new ClassicsFooter(context).setDrawableSize(18).setFinishDuration(0);
            }
        });
    }


    //LeakCanary
    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }
    private RefWatcher refWatcher;



}
