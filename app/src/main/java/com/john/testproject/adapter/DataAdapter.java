package com.john.testproject.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/2/6 17:08
 * Description:一般BaseAdapter封装
 */

public  abstract class DataAdapter<T> extends BaseAdapter {

    public List<T> data=new ArrayList<>();

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 设置数据源
     * @param data
     */
    public void setData(List<T> data){
        this.data.clear();
        this.data=data;
        notifyDataSetChanged();
    }

}
