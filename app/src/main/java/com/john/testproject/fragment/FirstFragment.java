package com.john.testproject.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.john.testproject.R;
import com.john.testproject.utils.L;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/2/2 11:04
 * Description:
 */

public class FirstFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        L.e("第1个Fragment加载了");
        return inflater.inflate(R.layout.fragment1_layout,container,false);
    }
}
