package com.SDPathBrowse;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.LayoutInflaterCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;

import engineering.R;

/**
 * @version 1.0
 */
@SuppressLint("NewApi")
public class SDPathBrowse extends Activity implements SearchView.OnQueryTextListener {
    //显示本路径下所有的文件夹和文件.
    ListView listView;
    //标题头，显示当前路径.
    TextView textView;
    // 记录当前的父文件夹
    File currentParent;

    // 记录当前路径下的所有文件的文件数组
    File[] currentFiles;


    //返回图标
    ImageView parent;

    SearchView searchView;

    // 创建一个List集合，List集合的元素是Map
    List<Map<String, Object>> listItems;

    SimpleAdapter simpleAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sdpathbrowse);
        initView();
        initData();
        initListener();
    }


    public void initView() {
        // 获取列出全部文件的ListView
        listView = (ListView) findViewById(R.id.lv_list);
        listView.setTextFilterEnabled(true);


        //标题头，显示当前路径.
        textView = (TextView) findViewById(R.id.tv_path);


        // 返回上一级目录的 图标
        parent = (ImageView) findViewById(R.id.iv_back);

        searchView = (SearchView) findViewById(R.id.sv_);

        listItems = new ArrayList<Map<String, Object>>();
    }

    public void initData() {
        // 获取系统的SD卡的目录,两段代码都可。
        //File root = new File("/mnt/sdcard/");
        File root = Environment.getExternalStorageDirectory();
        Log.i("lyw", root.toString());//storage/emulated/0

        // 如果 SD卡存在
        if (root.exists()) {
            currentParent = root;

            //获取当前文件夹下的所有文件和文件夹
            currentFiles = root.listFiles();

            // 使用当前目录下的全部文件、文件夹来填充ListView
            inflateListView(currentFiles);
        }
    }

    @SuppressLint("NewApi")
    public void initListener() {
        // 为ListView的列表项的单击事件绑定监听器
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 用户单击了文件，直接返回，不做任何处理
                if (currentFiles[position].isFile()) {
                    //此处可做对文件的各种操作，如对MP3的播放，图片的预览（预览界面可滑动），文本的打开等。
                    return;
                }
                // 获取用户点击的文件夹下的所有文件
                File[] tmp = currentFiles[position].listFiles();
                if (tmp == null || tmp.length == 0) {
                    Toast.makeText(SDPathBrowse.this
                            , "当前路径不可访问或该路径下没有文件",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // 获取用户单击的列表项对应的文件夹，设为当前的父文件夹
                    currentParent = currentFiles[position]; //②
                    // 保存当前的父文件夹内的全部文件和文件夹（即将临时tmp赋值给currentFiles).
                    currentFiles = tmp;
                    // 再次更新ListView
                    inflateListView(currentFiles);
                }
            }
        });

        parent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                try {
                    String currentPath = currentParent.getCanonicalPath();
                    Log.i("lyw", currentPath);
                    if (!currentParent.getCanonicalPath()//返回规范路径名
                            .equals("/")) {
                        // 获取上一级目录
                        currentParent = currentParent.getParentFile();
                        // 列出当前目录下所有文件
                        currentFiles = currentParent.listFiles();
                        // 再次更新ListView
                        inflateListView(currentFiles);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        searchView.setOnQueryTextListener(this);
    }


    private void inflateListView(File[] files) {
        listItems.clear();
        for (int i = 0; i < files.length; i++) {
            Map<String, Object> listItem =
                    new HashMap<String, Object>();
            // 如果当前File是文件夹，使用folder图标；否则使用file图标
            if (files[i].isDirectory()) {
                listItem.put("icon", R.mipmap.folder);
            } else {
                listItem.put("icon", R.mipmap.file);
            }
            listItem.put("fileName", files[i].getName());
            // 添加List项
            listItems.add(listItem);
        }
        // 创建一个SimpleAdapter
        simpleAdapter = new SimpleAdapter(this
                , listItems, R.layout.activity_sdpathbrowse_item
                , new String[]{"icon", "fileName"}
                , new int[]{R.id.iv_folder, R.id.tv_filename});


        // 为ListView设置Adapter
        listView.setAdapter(simpleAdapter);

        try {
            textView.setText("当前路径为："
                    + currentParent.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (TextUtils.isEmpty(s)) {
            // Clear the text filter.
            listView.clearTextFilter();
        } else {
            // Sets the initial value for the text filter.
            listView.setFilterText(s.toString());
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }


//    public class MySimpleAdapter extends SimpleAdapter implements Filterable {
//      private Context context;
//        List<Map<String, Object>> listItem;
//        private LayoutInflater  layoutInflater;
////       int resource;
////        String[] form;
////        int[] to;
//
//        MySimpleAdapter(Context context, List<Map<String, Object>> data,int resource,String[] from,int[] to){
//            super(context,data,resource,from,to);
//            this.context=context;
//            this.listItem=data;
//        }
//
//        @Override
//        public int getCount() {
//            return listItem.size();
//        }
//    }
}