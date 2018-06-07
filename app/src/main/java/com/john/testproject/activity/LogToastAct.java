package com.john.testproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.john.testproject.R;
import com.john.testproject.utils.L;
import com.john.testproject.utils.T;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2017/12/25 0025 17:48
 * <p/>
 * Description:
 */

public class LogToastAct extends BaseAct {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logtoast_layout);

    }

    public void test1(View view) {
        T.s(">>>>>>>>>>>>>>>>>>>>>>>");
        L.e(">>>>>>>>>>>>>>>>>>>>>>>");
    }


}
