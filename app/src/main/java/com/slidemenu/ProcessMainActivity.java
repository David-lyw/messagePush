package com.slidemenu;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.common.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import engineering.R;

/**
 * Created by sd on 2015/9/28.
 * 报销的主流程
 */
public class ProcessMainActivity extends BaseActivity {
    private ViewPager viewPager;
    private List<View> list;
    private ImageView imageView;

    //dot标志点所用到的数组.
    private ImageView[] imageViews;
    private int num = 300;
    private LocalActivityManager manager;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(R.layout.process_main);
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);
        initData();

    }

    @Override
    public void initView(int viewId) {
        super.initView(viewId);
    }


    @Override
    public void onClick(View v) {

    }

    /**
     * 初始化数据的方法
     * sun
     */
    public void initData() {
        list = new ArrayList<View>();

        intent = new Intent(ProcessMainActivity.this, ProcessFinancialManageActivity.class);
        list.add(getView("ImageActivity1", intent));

        intent = new Intent(ProcessMainActivity.this, ProcessGeneralDepartmentActivity.class);
        list.add(getView("ImageActivity2", intent));

        intent = new Intent(ProcessMainActivity.this, ProcessInformationActivity.class);
        list.add(getView("ImageActivity3", intent));

        intent = new Intent(ProcessMainActivity.this, ProcessOverAllActivity.class);
        list.add(getView("ImageActivity4", intent));

        intent = new Intent(ProcessMainActivity.this, ProcessPropertyManageActivity.class);
        list.add(getView("ImageActivity5", intent));

        imageViews = new ImageView[list.size()];

        //dot标志点所在的布局.
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.viewGroup);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        for (int i = 0; i < list.size(); i++) {
            imageViews[i] = new ImageView(this);
            imageViews[i].setLayoutParams(new LinearLayout.LayoutParams(20, 18));
            if (i == 0) {
                imageViews[i].setBackgroundResource(R.mipmap.white);
            } else {
                imageViews[i].setBackgroundResource(R.mipmap.black);
            }
            linearLayout.addView(imageViews[i]);
        }

        viewPager.setAdapter(new MyAdapter());
        viewPager.setOnPageChangeListener(new MyListener());

        //adapter中getCount中返回Integer.MAX_VALUE.大数字实现仿无限循环.
        //先设置到3百万的位置.
        viewPager.setCurrentItem(300 * 10000);

        //（放开代码）实现每隔一定时间界面自动切换的功能.
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

    private final Handler viewHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(msg.what);
            super.handleMessage(msg);
        }

    };

    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }

    //手机返回键对应的功能.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        mHandler.removeCallbacks(mRunnable);
    }

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
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(list.get(arg1 % list.size()));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            try {
                // ((ViewPager) arg0).addView(list.get(arg1),0);
                ((ViewPager) arg0).addView((View) list.get(arg1 % list.size()),
                        0);
            } catch (Exception e) {
            }

            return list.get(arg1 % list.size());
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public void finishUpdate(View arg0) {
        }
    }

    class MyListener implements ViewPager.OnPageChangeListener {
        // 当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int arg0) {
            // arg0=arg0%list.size();
        }

        // 当当前页面被滑动时调用
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        // 当新的页面被选中时调用
        @Override
        public void onPageSelected(int arg0) {
            num = arg0;
            arg0 = arg0 % list.size();
//            imageViews[arg0].setBackgroundColor(Color.alpha(100));
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[arg0].setBackgroundResource(R.mipmap.white);
                if (arg0 != i) {
                    imageViews[i].setBackgroundResource(R.mipmap.black);
                }
            }

        }
    }

}
