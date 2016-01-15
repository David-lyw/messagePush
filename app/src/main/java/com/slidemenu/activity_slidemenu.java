package com.slidemenu;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.util.zip.Inflater;

import engineering.R;

public class activity_slidemenu extends ActivityGroup implements OnClickListener {
    private LinearLayout ll_mainContent;
    LayoutInflater inflater;

    private SlidingMenu slidingMenu;
    private TextView tv_slidemenu_readtogether;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_slidemenu_);
        ll_mainContent = (LinearLayout) findViewById(R.id.ll_mainContent);
        slidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);

        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);





        findViewById(R.id.tv_0).setOnClickListener(this);
        findViewById(R.id.tv_1).setOnClickListener(this);
        findViewById(R.id.tv_2).setOnClickListener(this);
        findViewById(R.id.tv_3).setOnClickListener(this);
        findViewById(R.id.tv_4).setOnClickListener(this);
        findViewById(R.id.tv_5).setOnClickListener(this);
        findViewById(R.id.tv_6).setOnClickListener(this);
        findViewById(R.id.tv_slidemenu_readtogether).setOnClickListener(this);

    }

    //布局中绑定的方法。
    public void onMenuClick(View v) {
        slidingMenu.openMenu();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_1://加载的布局
                ll_mainContent.removeAllViews();
                ll_mainContent.addView(inflater.inflate(R.layout.item3, null));
                break;


            case R.id.tv_slidemenu_readtogether://加载的activity
                //   bodyLayout.addView( getLocalActivityManager().startActivity( "a1", intentBase ).getDecorView() );
                ll_mainContent.removeAllViews();
                ll_mainContent.addView(getLocalActivityManager().startActivity("123", new Intent(this, ImageActivity2.class)).getDecorView());
                break;

            default:
                break;
        }
    }

}
