package com.dialog;

/**
 * Created by sd on 2015/11/2.
 */

import android.content.Context;

import android.view.Gravity;

import android.view.LayoutInflater;

import android.view.View;

import android.view.View.OnClickListener;

import android.view.ViewGroup.LayoutParams;

import android.widget.Button;

import android.widget.LinearLayout;

import android.widget.PopupWindow;

import android.widget.RelativeLayout;
import android.widget.TextView;

import engineering.R;

public class CustomDialog {
    private View mParent;

    private PopupWindow mPopupWindow;

    private LinearLayout mRootLayout;

    private LayoutParams mLayoutParams;


    //PopupWindow必须有一个ParentView，所以必须添加这个参数

    public CustomDialog(Context context, View parent) {


        mParent = parent;


        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


      //加载布局文件

        mRootLayout = (LinearLayout) mInflater.inflate(R.layout.dialog_logoutaccount, null);


        mLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    }


//设置Dailog的标题

    public void setTitle(String title) {

        TextView mTitle = (TextView) mRootLayout.findViewById(R.id.title);

        mTitle.setText(title);

    }


//设置Dailog的主体内容

    public void setMessage(String message) {

        TextView mMessage = (TextView) mRootLayout.findViewById(R.id.tv_content);

        mMessage.setText(message);

    }


//设置Dailog的“确定”按钮

    public void setPositiveButton(String text, OnClickListener listener) {

        final TextView tv_OK = (TextView) mRootLayout.findViewById(R.id.confirm);

        tv_OK.setText(text);

        tv_OK.setOnClickListener(listener);

        tv_OK.setVisibility(View.VISIBLE);

    }


    //设置Dailog的“取消”按钮

    public void setNegativeButton(String text, OnClickListener listener) {

        final TextView tv_cancel = (TextView) mRootLayout.findViewById(R.id.cancel);

        tv_cancel.setText(text);

        tv_cancel.setOnClickListener(listener);

        tv_cancel.setVisibility(View.VISIBLE);

    }


    //替换Dailog的“主体”布局

    public void setContentLayout(View layout) {


        TextView mMessage = (TextView) mRootLayout.findViewById(R.id.tv_content);

        mMessage.setVisibility(View.GONE);


        RelativeLayout contentLayout = (RelativeLayout) mRootLayout.findViewById(R.id.ContentView);

        contentLayout.addView(layout);

    }


    //设置Dailog的长宽

    public void setLayoutParams(int width, int height) {

        mLayoutParams.width = width;

        mLayoutParams.height = height;

    }


    //显示Dailog

    public void show() {


        if (mPopupWindow == null) {

            mPopupWindow = new PopupWindow(mRootLayout, mLayoutParams.width, mLayoutParams.height);

            mPopupWindow.setFocusable(true);

        }


        mPopupWindow.showAtLocation(mParent, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);

    }


    //取消Dailog的显示

    public void dismiss() {


        if (mPopupWindow == null) {

            return;

        }

        mPopupWindow.dismiss();

    }

}