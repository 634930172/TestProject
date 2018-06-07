package com.john.testproject.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.john.testproject.R;
import com.john.testproject.utils.L;
import com.john.testproject.utils.T;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/9 0009 15:16
 * <p/>
 * Description:轮播图
 */

public class CarouselView extends FrameLayout implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private List<View> guideViews;//整个view视图
    private Context context;
    private int[] drawables;
    private LinearLayout linearLayout;
    private List<ImageView> points;
    private int currentPosition = 0;
    public static final String TAG = "GuideView";
    private CarouselHandler carouselHandler;
    private int realViewConts;
    private int selectIndex;
    private static final int STAR = 0x01;//开始
    private static final int CAROUSEL = 0x02;//轮播


    public CarouselView(@NonNull Context context) {
        this(context, null);
    }

    public CarouselView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarouselView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        linearLayout = findViewById(R.id.linerLayout);
        points = new ArrayList<>();
        drawables = new int[]{R.mipmap.guide_01, R.mipmap.guide_02, R.mipmap.guide_03};
        initImageView();
        initPoint();
    }

    private void initData() {
        GuideViewAdapter adapter = new GuideViewAdapter();
        viewPager.setAdapter(adapter);
    }

    //添加下标的点
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

    //添加要放入循环的图片
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
        realViewConts = guideViews.size();
    }

    //初始化事件
    private void initEvent() {
        viewPager.addOnPageChangeListener(this);
        carouselHandler = new CarouselHandler(this);
        carouselHandler.sendEmptyMessage(STAR);
    }

    //-----------------------pagerListener监听--------------------------------------------
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        for (ImageView imageView : points) {
            imageView.setImageResource(R.mipmap.guide_point);
        }
        selectIndex = position % drawables.length;
        Log.e(TAG, "onPageSelected: " + position + " realViewConts: " + drawables.length + "  index: " + selectIndex);
        points.get(selectIndex).setImageResource(R.mipmap.guide_point_cur);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    //---------------------------PagerAdapter----------------------------------------

    private class GuideViewAdapter extends PagerAdapter implements OnTouchListener {
        long    downTime=0;
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //            int selectPointIndex = position % realViewConts;
            //            container.removeView(guideViews.get(selectPointIndex));//guideView引导图实际的数量  List<View> guideViews
            //            container.removeView((View) object);//删除页卡
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {  //这个方法用来实例化页卡
            int selectPointIndex = position % realViewConts;
            View view = guideViews.get(selectPointIndex);
            //判断其父容器是否存在，如存在，先和此子控件解除关系
            ViewPager parent = (ViewPager) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            container.addView(view);
            view.setOnTouchListener(this);
            return view;
            //            ImageView view = new ImageView(context);
            //            view.setScaleType(ImageView.ScaleType.FIT_XY);
            //            view.setImageResource(drawables[position % drawables.length]);
            //            view.setOnTouchListener(this);
            //            container.addView(view);
            //            return view;


            //            int selectPointIndex = position % realViewConts;
            //            container.addView(guideViews.get(selectPointIndex));//添加页卡  List<View> guideViews
            //            return guideViews.get(selectPointIndex);
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downTime=System.currentTimeMillis();
                    L.e("取消循环");
                    stopCarouse();
                    break;
                case MotionEvent.ACTION_UP:
                    long  upTime=System.currentTimeMillis();
                    if(upTime-downTime<1000){//充当点击事件
                        T.s("index: "+selectIndex);
                    }
                    L.e("开始循环");
                    starCarouse();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    L.e("开始循环");
                    starCarouse();
                    break;
            }
            return true;
        }
    }
    //-----------------------------------CarouselHandler----------------------------------------------------

    //防止内存泄露的handler
    private static class CarouselHandler extends Handler {
        WeakReference<CarouselView> carouselView;

        CarouselHandler(CarouselView carouselView) {
            this.carouselView = new WeakReference<>(carouselView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CarouselView carouselView = this.carouselView.get();
            switch (msg.what) {
                case STAR:
                    L.e(">>>>STAR>>>>>>>");
                    sendEmptyMessageDelayed(CAROUSEL, 7000);
                    break;
                case CAROUSEL:
                    if (carouselView != null) {
                        L.e(">>>>carouselView.get().currentPosition>>>>>>>" + carouselView.currentPosition);
                        carouselView.viewPager.setCurrentItem(carouselView.currentPosition + 1, true);
                        sendEmptyMessage(STAR);
                    } else {
                        L.e(">>>>carouselView====================null>>>>>>>>>>>>>>>>>>>");
                    }
                    break;
            }
        }
    }


    //启动轮播
    public void starCarouse() {
        if (carouselHandler != null && !carouselHandler.hasMessages(STAR)) {
            carouselHandler.sendEmptyMessage(STAR);
        }
    }

    //停止轮播
    public void stopCarouse() {
        if (carouselHandler != null) {
            carouselHandler.removeCallbacksAndMessages(null);
        }
    }


}
