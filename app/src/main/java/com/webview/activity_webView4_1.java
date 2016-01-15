package com.webview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.common.GsonUtils;
import com.common.HttpUtils;
import com.listview.PinnedHeaderExpandableAdapter;
import com.listview.PinnedHeaderExpandableListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.utils.L;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import engineering.R;

/**
 * Created by sd on 2015/11/25.
 */
public class activity_webView4_1 extends Activity implements OnClickListener {
    String Id, user;
    String uri;
    List<Process_bean> list;
    private PinnedHeaderExpandableListView explistview;
    private String[][] childrenData;// = new String[10][10];
    private String[] groupData;// = new String[10];
    private int expandFlag = -1;//控制列表的展开
    private PinnedHeaderExpandableAdapter adapter;
    View underline_time, underline_project, underline_department;
    RadioButton btn_time, btn_project, btn_department;

    Map<String, List> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview4_1);

        Id = getIntent().getStringExtra("Id");
        user = getIntent().getStringExtra("user");
        uri = "http://172.25.0.2:8080/sedin.process.get/tasklist/get.act?user=113004&procdefid=ProjectFundsProcess:13:102504";
        list = new ArrayList<Process_bean>();
        initView();

        HttpUtils.doGet(uri, res2);


        initData();
        initListener();
    }

    //异步网络请求
    JsonHttpResponseHandler res2 = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers,
                              JSONObject response) {
            super.onSuccess(statusCode, headers, response);

            try {
                if (statusCode == 200) {
                    JSONArray jsonArray = response.getJSONArray("tasklist");
                    if (jsonArray.length() > 0) {
                        list = GsonUtils.jsonToBeanList(jsonArray, Process_bean.class);
                    }


//                    result = classify_time(list);
//
//                    List<String> keyStr = new ArrayList<String>();
//                    //List<Process_bean> value = new ArrayList<Process_bean>();
//                    for (Map.Entry<String, List> entry : result.entrySet()) {
//                        keyStr.add(entry.getKey());
//                        //  value.addAll(entry.getValue());
//                        Log.i("lyw", entry.getKey());
//                    }
//
//
//                    groupData = new String[keyStr.size()];
//                    for (int i = 0; i < keyStr.size(); i++) {
//                        groupData[i] = keyStr.get(i).toString();
//                    }


                    result = classify_time(list);
                    List<String> keyStr = new ArrayList<>();
                    for (Map.Entry<String, List> entry : result.entrySet()) {
                        keyStr.add(entry.getKey());
                        L.i("size:=" + result.size() + "key=" + entry.getKey() + "  value:" + entry.getValue().toString());
                    }
                    groupData = keyStr.toArray(new String[result.size()]);


                    for (int i = 0; i < groupData.length; i++) {
                        List test = result.get(groupData[i]);
                        for (int j = 0; j < test.size(); j++) {
                            L.i(((Process_bean) test.get(j)).getCreateTime());
                        }
                    }

                    childrenData = new String[groupData.length][5];
                    for (int i = 0; i < groupData.length; i++) {
                        for (int j = 0; j < 5; j++) {
                            childrenData[i][j] = "hahahahahha";
                        }
                    }

                    initData();


                } else {
                    Toast.makeText(getApplicationContext(), "服务器未连接成功，请稍后再试或联系我们！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 初始化VIEW
     */
    private void initView() {
        explistview = (PinnedHeaderExpandableListView) findViewById(R.id.explistview);

        btn_time = (RadioButton) findViewById(R.id.btn_time);
        btn_project = (RadioButton) findViewById(R.id.btn_project);
        btn_department = (RadioButton) findViewById(R.id.btn_department);

        underline_time = findViewById(R.id.underline_time);
        underline_project = findViewById(R.id.underline_project);
        underline_department = findViewById(R.id.underline_department);

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


        //        groupData=new String[3];
//        groupData[0]="zu1";
//        groupData[1]="zu2";
//        groupData[2]="zu3";
//
//       List test1=new ArrayList();
//        test1.add("hahahha");
//
//        List test2=new ArrayList();
//        test2.add("hahahha");
//        test2.add("hahahha");
//        test2.add("hahahha");
//
//        List test3=new ArrayList();
//        test3.add("hahahha");
//        test3.add("hahahha");
//        test3.add("hahahha");
//
//        result.clear();
//        result.put(groupData[0], test1);
//        result.put(groupData[1],test2);
//        result.put(groupData[2],test3);
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

    public void initListener() {

    }

    class GroupClickListener implements ExpandableListView.OnGroupClickListener {
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


    public Map<String, List> classify_time(List<Process_bean> list) {
        Map<String, List> map = new HashMap<String, List>();

        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Process_bean process_bean = (Process_bean) it.next();
            if (map.containsKey(process_bean.getCreateTime().substring(0, 10))) {
                List beanlist = map.get(process_bean.getCreateTime().substring(0, 10));
                beanlist.add(process_bean);

            } else {
                List beanlist = new ArrayList();
                beanlist.add(process_bean);
                map.put(process_bean.getCreateTime().substring(0, 10), beanlist);
            }
        }

        return map;
    }

    public Map<String, List> classify_project(List<Process_bean> list) {
        Map<String, List> map = new HashMap<String, List>();

        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Process_bean process_bean = (Process_bean) it.next();
            if (map.containsKey(process_bean.getProjectname())) {
                List beanlist = map.get(process_bean.getProjectname());
                beanlist.add(process_bean);

            } else {
                List beanlist = new ArrayList();
                beanlist.add(process_bean);
                map.put(process_bean.getProjectname(), beanlist);
            }
        }

        return map;
    }

    public Map<String, List> classify_department(List<Process_bean> list) {
        Map<String, List> map = new HashMap<String, List>();

        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Process_bean process_bean = (Process_bean) it.next();
            if (map.containsKey(process_bean.getDepartment())) {
                List beanlist = map.get(process_bean.getDepartment());
                beanlist.add(process_bean);
            } else {
                List beanlist = new ArrayList();
                beanlist.add(process_bean);
                map.put(process_bean.getDepartment(), beanlist);
            }
        }

        return map;

    }


    @Override
    public void onClick(View view) {


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
