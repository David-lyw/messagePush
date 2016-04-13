package com.fragmenttabhost;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import engineering.R;

/**
 * Created by David on 16/4/12.
 */
public class activity_tab_test1 extends Activity implements OnClickListener {
    Button btn_backto_index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_test1);
        btn_backto_index= (Button) findViewById(R.id.btn_backtoindex);
        btn_backto_index.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_backtoindex:
                //此处可用bundle传递数据，指定显示首页的特定fragment。
                Intent intent=new Intent(this,activity_fragmenttabhost.class);
                startActivity(intent);
                finish();
                break;
        }

    }
}
