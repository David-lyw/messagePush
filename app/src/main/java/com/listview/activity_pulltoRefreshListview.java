package com.listview;


import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;


import engineering.R;

public class activity_pulltoRefreshListview extends Activity {

    private ArrayList<String> datas;
    private int i;
    private int j;
    private ArrayAdapter<String> adapter;
    private PullToRefreshListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltorefershlistview);
        listView = (PullToRefreshListView) findViewById(R.id.list_view);
        datas = new ArrayList<String>();
        // 模拟数据
        for (int i = 0; i < 15; i++) {
            datas.add("明天我会拥有" + (i * 10 + 1) + "千万!");
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(adapter);

        listView.setOnRefreshingListener(new PullToRefreshListView.OnRefreshingListener() {

            @Override
            public void onRefreshing() {
                // 模拟获取数据的操作
                refreshing();
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
    }

    protected void refreshing() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                datas.add(0, "我是刷新出来的数据" + ++i);
                adapter.notifyDataSetChanged();    //  通知Adapter数据发生改变了
                listView.onRefreshComplete();

            }
        }, 3000);
    }

    protected void loadMore() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                datas.add("我是加载更多出来的数据" + ++j);
                adapter.notifyDataSetChanged();    //  通知Adapter数据发生改变了
                listView.onLoadMoreComplete();

            }
        }, 3000);
    }

}
