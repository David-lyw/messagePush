package com.fragmenttabhost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

import engineering.R;


public class activity_fragmenttabhost extends FragmentActivity {
    private FragmentTabHost tabHost;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmenttabhost);
        initTabHost();
        initRadioGroup();

        //设置默认显示指定的fragment，
        //tabHost.setCurrentTab(3);

        //此时布局方面做配合。(只这一种方法就可以实现)
        radioGroup.check(R.id.rbtn_search);

    }

    private void initRadioGroup() {
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_mainpage://主页
                        tabHost.setCurrentTab(0);
                        break;
                    case R.id.rbtn_about://关于
                        tabHost.setCurrentTab(1);
                        break;
                    case R.id.rbtn_setting://设置
                        tabHost.setCurrentTab(2);
                        break;
                    case R.id.rbtn_search://搜索
                        tabHost.setCurrentTab(3);
                        break;
                    case R.id.rbtn_more://更多
                        Toast.makeText(activity_fragmenttabhost.this, "有待开发!",
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    private void initTabHost() {
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        // 必须先调用FragmentTabHost的setup()方法
        tabHost.setup(activity_fragmenttabhost.this, getSupportFragmentManager(),
                R.id.realTabcontent);


        // 为FragmentTabHost添加标签页
        // 第一个标签页
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("标签1"), // 设置该标签页标签部分所显示的文本和图标
                Fragment1.class, // 设置该标签页标签内容所来自的Fragment类
                null // 如果该标签页的Fragment初始化时需要传参，将参数放入Bundle，此处为Bundle对象
        );

        // 第二个标签页
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("标签2"),
                Fragment2.class, null);

        // 第三个标签页
        View view = LayoutInflater.from(this).inflate(R.layout.fragment_tab,
                null);
        Bundle bundle = new Bundle();
        bundle.putString("txt", "FragmentTabHost");
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(view),
                Fragment3.class, bundle);

        // 第四个标签页
        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("标签4"),
                Fragment2.class, null);

        //tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("标签4").setContent(new Intent(this,activity_tab_4.class)));
        //tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("标签4").setContent(new Intent(activity_fragmenttabhost.this,activity_tab_4.class)));



        // FragmentTabHost 的监听器
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                Toast.makeText(activity_fragmenttabhost.this, tabId, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

}
