package com.webview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.view.View.OnClickListener;

import engineering.R;

/**
 * Created by sd on 2015/11/16.
 */
public class activity_webView extends Activity implements OnClickListener {

    Intent intent;
    Button btn_webViewDetail;
    Button btn_webViewDetail1;
    Button btn_webViewDetail2;
    Button btn_webViewDetail4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_index);
        initView();
        initData();
        initListener();
    }

    public void initView() {
        btn_webViewDetail = (Button) findViewById(R.id.btn_webViewDetail);
        btn_webViewDetail1 = (Button) findViewById(R.id.btn_webViewDetail1);
        btn_webViewDetail2 = (Button) findViewById(R.id.btn_webViewDetail2);
        btn_webViewDetail4= (Button) findViewById(R.id.btn_webViewDetail4);
    }

    public void initData() {

    }

    public void initListener() {
        btn_webViewDetail.setOnClickListener(this);
        btn_webViewDetail1.setOnClickListener(this);
        btn_webViewDetail2.setOnClickListener(this);
        btn_webViewDetail4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_webViewDetail:
                intent = new Intent(this, activity_webView1.class);
                startActivity(intent);
                break;
            case R.id.btn_webViewDetail1:
                intent = new Intent(this, activity_webView2.class);
                startActivity(intent);
                break;
            case R.id.btn_webViewDetail2:
                intent = new Intent(this, activity_webView3.class);

                startActivity(intent);
                break;
            case R.id.btn_webViewDetail4:
                intent=new Intent(this,activity_webView4.class);
                startActivity(intent);
                break;
            default:

                break;
        }

    }
}
