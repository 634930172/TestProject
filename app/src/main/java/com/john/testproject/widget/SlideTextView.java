package com.john.testproject.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.john.testproject.R;
import com.john.testproject.utils.L;


/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/1/25 11:07
 * Description:侧边字母栏，如果要与List联动，需要在回调的字母中先遍历其list，
 * 找到与之对应一样字母下标的index，然后再调用listView.setSelection（index）方法移动到特定的目录
 */

public class SlideTextView extends View {

    //需要绘制的内容
    private String[] strs = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private Paint mPaint;
    private int mHeight;
    private int textIndex=-1;

    public SlideTextView(Context context) {
        this(context, null);
    }

    public SlideTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setTextSize(40);
        mPaint.setColor(getContext().getResources().getColor(R.color.divider_color));
        setPadding(0,100,0,100);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        heightSize-=(getPaddingBottom()+getPaddingTop());
        mHeight = heightSize / strs.length;
        int viewWidth = 0;
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                viewWidth = widthSize;
                break;
            case MeasureSpec.AT_MOST:
                for (String str : strs) {
                    L.e("width"+mPaint.measureText(str));
                    if (viewWidth < mPaint.measureText(str)) {
                        viewWidth = (int) mPaint.measureText(str);
                    }
                }
                break;
        }
        L.e("viewWidth: " + viewWidth + " heightSize: " + heightSize);
        setMeasuredDimension(viewWidth, heightSize);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                 textIndex = (int) event.getY() / mHeight - 1;
                if (textIndex < 0) {
                    textIndex = 0;
                }
                if(textIndex>=strs.length){
                    textIndex=strs.length-1;
                }
                L.e("字母： "+strs[textIndex]);
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                textIndex=-1;
                postInvalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width ;
        for (int i = 0; i < strs.length; i++) {
            if(i==textIndex){
                mPaint.setColor(getContext().getResources().getColor(R.color.red_normal));
            }else {
                mPaint.setColor(getContext().getResources().getColor(R.color.divider_color));
            }
            width = (int) mPaint.measureText(strs[i]);
            canvas.drawText(strs[i], getWidth() / 2 - width / 2, mHeight * (1 + i), mPaint);//保证竖直字体在中间
        }
    }


}
