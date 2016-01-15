package com.json;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.common.GsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.lang.String;


import engineering.R;

/**
 * Created by sd on 2015/11/9.
 */
public class activity_json_index extends Activity implements OnClickListener {
    Button btn_jsontoStringList;
    ArrayList<String> ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_index);
        initView();
        initData();
        initListener();
    }

    public void initView() {
        btn_jsontoStringList = (Button) findViewById(R.id.btn_jsontoStringList);
        //ls = new ArrayList<String>();
    }

    public void initData() {
    }

    public void initListener() {
        btn_jsontoStringList.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_jsontoStringList:
                ls = GsonUtils.jsonToStringList("{\"messageList\": [\"123123\", \"45654654\"]}");
                Log.i("lyw", "ArrayList数组大小：" + ls.size());
                for (int i = 0; i < ls.size(); i++) {
                    Log.i("lyw", ls.get(i));
                }
                break;
        }

    }


}
