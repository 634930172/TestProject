package com.john.testproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.john.testproject.R;
import com.john.testproject.widget.SweetDialog;


/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/10 0010 17:15
 * <p/>
 * Description:
 */

public class DialogsAct extends BaseAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogs_layout);
    }

    public void test1(View view){
        final SweetDialog dialog=new SweetDialog(this,"保存成功", new SweetDialog.SweetOnClickListener() {
            @Override
            public void onCancelClick(SweetDialog sweetDialog) {
                sweetDialog.dismiss();
            }

            @Override
            public void onConfirmClick(SweetDialog sweetDialog) {

            }
        });
        dialog.show();
    }

    public void test2(View view){
      SweetDialog dialog=new SweetDialog(this, "撒大声地开奖号噶水电费客家话桑开始的发挥当升科技电光sdfsd的接口火龙果速度快回复金刚砂的看法和十几个地方开始吉电股份 火石考虑对方", new SweetDialog.SweetOnToastClickListener() {
          @Override
          public void onConfirmClick(SweetDialog sweetDialog) {
              sweetDialog.dismiss();
          }
      });
        dialog.show();
    }

}
