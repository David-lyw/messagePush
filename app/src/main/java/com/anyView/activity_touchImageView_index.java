package com.anyView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.touchImageView.touchImageView1.activity_touchimageview1;
import com.touchImageView.touchImageView2.activity_touchimageview2;

import engineering.R;

/**
 * Created by David on 16/1/16.
 */
public class activity_touchImageView_index extends Activity implements OnClickListener{
    Intent intent;
    Button btn_touchImageView1;
    Button btn_touchImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchimageview_index);
        initView();
        initListener();
    }
    public void initView(){
        btn_touchImageView1= (Button) findViewById(R.id.btn_touchImageView1);
        btn_touchImageView2= (Button) findViewById(R.id.btn_touchImageView2);
    }
    public void initListener(){
        btn_touchImageView1.setOnClickListener(this);
        btn_touchImageView2.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_touchImageView1:
                intent=new Intent(this,activity_touchimageview1.class);
                startActivity(intent);

                break;
            case R.id.btn_touchImageView2:
                intent=new Intent(this,activity_touchimageview2.class);
                startActivity(intent);
                break;
        }
    }
}
