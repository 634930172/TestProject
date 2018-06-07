package com.john.testproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.john.testproject.R;
import com.john.testproject.improve.InitializeService;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/3 0003 11:50
 * <p/>
 * Description:
 */

public class LunchImproveAct extends BaseAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lunchimprove_layout);
    }

    //优化代码
    public  void test1(View view){
        InitializeService.start(this);
    }


}
