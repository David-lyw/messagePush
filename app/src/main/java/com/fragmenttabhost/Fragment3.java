package com.fragmenttabhost;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import engineering.R;

public class Fragment3 extends Fragment {
    /**
     * 当初始化需要传入参数的Fragment时，调用该方法初始化Fragment对象
     *
     * @param msg Fragment所需要传入的参数
     * @return
     */
    public static Fragment3 newInstance(String msg) {
        Bundle bundle = new Bundle();
        bundle.putString("txt", msg);
        Fragment3 f = new Fragment3();
        f.setArguments(bundle); // 给Fragment设置一个参数，保存在Bundle中
        return f;
    }

    /**
     * 取出Fragment所需要的参数
     *
     * @return
     */
    private String getText() {
        String txt = null;
        Bundle bundle = getArguments();
        if (null != bundle) {
            txt = bundle.getString("txt");
        }
        return txt;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        TextView tv = (TextView) view.findViewById(R.id.fragment3_tv);
        // tv.setText("从Activity传递过来");
        tv.setText(getText());
        Button btn_1= (Button) view.findViewById(R.id.btn_1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),activity_tab_test1.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
