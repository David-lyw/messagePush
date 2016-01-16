package com.touchImageView.touchImageView2;

import com.touchImageView.touchImageView2.TouchImageView.OnTouchImageViewListener;

import android.app.Activity;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import engineering.R;

public class MyInstanceActivity extends Activity {
	TouchImageView img_touchable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinstance);
		//设置imageview适应屏幕
		DisplayMetrics displayMetrics=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		img_touchable = (TouchImageView) findViewById(R.id.img_touchable);
		Log.i("lyw",displayMetrics.widthPixels+"+"+displayMetrics.heightPixels);
		//img_touchable.setLayoutParams(new ViewGroup.LayoutParams(displayMetrics.widthPixels,displayMetrics.widthPixels*841/595));
		img_touchable.setLayoutParams(new LinearLayout.LayoutParams(displayMetrics.widthPixels,displayMetrics.widthPixels*841/595));
		img_touchable.setImageResource(R.mipmap.tifa_lockhart);
	}
}
