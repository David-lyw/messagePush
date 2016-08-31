package com.webview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.view.View.OnClickListener;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import engineering.R;

/**
 * Created by sd on 2015/11/16.
 */
public class activity_webView1 extends Activity implements OnClickListener {
    WebView webView1;
    Button btn_submit;
    EditText editText;
    String uri1 = "http://";
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview1);
        initView();
        initListener();
    }

    public void initView() {
        webView1 = (WebView) findViewById(R.id.webView1);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        editText = (EditText) findViewById(R.id.edittext);
        relativeLayout = (RelativeLayout) findViewById(R.id.linearlayou);
    }

    public void initListener() {
        btn_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                String url = "http://119.75.217.109";
                if (url != null) {
                    webView1.loadUrl(url);
                    webView1.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            view.loadUrl(url);
                            return true;
                        }
                    });
                }
                relativeLayout.setVisibility(View.GONE);
                break;
        }

    }
}
