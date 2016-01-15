package com.slidemenu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

import engineering.R;

/**
 * Created by sd on 2015/10/19.
 */
public class activity_slideMenu_img extends Activity implements OnClickListener {
    private ViewPager view_pager;

    private int[] imageResIds = {
            R.mipmap.a,
            R.mipmap.b,
            R.mipmap.c,
            R.mipmap.d,
            R.mipmap.e,
    };

    private String[] descs = {
            "巩俐不低俗，我就不能低俗",
            "扑树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送",
            "热血屌丝的反杀",
    };


    private ImageView[] imageViews = new ImageView[imageResIds.length];
    private TextView tv_desc;
    private LinearLayout ll_dots;//
    private View[] dots = new View[imageResIds.length];//
    private View currentDot;//
    private int pageSize = imageResIds.length * 10000 * 100;//
    private int halfPageSize = pageSize / 2;
    /**
     * 切换图片
     */
    private static final int SWITCH_IMAGE = 0;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SWITCH_IMAGE:
                    switchImage();
                    break;

                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidemenu_img);
        initView();
        initData();
        initListener();
    }

    public void initView() {
        view_pager = (ViewPager) findViewById(R.id.view_pager_img);
        tv_desc = (TextView) findViewById(R.id.tv_desc_img);
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots_img);
    }

    public void initData() {
        for (int i = 0; i < imageViews.length; i++) {
            // 初始化ImageView
            imageViews[i] = new ImageView(this);
            imageViews[i].setBackgroundResource(imageResIds[i]);

            // 初始化点
            dots[i] = new View(this);
            // dotView的窗器是谁，LayoutParams就用谁的内部类
            LinearLayout.LayoutParams params = new LayoutParams(5, 5);
            if (i != 0) {
                // 如果不是第一个点，则需要设置leftMargin
                params.leftMargin = 5;
            }
            dots[i].setLayoutParams(params);
            dots[i].setBackgroundResource(R.drawable.selector_dot);
            ll_dots.addView(dots[i]);
        }

        setDescAndDot(0);

        view_pager.setAdapter(new MyAdapter());
        view_pager.setOnPageChangeListener(mOnPageChangeListener);    // 设置页面改变监听器
        view_pager.setCurrentItem(halfPageSize);    // 设置ViewPager显示到一半的地方
    }

    public void initListener() {
    }

    /**
     * 切换图片
     */
    protected void switchImage() {
        int currentItem = view_pager.getCurrentItem();    // 取出ViewPager当前显示的位置
        if (currentItem == view_pager.getAdapter().getCount() - 1) {
            // 如果是最后一个位置了，则让它变成第一个
            currentItem = 0;
        } else {
            currentItem++;
        }
        view_pager.setCurrentItem(currentItem);
        sendSwitchImageMessage();
    }

    protected void onStart() {
        super.onStart();
        sendSwitchImageMessage();
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeSwitchImageMessage();
    }

    /**
     * 发送切换图片的消息
     */
    private void sendSwitchImageMessage() {
        handler.sendEmptyMessageDelayed(SWITCH_IMAGE, 3000);
    }

    ;

    /**
     * 移除切换图片的消息
     */
    private void removeSwitchImageMessage() {
        handler.removeMessages(SWITCH_IMAGE);
    }

    ;

    OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {

        // 当某一页被选择的时候
        @Override
        public void onPageSelected(int position) {
            position = position % imageResIds.length;
            setDescAndDot(position);
        }

        // 当页面滚动
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        // 当页面的滚动状态发生改变
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                // 如果是空闲状态，应该自动切换图片
                sendSwitchImageMessage();
            } else {
                // 如果不是空闲状态，则不自动切换
                removeSwitchImageMessage();
            }
        }
    };

    /**
     * 设置当前图片的描述文字和当前的点
     *
     * @param position
     */
    private void setDescAndDot(int position) {
        tv_desc.setText(descs[position]);

        // 设置点
        // 把原来的点变成默认状态
        if (currentDot != null) {
            currentDot.setSelected(false);
        }

        // 把当前的点变成选择状态
        currentDot = dots[position];
        currentDot.setSelected(true);
    }

    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageSize;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 生成一个界面
         * 相当于ListView中的Adapter中的getView
         *
         * @param container ViewPager对象
         * @param position  当前显示的界面的位置
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % imageViews.length;
            // 让position在 0 ~ 4之间进行循环
            ImageView imageView = imageViews[position];
            container.addView(imageView);    // 往ViewPager里面添加View
            return imageView;
        }

        /**
         * 销毁一个界面
         *
         * @param container ViewPager对象
         * @param position  要销毁的界面的位置
         * @param object    instantiateItem方法的返回值
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }

    @Override
    public void onClick(View view) {

    }
}
