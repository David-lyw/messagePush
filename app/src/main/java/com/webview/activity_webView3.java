package com.webview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.common.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.utils.L;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import engineering.R;

/**
 * Created by sd on 2015/11/16.
 */
public class activity_webView3 extends Activity implements android.view.View.OnClickListener {
    TextView tv_uriContent, tv_uriContent2;
    Button btn_request;
    String uri, uri2;
    Button btn;
    ArrayList<JSONObject> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview3);
        tv_uriContent = (TextView) findViewById(R.id.tv_uriContent);
        tv_uriContent2 = (TextView) findViewById(R.id.tv_uriContent2);

        btn_request = (Button) findViewById(R.id.btn_request);
        btn = (Button) findViewById(R.id.btn_request1);
        btn.setOnClickListener(this);
        uri = "http://172.19.2.16/sedinweb/html/ajax/Gzcx.ashx?name=110034&psd=dd&rq=201503";

        uri2 = "http://172.25.0.2:8080/sedin.process.get/process/processlist.act";

        tv_uriContent.setText(uri);
        tv_uriContent2.setText(uri2);

        list = new ArrayList<JSONObject>();


    }

    //布局中绑定的方法----数据请求
    public void requestData(View v) {
        HttpUtils.doGet(uri, res);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request1:
                requestData2();
                break;
            default:
                break;
        }
    }

    //布局中绑定的方法----数据请求
    public void requestData2() {
        HttpUtils.doGet(uri2, res2);
    }

    JsonHttpResponseHandler res = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers,
                              JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {
                if (statusCode == 200) {
                    L.i("数据请求成功");
                    int total = response.getInt("total");
                    JSONArray arr = response.getJSONArray("rows");
                    if (total > 0) {

                        String str = arr.get(0).toString().replace("{", "").replace("}", "");
                        tv_uriContent.setText(str);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "服务器未连接成功，请稍后再试或联系我们！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    //异步网络请求
    JsonHttpResponseHandler res2 = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers,
                              JSONArray responseString) {
            super.onSuccess(statusCode, headers, responseString);

            try {
                if (statusCode == 200) {
                    for (int i = 0; i < responseString.length(); i++) {
                        list.add(responseString.getJSONObject(i));
                    }

                    tv_uriContent2.setText(list.toString());
                } else {
                    Toast.makeText(getApplicationContext(), "服务器未连接成功，请稍后再试或联系我们！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
