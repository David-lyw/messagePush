package com.fragmenttabhost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import engineering.R;

/**
 * Created by David on 16/4/12.
 */
public class activity_tab_4 extends Activity implements OnClickListener{
    Button btn_tab_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_4);
        btn_tab_1= (Button) findViewById(R.id.btn_tab_1);
        btn_tab_1.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_tab_1:
                Intent intent=new Intent(this,activity_tab_test1.class);
                startActivity(intent);
                break;
        }

    }
}
