package com.slidemenu;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import engineering.R;

/**
 * Created by sd on 2015/10/19.
 */
public class activity_slideMenu_activity extends Activity {

    private ViewPager viewPager;
    private List<View> list;
    private ImageView imageView;
    private ImageView[] imageViews;
    private int num = 300;
    private LocalActivityManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidemenu_activity);
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);

        list = new ArrayList<View>();
        Intent intent = new Intent(activity_slideMenu_activity.this, ImageActivity1.class);
        list.add(getView("ImageActivity1", intent));

        intent = new Intent(activity_slideMenu_activity.this, ImageActivity2.class);
        getView("ImageActivity2", intent);
        list.add(getView("ImageActivity2", intent));

        intent = new Intent(activity_slideMenu_activity.this, ImageActivity3.class);
        getView("ImageActivity3", intent);
        list.add(getView("ImageActivity3", intent));

        intent = new Intent(activity_slideMenu_activity.this, ImageActivity4.class);
        getView("ImageActivity4", intent);
        list.add(getView("ImageActivity4", intent));

        intent = new Intent(activity_slideMenu_activity.this, ImageActivity5.class);
        getView("ImageActivity5", intent);
        list.add(getView("ImageActivity5", intent));

        imageViews = new ImageView[list.size()];

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.viewGroup);

        viewPager = (ViewPager) findViewById(R.id.viewPager_activity);

        for (int i = 0; i < list.size(); i++) {
            imageViews[i] = new ImageView(this);
            imageViews[i].setLayoutParams(new LinearLayout.LayoutParams(12, 12));
            if (i == 0) {
                imageViews[i].setBackgroundResource(R.mipmap.white);
            } else {
                imageViews[i].setBackgroundResource(R.mipmap.black);
            }
            linearLayout.addView(imageViews[i]);
        }

        viewPager.setAdapter(new MyAdapter());
        viewPager.setOnPageChangeListener(new MyListener());
        viewPager.setCurrentItem(300);

        //放开代码实现自动切换.
        // mHandler.postDelayed(mRunnable, 2000);
    }

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        public void run() {
            // 每隔多长时间执行一次
            // mHandler.postDelayed(mRunnable, 1000*PhoneConstans.TIMEVALUE);
            mHandler.postDelayed(mRunnable, 1000 * 2);
            num++;
            viewHandler.sendEmptyMessage(num);
        }

    };

    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        mHandler.removeCallbacks(mRunnable);
    }


    private final Handler viewHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(msg.what);
            super.handleMessage(msg);
        }

    };

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            // TODO Auto-generated method stub
            ((ViewPager) arg0).removeView(list.get(arg1 % list.size()));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            // TODO Auto-generated method stub
            try {
                // ((ViewPager) arg0).addView(list.get(arg1),0);
                ((ViewPager) arg0).addView((View) list.get(arg1 % list.size()),
                        0);
            } catch (Exception e) {
                // TODO: handle exception
            }

            return list.get(arg1 % list.size());

        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }
    }

    class MyListener implements ViewPager.OnPageChangeListener {

        // 当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            // arg0=arg0%list.size();

        }

        // 当当前页面被滑动时调用
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        // 当新的页面被选中时调用
        @Override
        public void onPageSelected(int arg0) {
            num = arg0;
            arg0 = arg0 % list.size();
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[arg0].setBackgroundResource(R.mipmap.white);
                if (arg0 != i) {
                    imageViews[i].setBackgroundResource(R.mipmap.black);
                }
            }

            System.out.println("当前是第" + arg0 + "页");
        }

    }
}
