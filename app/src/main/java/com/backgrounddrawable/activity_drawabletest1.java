package com.backgrounddrawable;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import engineering.R;

/**
 * Created by David on 16/4/27.
 */
public class activity_drawabletest1 extends Activity{
    ImageView iv_test1,iv_test2,iv_test3;
    TextView tv_test1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawabletest1);
        iv_test1= (ImageView) findViewById(R.id.iv_test1);
        iv_test2= (ImageView) findViewById(R.id.iv_test2);
        iv_test3= (ImageView) findViewById(R.id.iv_test3);
        tv_test1= (TextView) findViewById(R.id.tv_test1);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.tifa_lockhart);
        RoundImageDrawable one=new RoundImageDrawable(bitmap);

        CircleImageDrawable two=new CircleImageDrawable(bitmap);

        iv_test1.setImageDrawable(one);
        iv_test2.setImageDrawable(new RoundImageDrawable(bitmap));
        iv_test3.setImageDrawable(two);

    }
}
