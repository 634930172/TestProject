package com.john.testproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.john.testproject.R;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/2/26 10:15
 * Description:视图拉伸效果
 */

public class StretchAct extends BaseAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stretch_layout);
    }
}
