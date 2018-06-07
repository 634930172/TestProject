package com.john.testproject.photo;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.john.testproject.R;
import com.john.testproject.widget.NoDoubleClickButton;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/2/23 9:16
 * Description:选择的PopupWindow
 */

public class PickPopupWindow extends PopupWindow implements PopupWindow.OnDismissListener, View.OnClickListener {

    private NoDoubleClickButton firstBtn;
    private NoDoubleClickButton secondBtn;
    private Activity activity;
    private PickBtnListener pickBtnListener;

    public PickPopupWindow(Activity activity, PickBtnListener pickBtnListener) {
        super(activity);
        this.activity = activity;
        this.pickBtnListener=pickBtnListener;
        View rootView = LayoutInflater.from(activity).inflate(R.layout.pick_popup_window, null);
        setContentView(rootView);//设置pop视图
        initView(rootView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);//设置pop宽
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);//设置pop高
        setFocusable(true);//是否获取焦点
        setBackgroundDrawable(new ColorDrawable(0x80000000));//设置透明的窗体，点击pop外面可以退出
        setAnimationStyle(R.style.PopupAnimation);
        setOnDismissListener(this);
    }

    private void initView(View rootView) {
        firstBtn = rootView.findViewById(R.id.firstBtn);
        secondBtn = rootView.findViewById(R.id.secondBtn);
        NoDoubleClickButton cancelBtn = rootView.findViewById(R.id.cancelBtn);
        firstBtn.setOnClickListener(this);
        secondBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onDismiss() {
        setWindowAlpha(1.0f);
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            setWindowAlpha(0.7f);
            this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        } else {
            this.dismiss();
        }
    }

    private void setWindowAlpha(float alpha) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = alpha;
        activity.getWindow().setAttributes(params);
    }

    public PickPopupWindow setFirstText(String firstStr) {
        if (firstBtn != null && firstStr != null) {
            firstBtn.setText(firstStr);
        }
        return this;
    }

    public PickPopupWindow setSecondText(String secondStr) {
        if (secondBtn != null && secondStr != null) {
            secondBtn.setText(secondStr);
        }
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.firstBtn:
            case R.id.secondBtn:
                pickBtnListener.onBtnClick(v);
                break;
        }
        dismiss();
    }

    public interface PickBtnListener {
        void onBtnClick(View view);
    }


}
