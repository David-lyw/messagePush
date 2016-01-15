package com.anyView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.utils.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engineering.R;

/**
 * Created by sd on 2015/11/24.
 */
public class activity_gridview extends Activity implements OnClickListener {

    private GridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;

    private int[] icon = {R.mipmap.timomengzhu, R.mipmap.timomengzhu,
            R.mipmap.timomengzhu, R.mipmap.timomengzhu, R.mipmap.timomengzhu,
            R.mipmap.timomengzhu, R.mipmap.timomengzhu, R.mipmap.timomengzhu,
            R.mipmap.timomengzhu, R.mipmap.timomengzhu, R.mipmap.timomengzhu,
            R.mipmap.timomengzhu};
    private String[] iconName = {"通讯录", "日历", "照相机", "时钟", "游戏", "短信", "铃声",
            "设置", "语音", "天气", "浏览器", "视频"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);
        gview = (GridView) findViewById(R.id.gview);
        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String[] from = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.activity_gridview_item, from, to);
        //配置适配器
        gview.setAdapter(sim_adapter);

        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                L.i("hahahahahhahahaha");
                Toast.makeText(activity_gridview.this, "position"+i+"id"+l, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public List<Map<String, Object>> getData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
    }

    @Override
    public void onClick(View view) {

    }
}
