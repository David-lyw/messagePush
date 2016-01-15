package com.listview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import engineering.R;

/**
 * Created by sd on 2015/10/19.
 */

import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

import java.util.List;


public class activity_expandablelistview_pinnedHead extends Activity {
    private PinnedHeaderExpandableListView explistview;
    private String[][] childrenData = new String[10][10];
    private String[] groupData = new String[10];
    private int expandFlag = -1;//控制列表的展开
    private PinnedHeaderExpandableAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandablelistview);
        initView();
        initData();
    }

    /**
     * 初始化VIEW
     */
    private void initView() {
        explistview = (PinnedHeaderExpandableListView) findViewById(R.id.explistview);
    }

    /**
     * 初始化数据
     */
    private void initData() {
//        for (int i = 0; i < groupData.length; i++) {
//            groupData[i] = "分组" + i;
//        }
//
//        for (int i = 0; i < childrenData.length; i++) {
//            for (int j = 0; j < childrenData[i].length; j++) {
//                childrenData[i][j] = "好友" + i + "-" + j;
//            }
//        }
        //设置悬浮头部VIEW,注释本行代码就是一般的expandablelistview.
        explistview.setHeaderView(getLayoutInflater().inflate(R.layout.group_head,
                explistview, false));

        adapter = new PinnedHeaderExpandableAdapter(childrenData, groupData, getApplicationContext(), explistview);
        explistview.setAdapter(adapter);

        //循环实现expandablelistview的默认展开.
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            explistview.expandGroup(i);
        }

        //设置单个分组展开(为组设置点击事件)
        // explistview.setOnGroupClickListener(new GroupClickListener());

        //为组中的条目设置点击事件.
        explistview.setOnChildClickListener(new ChildClikListener());
    }

    class GroupClickListener implements OnGroupClickListener {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v,
                                    int groupPosition, long id) {
            if (expandFlag == -1) {
                // 展开被选的group
                explistview.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                explistview.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            } else if (expandFlag == groupPosition) {
                explistview.collapseGroup(expandFlag);
                expandFlag = -1;
            } else {
                explistview.collapseGroup(expandFlag);
                // 展开被选的group
                explistview.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                explistview.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            }
            return true;
        }
    }


    class ChildClikListener implements ExpandableListView.OnChildClickListener {
        @Override
        public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
            Log.i("lyw", "分组：" + groupPosition + "条目：" + childPosition);
            return false;
        }
    }
}

