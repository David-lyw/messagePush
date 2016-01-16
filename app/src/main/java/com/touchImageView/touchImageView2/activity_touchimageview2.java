package com.touchImageView.touchImageView2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import engineering.R;

public class activity_touchimageview2 extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touchimageview2);
		findViewById(R.id.single_touchimageview_button).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivity(new Intent(activity_touchimageview2.this,
								SingleTouchImageViewActivity.class));
					}
				});
		findViewById(R.id.viewpager_example_button).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivity(new Intent(activity_touchimageview2.this,
								ViewPagerExampleActivity.class));
					}
				});
		findViewById(R.id.mirror_touchimageview_button).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivity(new Intent(activity_touchimageview2.this,
								MirroringExampleActivity.class));
					}
				});
		findViewById(R.id.switch_image_button).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivity(new Intent(activity_touchimageview2.this,
								SwitchImageExampleActivity.class));
					}
				});
		findViewById(R.id.switch_scaletype_button).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivity(new Intent(activity_touchimageview2.this,
								SwitchScaleTypeExampleActivity.class));
					}
				});
		findViewById(R.id.btn_singlePicture).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivity(new Intent(activity_touchimageview2.this,
								MyInstanceActivity.class));
					}
				});

		findViewById(R.id.btn_normalImageView).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(activity_touchimageview2.this,
								NormalImageViewActivity.class));
					}
				});
	}
}