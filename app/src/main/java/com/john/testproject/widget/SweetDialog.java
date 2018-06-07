package com.john.testproject.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.john.testproject.R;
import com.john.testproject.utils.ScreenUtils;
import com.john.testproject.utils.TextUtil;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/11 0011 9:45
 * <p/>
 * Description:通用dialog
 */

public class SweetDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private TextView sweet_content;
    private Button cancel_button, confirm_button;
    private SweetOnClickListener sweetOnClickListener;
    private SweetOnToastClickListener sweetOnToastClickListener;
    private String sweetContent;
    private boolean cancelable;


    /**
     * 选择式弹窗1
     */
    public SweetDialog(@NonNull Context context, String sweetContent, SweetOnClickListener sweetOnClickListener) {
        super(context, R.style.sweet_dialog_style);
        this.context = context;
        this.sweetOnClickListener = sweetOnClickListener;
        this.sweetContent = sweetContent;
    }

    /**
     * 选择式弹窗2
     */
    public SweetDialog(@NonNull Context context, String sweetContent, boolean cancelable, SweetOnClickListener sweetOnClickListener) {
        super(context, R.style.sweet_dialog_style);
        this.context = context;
        this.sweetOnClickListener = sweetOnClickListener;
        this.sweetContent = sweetContent;
        this.cancelable = cancelable;
    }

    /**
     * Toast样式弹窗 1
     */
    public SweetDialog(@NonNull Context context, String sweetContent, SweetOnToastClickListener sweetOnToastClickListener) {
        super(context, R.style.sweet_dialog_style);
        this.context = context;
        this.sweetOnToastClickListener = sweetOnToastClickListener;
        this.sweetContent = sweetContent;
    }

    /**
     * Toast样式弹窗 2
     */
    public SweetDialog(@NonNull Context context, String sweetContent, boolean cancelable, SweetOnToastClickListener sweetOnToastClickListener) {
        super(context, R.style.sweet_dialog_style);
        this.context = context;
        this.sweetOnToastClickListener = sweetOnToastClickListener;
        this.sweetContent = sweetContent;
        this.cancelable = cancelable;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sweetdialog_layout);
        initView();
        initData();
        initEvent();
    }

    private void initData() {
        if (!TextUtil.isEmpty(sweetContent)) {
            sweet_content.setText(sweetContent);
        }
    }

    private void initEvent() {
        cancel_button.setOnClickListener(this);
        confirm_button.setOnClickListener(this);
        setCanceledOnTouchOutside(false);
        setCancelable(cancelable);
    }

    private void initView() {
        sweet_content = findViewById(R.id.sweet_content);
        cancel_button = findViewById(R.id.cancel_button);
        confirm_button = findViewById(R.id.confirm_button);
        View view = findViewById(R.id.button_view);
        if (sweetOnToastClickListener != null) {
            view.setVisibility(View.GONE);
            cancel_button.setVisibility(View.GONE);
        }
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = (int) (ScreenUtils.deviceWidth(context) * 0.8);
            getWindow().setAttributes(lp);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_button:
                if (sweetOnClickListener != null) {
                    sweetOnClickListener.onCancelClick(this);
                }
                break;
            case R.id.confirm_button:
                if (sweetOnClickListener != null) {
                    sweetOnClickListener.onConfirmClick(this);
                }
                if (sweetOnToastClickListener != null) {
                    sweetOnToastClickListener.onConfirmClick(this);
                }
                break;
        }
    }

    public interface SweetOnClickListener {
        void onCancelClick(SweetDialog sweetDialog);

        void onConfirmClick(SweetDialog sweetDialog);
    }

    public interface SweetOnToastClickListener {
        void onConfirmClick(SweetDialog sweetDialog);
    }

}
