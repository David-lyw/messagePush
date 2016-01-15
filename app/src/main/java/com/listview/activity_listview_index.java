package com.listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import engineering.R;

/**
 * Created by sd on 2015/10/19.
 */
public class activity_listview_index extends Activity implements OnClickListener {
    Intent intent;
    Button btn_pinnedHeadListView;
    Button btn_pulltorefreshListView;
    Button btn_ListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_index);
        initView();
        initData();
        initListener();
    }

    public void initView() {
        btn_pinnedHeadListView= (Button) findViewById(R.id.btn_pinnedHeadListView);
        btn_pulltorefreshListView= (Button) findViewById(R.id.btn_pulltorefreshListView);
        btn_ListView= (Button) findViewById(R.id.btn_ListView);
    }

    public void initData() {
    }

    public void initListener() {
        btn_pinnedHeadListView.setOnClickListener(this);
        btn_pulltorefreshListView.setOnClickListener(this);
        btn_ListView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_pinnedHeadListView:
                intent=new Intent(this,activity_expandablelistview_pinnedHead.class);
                startActivity(intent);
                break;
            case R.id.btn_pulltorefreshListView:
                intent=new Intent(this,activity_pulltoRefreshListview.class);
                startActivity(intent);
                break;
            case R.id.btn_ListView:
                intent=new Intent(this,activity_sortlistview.class);
                startActivity(intent);
                break;
        }

    }
}
