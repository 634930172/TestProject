package com.john.testproject.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.john.testproject.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/9 0009 15:16
 * <p/>
 * Description:启动引导页
 */

public class GuideView extends FrameLayout implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private List<View> guideViews;//整个view视图
    private Context context;
    private int[] drawables;
    private LinearLayout linearLayout;
    private List<ImageView> points;

    public static final String TAG = "GuideView";

    public GuideView(@NonNull Context context) {
        this(context, null);
    }

    public GuideView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.guideview_layout, this, true);
        //        pics = context.getResources().obtainTypedArray(R.array.guideImages);
        initView();
        initData();
        initEvent();
    }


    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        guideViews = new ArrayList<>();
        linearLayout=findViewById(R.id.linerLayout);
        points=new ArrayList<>();
        drawables = new int[]{R.mipmap.guide_01, R.mipmap.guide_02, R.mipmap.guide_03};
        initImageView();
        initPoint();
    }

    private void initData() {
        GuideViewAdapter adapter = new GuideViewAdapter();
        viewPager.setAdapter(adapter);
    }

    private void initPoint() {

        ImageView imageView;
        for (int i = 0; i < drawables.length; i++) {
            imageView = new ImageView(context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setPadding(10, 10, 10, 10);
            imageView.setLayoutParams(layoutParams);
            if (i == 0) {
                imageView.setImageResource(R.mipmap.guide_point_cur);//选中图标
            } else {
                imageView.setImageResource(R.mipmap.guide_point);//未选中图标
            }
            linearLayout.addView(imageView);
            points.add(imageView);
        }

    }

    private void initImageView() {
        // 定义一个布局并设置参数
        ViewGroup.LayoutParams mParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        // 初始化引导图片列表
        for (int drawable : drawables) {
            ImageView iv = new ImageView(context);
            iv.setLayoutParams(mParams);
            // 防止图片不能填满屏幕
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            // 加载图片资源
            iv.setImageResource(drawable);
            guideViews.add(iv);
        }
    }

    private void initEvent() {
        viewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.e(TAG, "onPageScrolled: " + position + "  positionOffset:" + positionOffset + "  positionOffsetPixels" + positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        Log.e(TAG, "onPageSelected: " + position);
        for(ImageView imageView:points){
            imageView.setImageResource(R.mipmap.guide_point);
        }
        points.get(position).setImageResource(R.mipmap.guide_point_cur);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public class GuideViewAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return guideViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(guideViews.get(position));//删除页卡
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {  //这个方法用来实例化页卡
            container.addView(guideViews.get(position));//添加页卡
            return guideViews.get(position);
        }

    }

}
