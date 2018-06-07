package com.john.testproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.john.testproject.R;
import com.john.testproject.fragment.FirstFragment;
import com.john.testproject.fragment.FourthFragment;
import com.john.testproject.fragment.SecondFragment;
import com.john.testproject.fragment.ThirdFragment;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/2/2 11:13
 * Description:
 */

public class SelectorAct extends BaseAct implements RadioGroup.OnCheckedChangeListener {

    private FrameLayout frameLayout;
    private RadioGroup radioGroup;
    private FirstFragment firstFragment;
    private FourthFragment fourthFragment;
    private SecondFragment sencondFragment;
    private ThirdFragment thirdFragment;
    private Fragment showFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_layout);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        frameLayout = findViewById(R.id.frameLayout);
        radioGroup = findViewById(R.id.radioGroup);
    }

    private void initData() {


    }

    private void initEvent() {
        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.getChildAt(0).performClick();
    }

    //暂时这么处理
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(showFragment!=null){
            fragmentTransaction.hide(showFragment);
        }
        switch (checkedId) {
            case R.id.radio1:
                if (firstFragment == null) {
                    firstFragment = new FirstFragment();
                    fragmentTransaction.add(R.id.frameLayout, firstFragment);
                }
                if (firstFragment.isHidden()) {
                    fragmentTransaction.show(firstFragment);
                }
                showFragment=firstFragment;
                break;
            case R.id.radio2:
                if (sencondFragment == null) {
                    sencondFragment = new SecondFragment();
                    fragmentTransaction.add(R.id.frameLayout, sencondFragment);
                }
                if (sencondFragment.isHidden()) {
                    fragmentTransaction.show(sencondFragment);
                }
                showFragment=sencondFragment;
                break;
            case R.id.radio3:
                if (thirdFragment == null) {
                    thirdFragment = new ThirdFragment();
                    fragmentTransaction.add(R.id.frameLayout, thirdFragment);
                }
                if (thirdFragment.isHidden()) {
                    fragmentTransaction.show(thirdFragment);
                }
                showFragment=thirdFragment;
                break;
            case R.id.radio4:
                if (fourthFragment == null) {
                    fourthFragment = new FourthFragment();
                    fragmentTransaction.add(R.id.frameLayout, fourthFragment);
                }
                if (fourthFragment.isHidden()) {
                    fragmentTransaction.show(fourthFragment);
                }
                showFragment=fourthFragment;
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }



}
