package com.john.testproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import com.john.testproject.R;


/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/2/26 10:14
 * Description:5.0折叠效果页面
 */

public class FoldAct extends BaseAct {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fold_layout);
        CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.ct);
        collapsingToolbarLayout.setTitle("JAVA第一周");
        collapsingToolbarLayout.setContentScrimResource(R.mipmap.ic_launcher);
        Toolbar toolbar_detail =  findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar_detail);
        toolbar_detail.setNavigationIcon(R.mipmap.ic_menu);

//        initView();
    }

//    private void initView() {
//        listView = findViewById(R.id.listView);
//        MyAdapter myAdapter = new MyAdapter(this, R.layout.data_layout);
//        List<String> data = new ArrayList<>();
//        for (int i = 0; i <= 20; i++) {
//            data.add(String.valueOf(i));
//        }
//        myAdapter.setDatas(data);
//        listView.setAdapter(myAdapter);
//    }


//    private class MyAdapter extends AbsBaseAdapter<String> {
//
//        private MyAdapter(Context context, int resId) {
//            super(context, resId);
//        }
//
//        @Override
//        public void bindData(AbsBaseAdapter<String>.ViewHolder viewHolder, String data) {
//            viewHolder.setTextView(R.id.ttt, data);
//        }
//    }


}
