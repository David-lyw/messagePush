package com.SDPathBrowse;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;

import engineering.R;

/**
 * @version 1.0
 */
@SuppressLint("NewApi")
public class SDPathBrowse1 extends Activity implements SearchView.OnQueryTextListener {
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

    SearchView sv_;

    //listItems存储listview要加载的数据，根据serachView的输入，listView加载部分数据或全部数据.
    List<Map<String, Object>> listItems;
    //tmplistItems是listItem全部数据的拷贝。
    List<Map<String, Object>> tmplistItems;

    MyAdapter myAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sdpathbrowse1);
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

        sv_ = (SearchView) findViewById(R.id.sv_);

        //searchView输入后 右面有搜索按钮.false则没有.
        sv_.setSubmitButtonEnabled(false);
        //搜索图标是否在输入框中
        sv_.setIconifiedByDefault(true);


        //通过反射，修改默认的样式，可以从android的search_view.xml中找到需要的组件
        //argClass.getDeclaredField("mSearchHintIcon");
        //argClass.getDeclaredField("mSearchPlate");
        //argClass.getDeclaredField("mQueryTextView");
        //mTextViewClass.getDeclaredField("mCursorDrawableRes");
        try {
            Field field = sv_.getClass().getDeclaredField("mSubmitButton");//此处代码修改 提交按钮 的图样。
            field.setAccessible(true);
            ImageView iv = (ImageView) field.get(sv_);
            iv.setImageDrawable(this.getResources().getDrawable(R.mipmap.ic_launcher));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Class argClass = sv_.getClass();

            //注意mSearchPlate的背景是stateListDrawable(不同状态不同的图片)  所以不能用BitmapDrawable
            Field ownField = argClass.getDeclaredField("mSearchPlate");//修改 搜索框底部 的蓝线（效果：设置的图片变成扁长状态）----lyw
            //setAccessible 它是用来设置是否有权限访问反射类中的私有属性的，只有设置为true时才可以访问，默认为false
            ownField.setAccessible(true);
            View mView = (View) ownField.get(sv_);
            mView.setBackground(getResources().getDrawable(R.mipmap.ic_launcher));
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 创建一个List集合，List集合的元素是Map
        listItems = new ArrayList<Map<String, Object>>();
        tmplistItems = new ArrayList<Map<String, Object>>();

        //用到的数据集合（listItems）在本类中是全局变量，adapter类写到了同一个文件中。若不在同一个文件中，adapter的构造函数需传递数据集合，用于本文件的逻辑就需要改变。
        myAdapter = new MyAdapter(this);

        listView.setAdapter(myAdapter);
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

            trimCurrentFiles(currentFiles);
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
                    Toast.makeText(SDPathBrowse1.this
                            , "当前路径不可访问或该路径下没有文件",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // 获取用户单击的列表项对应的文件夹，设为当前的父文件夹
                    currentParent = currentFiles[position];
                    // 保存当前的父文件夹内的全部文件和文件夹（即将临时tmp赋值给currentFiles).
                    currentFiles = tmp;
                    trimCurrentFiles(currentFiles);
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

                        trimCurrentFiles(currentFiles);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //为searchview设置监听事件
        sv_.setOnQueryTextListener(this);


        //点击搜索框弹出软件盘，但输入文本没实现。.
//        sv_.setFocusableInTouchMode(true);
//        sv_.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sv_.setOnQueryTextListener(SDPathBrowse1.this);
//                InputMethodManager inputManager = (InputMethodManager) sv_.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputManager.showSoftInput(sv_, 0);
//            }
//        });


        //调用此方法为searchview布局中子控件设置监听。setSearchViewOnClickListener方法在本文件别处实现。
//        setSearchViewOnClickListener(sv_, new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }


    /**
     * 初始化，返回上一级路径，进入下一级路径。
     * 整理数据，放置到listItems中。然后adapter.notifyDataSetChanged().
     */
    private void trimCurrentFiles(File[] files) {
        listItems.clear();
        Map<String, Object> listItem;
        for (int i = 0; i < files.length; i++) {
            listItem = new HashMap<String, Object>();
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
        tmplistItems.addAll(listItems);
        myAdapter.notifyDataSetChanged();//每次listItems中的数据改变，通知adapter.

        try {
            textView.setText("当前路径为："
                    + currentParent.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onQueryTextChange(String s) {
        //当搜索文字发生改变，此处执行的代码与activity_searchView相应位置执行的代码做比对，同一功能两种不同的实现。
        if (TextUtils.isEmpty(s)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(s.toString());
        }
        return true;
    }

    //单击搜索按钮时激发该方法
    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }


    public class MyAdapter extends BaseAdapter implements Filterable {
        private MyFilter myFilter;

        // private List<Map<String, Object>> list_Items;
        private LayoutInflater mInflater;

        public ImageView iv;
        public TextView tv;
        Bitmap bitmap;

        public MyAdapter(Context c) {
            mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //this.list_Items = listItems;
        }


        @Override
        public int getCount() {
            if (listItems == null)
                return 0;
            return listItems.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup Parent) {

            convertView = mInflater.inflate(R.layout.activity_sdpathbrowse1_item, null);
            iv = (ImageView) convertView.findViewById(R.id.iv_folder);
            tv = (TextView) convertView.findViewById(R.id.tv_filename);

            bitmap = BitmapFactory.decodeResource(getResources(), (Integer) listItems.get(position).get("icon"));
            iv.setImageBitmap(bitmap);
            tv.setText((CharSequence) listItems.get(position).get("fileName"));
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return listItems.get(position);
        }

        @Override
        public Filter getFilter() {
            if (null == myFilter) {
                myFilter = new MyFilter();
            }
            return myFilter;
        }

        class MyFilter extends Filter {
            @Override
            // 该方法在子线程中执行
            // 自定义过滤规则
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                List<Map<String, Object>> newValues = new ArrayList<Map<String, Object>>();
                String filterString = charSequence.toString().trim();

                if (TextUtils.isEmpty(filterString)) {
                    newValues = tmplistItems;
                } else {
                    for (int i = 0; i < tmplistItems.size(); i++) {
                        String str = (String) tmplistItems.get(i).get("fileName");
                        if (-1 != str.indexOf(filterString)) {
                            newValues.add(tmplistItems.get(i));
                        }
                    }
                }
                results.values = newValues;
                results.count = newValues.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listItems = (List<Map<String, Object>>) filterResults.values;
                if (filterResults.count > 0) {
                    myAdapter.notifyDataSetChanged();
                } else {
                    myAdapter.notifyDataSetInvalidated();
                }

            }
        }
    }


    //searchView是一个线性布局，此方法为布局中控件添加监听事件.(目前不知道怎么用？？）
    public void setSearchViewOnClickListener(View v, OnClickListener listener) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = group.getChildAt(i);
                if (child instanceof LinearLayout || child instanceof RelativeLayout) {//如果child还是布局，递归调用本方法。
                    setSearchViewOnClickListener(child, listener);
                }
                if (child instanceof TextView) {
                    TextView text = (TextView) child;
                    text.setFocusable(false);
                }
                child.setOnClickListener(listener);
            }
        }
    }
}