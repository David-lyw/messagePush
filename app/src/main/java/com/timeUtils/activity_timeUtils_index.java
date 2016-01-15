package com.timeUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import engineering.R;

/**
 * Created by sd on 2015/11/11.
 */
public class activity_timeUtils_index extends Activity implements OnClickListener {
    TextView tv_setTime0;
    String initDateTiem;
    Button btn_recover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeutils_index);
        initView();
        initData();
        initListener();
    }

    public void initView() {
        tv_setTime0 = (TextView) findViewById(R.id.tv_setTime0);
        btn_recover= (Button) findViewById(R.id.btn_recover);
    }

    public void initData() {
        initDateTiem="2015年10月10日";
    }

    public void initListener() {
        tv_setTime0.setOnClickListener(this);
        btn_recover.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_setTime0:

                DateTimePickDialogUtil dateTimePickDialogUtil=new DateTimePickDialogUtil(this,initDateTiem);
                dateTimePickDialogUtil.dateTimePicKDialog(tv_setTime0);
                break;
            case R.id.btn_recover:
                tv_setTime0.setText("点击设置时间");
                break;
        }

    }
}
