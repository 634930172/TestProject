package com.john.testproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.john.testproject.R;
import com.john.testproject.widget.CarouselView;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/9 0009 17:41
 * <p/>
 * Description:
 */

public class CarouselAct extends BaseAct {

    private CarouselView carouselView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel_layout);
        carouselView=findViewById(R.id.carouse);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carouselView.starCarouse();
    }


    @Override
    protected void onPause() {
        super.onPause();
        carouselView.stopCarouse();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
