package com.quitAPP;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.common.ScreenManager;

import engineering.R;

/**
 * Created by sd on 2015/11/2.
 */
public class activity4 extends Activity implements OnClickListener {
    ScreenManager screenManager;
    Intent intent;
    Button btn_actibity4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity4);
        initView();
        initListener();
    }

    public void initView() {
        screenManager=ScreenManager.getScreenManager();

        btn_actibity4 = (Button) findViewById(R.id.btn_actibity4);
    }

    public void initListener() {
        btn_actibity4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_actibity4:
                screenManager.pushActivity(this);
                intent = new Intent(this, activity5.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
