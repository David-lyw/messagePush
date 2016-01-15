package com.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import engineering.R;

/**
 * Created by sd on 2015/11/2.
 */
public class activity_dialog_index extends Activity implements OnClickListener {

    public static final int DIALOG = 1;
    Button btn_dialog_1;
    Button btn_dialog_2;

    Button btn_dialog_3;
    AlertDialog dialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DIALOG:
                    //dialog.show();
                    Toast.makeText(getApplicationContext(), "2222222", Toast.LENGTH_LONG).show();
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_index);
        initView();
        initListener();
    }

    public void initView() {
        btn_dialog_1 = (Button) findViewById(R.id.btn_dialog_1);
        btn_dialog_2 = (Button) findViewById(R.id.btn_dialog_2);
        btn_dialog_3 = (Button) findViewById(R.id.btn_dialog_3);
    }

    public void initListener() {
        btn_dialog_1.setOnClickListener(this);
        btn_dialog_2.setOnClickListener(this);
        btn_dialog_3.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog_1:
                final ConfirmDialog confirmDialog = new ConfirmDialog(this, "确定退出吗？", "确定", "取消");
                confirmDialog.show();
                confirmDialog.setClicklistener(new ConfirmDialog.ClickListenerInterface() {
                    @Override
                    public void doConfirm() {
                        Toast.makeText(getApplicationContext(), "888", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void doCancel() {
                        confirmDialog.dismiss();
                    }
                });
                break;
            case R.id.btn_dialog_2:
                final CustomDialog customDialog = new CustomDialog(this, getRootLayout());
                customDialog.setTitle("Warning");
                customDialog.setMessage("hahahahahhahah");
                customDialog.setPositiveButton("OK", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });
                customDialog.setNegativeButton("Cancel", new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });
                customDialog.show();
                break;
            case R.id.btn_dialog_3://生成并弹出了两个对话框，以下两种写法都行。

                new AlertDialog.Builder(this).setTitle("请输入").setIcon(
                        android.R.drawable.ic_dialog_info).setView(
                        new EditText(this)).setPositiveButton("确定", null)
                        .setNegativeButton("取消", null).show();


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请输入");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setView(new EditText(this));
                builder.setPositiveButton("确定", null);
                builder.setNegativeButton("取消", null);
                dialog = builder.create();
                dialog.show();


                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);

                break;
            default:
                Toast.makeText(this, "没有点击事件对应的响应事件", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public View getRootLayout() {
        return (findViewById(android.R.id.content)).getRootView();
    }
}
