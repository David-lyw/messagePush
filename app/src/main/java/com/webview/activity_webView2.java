package com.webview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import engineering.R;

/**
 * Created by sd on 2015/11/16.
 */
public class activity_webView2 extends Activity implements OnClickListener {
    TextView tv_uriContent;
    String uri;
    String aid, type;
    WebView webView2;
    WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview2);

        initView();
        initData();

    }

    public void initView() {
        tv_uriContent = (TextView) findViewById(R.id.tv_uriContent);
        webView2 = (WebView) findViewById(R.id.webView2);
    }

    public void initData() {
        aid = "996";
        type = "11";
        uri = "http://172.19.2.16/sedinweb/html/contentapp.html?Aid=" + aid + "&type=" + type;
        tv_uriContent.setText(uri);


    }

    public void requestData(View view) {
        previewDocument(uri);
    }

    private void previewDocument(String url) {
        webView2.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

        webSettings = webView2.getSettings();
        webView2.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        // 扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // 设置出现缩放工具
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        // // 自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);

        webView2.setWebViewClient(wvc);
        webView2.loadUrl(url);
    }

    MyWebViewClient wvc = new MyWebViewClient();

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onClick(View view) {

    }
}
