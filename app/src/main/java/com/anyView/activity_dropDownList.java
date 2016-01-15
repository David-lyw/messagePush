package com.anyView;


import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;

import engineering.R;

public class activity_dropDownList extends Activity implements OnClickListener {

    private EditText et_number;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropdownlist);
        et_number = (EditText) findViewById(R.id.et_number);
        ImageButton ib_arrow = (ImageButton) findViewById(R.id.ib_arrow);
        ib_arrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_arrow:
                showNumberList();
                break;
        }
    }

    private void showNumberList() {
//		1、	创建一个PopupWindow对象
        if (popupWindow == null) {
            ListView listView = createListView();
            int width = et_number.getWidth() - 4;    // 设置PopupWindow的宽
            int height = 300;                    // 设置PopupWindow的高
            boolean focusable = true;            // 设置可以获取焦点
            popupWindow = new PopupWindow(listView, width, height, focusable);

            // 设置点击PopupWindow之外的地方的时候把PopupWindow隐藏
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable());
        }

        //		2、	显示PopupWindow
        View anchor = et_number;    // 指定PopupWindow显示在哪个View的下方
        int xoff = 2;    // PopupWindow水平方向的偏移量
        int yoff = -5;    // PopupWindow垂直方向的偏移量
        popupWindow.showAsDropDown(anchor, xoff, yoff);
    }

    private ListView createListView() {
        ListView listView = (ListView) View.inflate(this, R.layout.number_list, null);
        listView.setAdapter(new NumberAdapter());
        listView.setVerticalScrollBarEnabled(false); // 隐藏垂直滚动条
        listView.setOnItemClickListener(mOnItemClickListener);
        return listView;
    }

    OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        // 参数1：AdapterView其实就是ListView
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 把号码显示到输入框上面
            String number = (String) parent.getItemAtPosition(position);
            et_number.setText(number);

            // 隐藏PopupWindow
            popupWindow.dismiss();
        }
    };

}

















