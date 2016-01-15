package com.slidemenu;

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
public class activity_slideMenu_index extends Activity implements OnClickListener {
    Button btn_upload_xml;
    Button btn_upload_activity;
    Button btn_upload_img;
    Button btn_upload_activity2;
    Button btn_slideMenu;
    Button btn_youkuMenu;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidemenu_index);
        initView();
        initData();
        initListener();

    }

    public void initView() {
        btn_upload_xml = (Button) findViewById(R.id.btn_upload_xml);
        btn_upload_activity = (Button) findViewById(R.id.btn_upload_activity);
        btn_upload_img = (Button) findViewById(R.id.btn_upload_img);
        btn_upload_activity2 = (Button) findViewById(R.id.btn_upload_activity2);
        btn_slideMenu = (Button) findViewById(R.id.btn_slideMenu);
        btn_youkuMenu = (Button) findViewById(R.id.btn_youkuMenu);

    }

    public void initData() {

    }

    public void initListener() {
        btn_upload_xml.setOnClickListener(this);
        btn_upload_activity.setOnClickListener(this);
        btn_upload_img.setOnClickListener(this);
        btn_upload_activity2.setOnClickListener(this);
        btn_slideMenu.setOnClickListener(this);
        btn_youkuMenu.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_upload_xml:
                intent = new Intent(this, activity_slideMenu_xml.class);
                startActivity(intent);
                break;
            case R.id.btn_upload_activity:
                intent = new Intent(this, activity_slideMenu_activity.class);
                startActivity(intent);
                break;
            case R.id.btn_upload_img:
                intent = new Intent(this, activity_slideMenu_img.class);
                startActivity(intent);
                break;
            case R.id.btn_upload_activity2:
                intent = new Intent(this, ProcessMainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_slideMenu:
                intent = new Intent(this, activity_slidemenu.class);
                startActivity(intent);

                break;
            case R.id.btn_youkuMenu://优酷菜单
                intent=new Intent(this,activity_kugouMenu.class);
                startActivity(intent);
                break;
        }

    }
}
