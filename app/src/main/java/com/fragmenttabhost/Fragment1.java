package com.fragmenttabhost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import engineering.R;


public class Fragment1 extends Fragment {
	private String[] txts ;
	private TextView tv ;
	private Button btn ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		txts = new String[5] ;
		txts[0] = "Hello" ;
		txts[1] = "你好" ;
		txts[2] = "今天天气不错" ;
		txts[3] = "雾霾" ;
		txts[4] = "后天开始降温" ;
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_1, container, false) ;
		tv = (TextView) view.findViewById(R.id.fragment1_tv) ;
		btn = (Button) view.findViewById(R.id.fragment1_btn) ;
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv.setText(txts[(int) (Math.random() * txts.length)]) ;
			}
		}) ;
		return view ;
	}

	@Override
	public void onPause() {
		super.onPause();
		// 当Fragment不可见时保存数据
	}
}
