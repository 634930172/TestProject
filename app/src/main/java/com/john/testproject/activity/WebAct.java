package com.john.testproject.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.john.testproject.R;
import com.john.testproject.utils.L;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2017/12/25 0025 09:58
 * <p/>
 * Description:
 */

public class WebAct extends BaseAct {

    private static final String WEB_RUL = "https://www.baidu.com";
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);
        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        initWebView();
    }

    private void initWebView() {
        webView.loadUrl(WEB_RUL);
        WebSettings setting = webView.getSettings();
        // 支持缩放
        setting.setSupportZoom(false);
        // 设置支持缩放 + -
        // setting.setBuiltInZoomControls(false);
        // 关闭 webView 中缓存
        /**/
        /*webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);*/
        setting.setUseWideViewPort(false);
        setting.setLoadWithOverviewMode(true);
        // 设置WebView属性，能够执行Javascript脚本
        setting.setJavaScriptEnabled(true);
        setting.setSavePassword(false);
        setting.setDomStorageEnabled(true);
        setting.setDefaultTextEncodingName("utf-8");

        //这方法可以让你的页面适应手机屏幕的分辨率，完整的显示在屏幕上，可以放大缩小。
        setting.setUseWideViewPort(true);//设置webview推荐使用的窗口，设置为true
        setting.setLoadWithOverviewMode(true);//设置webview加载的页面的模式，也设置为true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setHorizontalScrollBarEnabled(false);//隐藏横向的scrollbar
        webView.setVerticalScrollBarEnabled(false);//隐藏纵向的scrollbar
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//去掉右边的边框
    }

    private class MyWebViewClient extends WebViewClient {
        public void onReceivedSslError(WebView view,
                                       SslErrorHandler handler, SslError error) {
            // 　　//handler.cancel(); 默认的处理方式，WebView变成空白页
            handler.proceed(); // 接受证书
            // handleMessage(Message msg); 其他处理
            super.onReceivedSslError(view, handler, error);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("baidu")) {
                return true;
            }
            //处理http和https开头的url
            view.loadUrl(url);//注销掉就不会有了
            return true;//使用拦截处理后的url打开 false使用内部webview机制打开  以上都在同一webview运行-- super使用默认的系统浏览器打开
        }

        // 开始加载网页时要做的工作
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            System.out.println("url = " + url);
        }

        // 加载完成时要做的工作
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (progressBar != null) {
                if (newProgress == progressBar.getMax()) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
                progressBar.setProgress(newProgress);
            }

        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            L.d("title：" + title);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(webView!=null){
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.stopLoading();//停止加载
            webView.clearHistory();
            webView.getSettings().setJavaScriptEnabled(false);
            webView.removeAllViews();
            webView=null;
        }
    }
}
