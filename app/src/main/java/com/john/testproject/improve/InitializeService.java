package com.john.testproject.improve;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.john.testproject.utils.L;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/3 0003 12:00
 * <p/>
 * Description:启动开启的优化服务
 */

public class InitializeService extends IntentService {
    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        init();
        L.e("异步启动");
    }

    private void init() {

    }
}
