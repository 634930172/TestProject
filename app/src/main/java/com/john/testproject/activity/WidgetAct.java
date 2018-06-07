package com.john.testproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.john.testproject.R;
import com.john.testproject.utils.Jump;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/9 0009 15:42
 * <p/>
 * Description:
 */

public class WidgetAct extends BaseAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_main_layout);
    }

    public void test1(View view) {
        Jump.lunch(this, GuideAct.class);
    }

    public void test2(View view) {
        Jump.lunch(this, CarouselAct.class);
    }


    public void test3(View view) {
        Jump.lunch(this, DialogsAct.class);
    }

    public void test4(View view) {
        Jump.lunch(this, PopsAct.class);
    }

    public void test5(View view) {
        Jump.lunch(this, SlideAct.class);
    }

    public void test6(View view) {
        Jump.lunch(this, SlideTextAc.class);
    }

}
