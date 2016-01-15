package com.anyView;

import android.app.Activity;
import android.os.Bundle;

import engineering.R;

/**
 * Created by sd on 2015/11/12.
 */
public class activity_showGif extends Activity {
    GifView gifView_1,gifView_2,gifView_3,gifView_4,gifView_5,gifView_6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showgif);
        initView();
        initData();



    }

    public void initView() {
        gifView_1 = (GifView) findViewById(R.id.gifView_1);
        gifView_2 = (GifView) findViewById(R.id.gifView_2);
        gifView_3 = (GifView) findViewById(R.id.gifView_3);
        gifView_4 = (GifView) findViewById(R.id.gifView_4);
        gifView_5 = (GifView) findViewById(R.id.gifView_5);
        gifView_6 = (GifView) findViewById(R.id.gifView_6);

    }

    public void initData() {
        gifView_1.setMovieResource(R.raw.someb);
        gifView_2.setMovieResource(R.raw.good);
        gifView_3.setMovieResource(R.raw.bird);
        gifView_4.setMovieResource(R.raw.sing);
        gifView_5.setMovieResource(R.raw.goodmorning);
        gifView_6.setMovieResource(R.raw.classic);

    }
}
