package com.john.testproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.john.testproject.utils.Jump;

import java.lang.ref.WeakReference;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/3 0003 12:21
 * <p/>
 * Description:启动页不渲染布局
 */

public class SplashAct extends BaseAct {

    private static final int START = 0x01;//开始
    private static final int GO_MAIN = 0x02;//主页面
    private static final int GO_GUIDE = 0X03;//引导页
    private static final int END = 0x04;//结束

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new SplashHandler(this).sendEmptyMessage(START);
    }

    private static class SplashHandler extends Handler {
        WeakReference<SplashAct> act;
        SplashHandler(SplashAct act) {
            this.act = new WeakReference<>(act);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START:
                    sendEmptyMessageDelayed(GO_MAIN, 1000);
                    break;
                case GO_MAIN:
                    Jump.lunch(act.get(), MainActivity.class);
                    sendEmptyMessageDelayed(END, 700);
                    break;
                case GO_GUIDE:
                    break;
                case END:
                    act.get().finish();
                    break;
            }
        }
    }

}
