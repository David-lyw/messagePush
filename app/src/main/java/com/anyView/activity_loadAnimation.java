package com.anyView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import engineering.R;

/**
 * Created by sd on 2015/11/20.
 */
public class activity_loadAnimation extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadanimation);
    }

    /**
     * 显示美团进度对话框
     * @param v
     */
    public void showmeidialog(View v){
        CustomProgressDialog dialog =new CustomProgressDialog(this, "正在加载中",R.anim.frame,0);
        dialog.show();
    }
    /**
     * 显示顺丰快递员进度对话框
     * @param v
     */
    public void showsfdialog(View v){
        CustomProgressDialog dialog =new CustomProgressDialog(this, "正在加载中",R.anim.frame2,0);
        dialog.show();
    }
}
