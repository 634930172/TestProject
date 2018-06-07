package com.john.testproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.john.testproject.R;
import com.john.testproject.utils.L;
import com.john.testproject.utils.MakeXml;

import java.math.BigDecimal;

/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/11/10 0010
 * <p/>
 * Description:
 */

public class ScreenFitAct extends BaseAct {

    private static final String TAG = "ScreenFitAct";
    private Button bb;
    private TextView tt,ttt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_layout);
        initScreen();
        initData();
    }

    private void initData() {
        bb = findViewById(R.id.bb);
        tt = findViewById(R.id.tt);
        ttt = findViewById(R.id.ttt);
        ViewTreeObserver vtb1 = bb.getViewTreeObserver();
        vtb1.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tt.setText("实际测量宽高：width:"
                        + bb.getWidth() + "px---->height:"
                        + bb.getHeight()+"px");
            }
        });

        ttt.setText(getScreenParams());
    }

    private void initScreen() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        Log.e(TAG, "width:" + width + "---height:" + height + "---density:" + density + "---densityDpi:" + densityDpi);
    }


    public void all(View view) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 1; i < 350; i++) {
            buffer.append("<dimen name=\"dimen" + i + "\">" + getInt(i * 1.0556) + "dp" + "</dimen>");
            buffer.append("\n");
            //  writeTxtToFile("<dimen name=\"dimen"+i+"\">"+getInt(i*1.25)+"dp"+"</dimen>",filePath,fileName);
        }
        showLogCompletion(buffer.toString(), 4000);
    }

    public void makeXml(View view) {
        MakeXml.makeXml();
    }

    /**
     * 分段打印出较长log文本
     *
     * @param log       原log文本
     * @param showCount 规定每段显示的长度（最好不要超过eclipse限制长度）
     */

    public static void showLogCompletion(String log, int showCount) {
        if (log.length() > showCount) {
            String show = log.substring(0, showCount);
            //          System.out.println(show);
            Log.i("TAG", show + "");
            if ((log.length() - showCount) > showCount) {//剩下的文本还是大于规定长度
                String partLog = log.substring(showCount, log.length());
                showLogCompletion(partLog, showCount);
            } else {
                String surplusLog = log.substring(showCount, log.length());
                //              System.out.println(surplusLog);
                Log.i("TAG", surplusLog + "");
            }

        } else {
            //          System.out.println(log);
            Log.i("TAG", log + "");
        }
    }

    public BigDecimal getInt(Double i) {
        return new BigDecimal(i).setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    public String getScreenParams() {
        DisplayMetrics dm = new DisplayMetrics();
        //        dm = getResources().getDisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int heightPixels = dm.heightPixels;
        int widthPixels = dm.widthPixels;
        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;
        int densityDpi = dm.densityDpi;
        float density = dm.density;
        float scaledDensity = dm.scaledDensity;
        float heightDP = heightPixels / density;
        float widthDP = widthPixels / density;
        String str = "heightPixels: " + heightPixels + "px";
        str += "\nwidthPixels: " + widthPixels + "px";
        str += "\nxdpi: " + xdpi + "dpi";
        str += "\nydpi: " + ydpi + "dpi";
        str += "\ndensityDpi: " + densityDpi + "dpi";
        str += "\ndensity: " + density;
        str += "\nscaledDensity: " + scaledDensity;
        str += "\nheightDP: " + heightDP + "dp";
        str += "\nwidthDP: " + widthDP + "dp";
        return str;
    }
}
