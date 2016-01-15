package com.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;

import com.common.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.utils.L;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import engineering.R;

/**
 * Created by sd on 2015/11/23.
 */
public class activity_webView4 extends Activity {
    String uri;
    ArrayList<JSONObject> list;
    GridView gridView;
    Intent intent;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview4);
        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<JSONObject>();
        uri = "http://172.25.0.2:8080/sedin.process.get/process/processlist.act";
        user="113004";
        HttpUtils.doGet(uri, res2);

    }

    public void setItemListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                intent=new Intent(activity_webView4.this,activity_webView4_1.class);

                intent.putExtra("user",user);
                try {
                    intent.putExtra("Id",list.get(position).getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startActivity(intent);
            }
        });
    }

    //异步网络请求
    JsonHttpResponseHandler res2 = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers,
                              JSONArray response) {
            super.onSuccess(statusCode, headers, response);

            try {
                if (statusCode == 200) {
                    for (int i = 0; i < response.length(); i++) {
                        list.add(response.getJSONObject(i));
                        L.i(list.get(i).getString("name"));
                    }
                    MyAdapter myAdapter=new MyAdapter(activity_webView4.this);
                    gridView.setAdapter(myAdapter);
                    setItemListener();
                } else {
                    Toast.makeText(getApplicationContext(), "服务器未连接成功，请稍后再试或联系我们！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    public class MyAdapter extends BaseAdapter {
        LayoutInflater layoutInflater;
        TextView tv_title;


        public MyAdapter(Context c) {
            layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = layoutInflater.inflate(R.layout.activity_webview_item, null);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            try {
                tv_title.setText(list.get(i).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //此方法行不通？？不解
            //view.setBackground(getDrawable(R.drawable.activity_webview4_item));

            //view.setBackgroundColor(getResources().getColor(R.color.process_module));

            Random random = new Random();
            for (int k = 0; k < list.size(); k++) {
                int j = random.nextInt(5);//0-4的整数
                switch (j) {
                    case 0:
                        view.setBackgroundResource(R.drawable.activity_webview4_item);
                        break;
                    case 1:
                        view.setBackgroundResource(R.drawable.activity_webview4_item1);
                        break;
                    case 2:
                        view.setBackgroundResource(R.drawable.activity_webview4_item2);
                        break;
                    case 3:
                        view.setBackgroundResource(R.drawable.activity_webview4_item3);
                        break;
                    case 4:
                        view.setBackgroundResource(R.drawable.activity_webview4_item4);
                        break;
                    default:
                        view.setBackgroundResource(R.drawable.activity_webview4_item);
                        break;

                }
            }

            return view;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }
    }

}
