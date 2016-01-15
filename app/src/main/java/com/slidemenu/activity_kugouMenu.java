package com.slidemenu;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import engineering.R;

public class activity_kugouMenu extends Activity implements OnClickListener {

    private RelativeLayout rl_menu3;
    private RelativeLayout rl_menu2;
    private RelativeLayout rl_menu1;
    private boolean menu3Showing = true;
    private boolean menu2Showing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kugoumenu);
        rl_menu3 = (RelativeLayout) findViewById(R.id.rl_menu3);
        rl_menu2 = (RelativeLayout) findViewById(R.id.rl_menu2);
        rl_menu1 = (RelativeLayout) findViewById(R.id.rl_menu1);
        Button btn_menu2 = (Button) findViewById(R.id.btn_menu2);
        Button btn_menu1 = (Button) findViewById(R.id.btn_menu1);

        btn_menu2.setOnClickListener(this);
        btn_menu1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_menu2:    // 单击菜单2按钮
                if (MenuUtil.hasAnimExcuting()) {
                    return;
                }

                if (menu3Showing) {
                    // 隐藏菜单3
                    MenuUtil.hide(rl_menu3);
                } else {
                    // 显示菜单3
                    MenuUtil.show(rl_menu3);
                }
                menu3Showing = !menu3Showing;
                break;
            case R.id.btn_menu1:    // 单击菜单1按钮
                if (MenuUtil.hasAnimExcuting()) {
                    return;
                }

                if (menu3Showing) {
                    // 先隐藏菜单3
                    MenuUtil.hide(rl_menu3);
                    menu3Showing = false;

                    // 再隐藏菜单2（延时）
                    MenuUtil.hide(rl_menu2, 300);
                } else if (menu2Showing) {
                    // 隐藏菜单2
                    MenuUtil.hide(rl_menu2);
                } else {
                    // 显示菜单2
                    MenuUtil.show(rl_menu2);
                }
                menu2Showing = !menu2Showing;
                break;
        }
    }

}

