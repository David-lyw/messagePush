package com.touchImageView.touchImageView2;


import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import engineering.R;

public class NormalImageViewActivity extends Activity{
	ImageView iv_normal;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_normalimageview);
		iv_normal= (ImageView) findViewById(R.id.iv_normal);
		//设置imageview适应屏幕
		DisplayMetrics displayMetrics=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		Log.i("lyw", displayMetrics.widthPixels + "+" + displayMetrics.heightPixels);
		iv_normal.setLayoutParams(new LinearLayout.LayoutParams(720, 1280));
		iv_normal.setImageResource(R.mipmap.tifa_lockhart);
	}

}
