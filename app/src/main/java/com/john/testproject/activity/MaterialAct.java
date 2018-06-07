package com.john.testproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.john.testproject.R;
import com.john.testproject.utils.Jump;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/2/26 10:10
 * Description:5.0材料设计新特性
 */

public class MaterialAct extends BaseAct {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_layout);
    }

    public void test1(View view) {
        Jump.lunch(this,FoldAct.class);
    }

    public void test2(View view) {
        Jump.lunch(this,StretchAct.class);
    }



}
