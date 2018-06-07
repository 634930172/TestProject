package com.john.testproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.john.testproject.R;
import com.john.testproject.utils.T;
import com.john.testproject.widget.SweetPopWindow;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/10 0010 17:15
 * <p/>
 * Description:
 */

public class PopsAct extends BaseAct {

    private Button bt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pops_layout);
        bt = findViewById(R.id.bt);
    }

    public void test1(View view) {
        SweetPopWindow popWindow = new SweetPopWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                T.s(">>>>>");
            }
        });
        popWindow.showPopupWindow(bt);

    }

}
