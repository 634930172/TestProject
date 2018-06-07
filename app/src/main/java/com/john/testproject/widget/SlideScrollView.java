package com.john.testproject.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.john.testproject.utils.L;
import com.john.testproject.utils.ScreenUtils;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/1/23 15:51
 * Description:
 */

public class SlideScrollView extends HorizontalScrollView {


    private int mScrrenWidth;//屏幕的跨度
    private LinearLayout parent;//父部控件
    private ViewGroup mMenu,mContent;//菜单布局，主布局
    private int menuWidth,mPaddingRigth;//菜单宽度，右部边距
    private boolean isFist=true;
    public SlideScrollView(Context context) {
        this(context, null);
    }

    public SlideScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScrrenWidth = ScreenUtils.deviceWidth(context);//屏幕的宽度

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        L.e("onLayout>>>>>>changed: "+changed);
        if(changed){
            scrollTo(menuWidth,0);
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        L.e("onMeasure>>>>>>");
        if(isFist){
            parent= (LinearLayout) getChildAt(0);
            mMenu= (ViewGroup) parent.getChildAt(0);
            mContent= (ViewGroup) parent.getChildAt(1);
            menuWidth=mMenu.getLayoutParams().width=mScrrenWidth-100;//菜单的宽度
            mContent.getLayoutParams().width=mScrrenWidth;
            isFist=false;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
            int x=  getScrollX();
                if(x>menuWidth/2){
                    smoothScrollTo(menuWidth,0);
                }else {
                    smoothScrollTo(0,0);
                }
                return true;

                case MotionEvent.ACTION_MOVE:
                    L.e("move:"+getScrollX());
                    break;
        }
        return super.onTouchEvent(ev);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        L.e("onScrollChanged: l: "+l+"|  t: "+t+" | oldl: "+oldl+" | oldt "+oldt);
    }
}
