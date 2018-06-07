package com.john.testproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 适配器的封装
 * Created by lili on 2017/3/3.
 */
public abstract class AbsBaseAdapter<T> extends BaseAdapter {

    protected Context context;
    public List<T> datas = new ArrayList<>();
    private int resId;//布局id

    public AbsBaseAdapter(Context context, int resId) {
        this.context = context;
        this.resId = resId;
    }

    /**
     * 设置数据源
     */
    public void setDatas(List<T> datas) {
        this.datas.clear();
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    /**
     * 追加数据
     *
     * @param datas
     */
    public void addDatas(List<T> datas) {
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }

    /**
     * 删除数据源
     *
     * @param position
     */
    public void deleteDatas(int position) {
        this.datas.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(resId, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        //为控件设置值
        bindData(viewHolder, datas.get(position));
        return convertView;
    }


    /**
     * 绑定数据
     */
    public abstract void bindData(ViewHolder viewHolder, T data);

    public class ViewHolder {
        private View layoutView;
        @SuppressLint("UseSparseArrays")
        private Map<Integer, View> map = new HashMap<>();

        public ViewHolder(View layoutView) {
            this.layoutView = layoutView;
        }

        public View getView(int id) {
            View view = null;
            if (map.containsKey(id)) {
                view = map.get(id);
            } else {
                view = layoutView.findViewById(id);
                map.put(id, view);
            }
            return view;
        }

        /**
         * 设置TextView
         *
         * @param id
         */
        public ViewHolder setTextView(int id, String value) {
            TextView tv = (TextView) this.getView(id);
            tv.setText(value);
            return this;
        }
    }
}
