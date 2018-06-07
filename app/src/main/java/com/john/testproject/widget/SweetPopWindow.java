package com.john.testproject.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.john.testproject.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/11 0011 15:45
 * <p/>
 * Description:
 */

public class SweetPopWindow extends PopupWindow implements PopupWindow.OnDismissListener {

    private Button button;
    private EditText editText;
    private Activity activity;


    public SweetPopWindow(Activity activity, View.OnClickListener onClickListener) {
        super(activity);
        this.activity=activity;
        View rootView = LayoutInflater.from(activity).inflate(R.layout.common_pop_layout, null);
        setContentView(rootView);//设置pop视图
        initView(rootView);
        button.setOnClickListener(onClickListener);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);//设置pop宽
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);//设置pop高
        setFocusable(true);//是否获取焦点
        setBackgroundDrawable(new ColorDrawable(0x80000000));//设置透明的窗体，点击pop外面可以退出
        setAnimationStyle(R.style.PopupAnimation);
        setOnDismissListener(this);
    }

    private void initView(View rootView) {
        button = rootView.findViewById(R.id.pop_button);
        editText = rootView.findViewById(R.id.edit_text);
        editText.setFocusable(true);
        editText.requestFocus();
        editText.setFocusableInTouchMode(true);
    }

    public  void showKeyboard() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
                       {
                           public void run()
                           {
                               InputMethodManager inputManager =
                                       (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(editText, 0);
                           }
                       },
                300);
    }


    @Override
    public void onDismiss() {
        setWindowAlpha(1.0f);
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            setWindowAlpha(0.5f);
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
}
