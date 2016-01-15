package com.quitAPP;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.common.ScreenManager;
import com.dialog.CustomDialog;

import engineering.R;

/**
 * Created by sd on 2015/11/2.
 */
public class activity5 extends Activity implements OnClickListener {
    ScreenManager screenManager;
    Button btn_actibity5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity5);
        initView();
        initListener();
    }

    public void initView() {
        screenManager = ScreenManager.getScreenManager();

        btn_actibity5 = (Button) findViewById(R.id.btn_actibity5);
    }

    public void initListener() {
        btn_actibity5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_actibity5:
                logout();
                break;
        }

    }

    public void logout() {
        final CustomDialog customDialog = new CustomDialog(this, getRootLayout());
        customDialog.setTitle("提示");
        customDialog.setMessage("删除本地缓存数据并退出当前用户，您确定要这样做吗？");
        customDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //确定系统退出，删除用户名，密码，相关缓存，退出相关服务，删除相关数据。
                //finish();
                //android.os.Process.killProcess(android.os.Process.myPid());   //获取PID
                //System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
                screenManager.popAllActivityExceptOne(activity5.class);
                finish();

//                //实现home键功能，实现app退出.
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                startActivity(intent);

            }
        });
        customDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });

        customDialog.show();

    }

    public View getRootLayout() {
        return (findViewById(android.R.id.content)).getRootView();
    }
}
