package com.touchImageView.touchImageView2;

import android.app.Activity;
import android.os.Bundle;

import engineering.R;

import com.touchImageView.touchImageView2.TouchImageView.OnTouchImageViewListener;
public class MirroringExampleActivity extends Activity {
	
	TouchImageView topImage;
	TouchImageView bottomImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mirroring_example);
		topImage = (TouchImageView) findViewById(R.id.topImage);
		bottomImage = (TouchImageView) findViewById(R.id.bottomImage);
		
		//
		// Each image has an OnTouchImageViewListener which uses its own TouchImageView
		// to set the other TIV with the same zoom variables.
		//
		topImage.setOnTouchImageViewListener(new OnTouchImageViewListener() {
			
			@Override
			public void onMove() {
				bottomImage.setZoom(topImage);
			}
		});
		
		bottomImage.setOnTouchImageViewListener(new OnTouchImageViewListener() {
			
			@Override
			public void onMove() {
				topImage.setZoom(bottomImage);
			}
		});
	}
}
