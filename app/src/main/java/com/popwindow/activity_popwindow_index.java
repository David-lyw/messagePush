package com.popwindow;

/**
 * Created by sd on 2015/11/2.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;


import engineering.R;

/**
 * Created by sd on 2015/11/2.
 */
public class activity_popwindow_index extends Activity implements OnClickListener {
    PopupWindow popupWindow;
    Button btn_popwindow_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popwindow_index);
        initView();
        initListener();
    }

    public void initView() {
        btn_popwindow_1 = (Button) findViewById(R.id.btn_popwindow_1);

    }

    public void initListener() {
        btn_popwindow_1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_popwindow_1:
                showPopupWindow(view);
                break;
            default:
                Toast.makeText(this, "没有点击事件对应的响应事件", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.dialog_logoutaccount, null);
        // 设置按钮的点击事件
        TextView tv_ok = (TextView) contentView.findViewById(R.id.confirm);
        TextView tv_cnacel = (TextView) contentView.findViewById(R.id.cancel);
        tv_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "button is pressed",
                        Toast.LENGTH_SHORT).show();
            }
        });
        tv_cnacel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();//会销毁本界面返回上界面。
                popdimiss();//pop消失。
            }
        });

        popupWindow = new PopupWindow(contentView,
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.mipmap.qq));


        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }

    public void popdimiss() {
        popupWindow.dismiss();
    }
}
