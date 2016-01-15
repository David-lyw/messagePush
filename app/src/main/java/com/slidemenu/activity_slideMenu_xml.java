package com.slidemenu;

import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.widget.LinearLayout.LayoutParams;

import java.util.ArrayList;

import engineering.R;


/**
 * Created by sd on 2015/10/17.
 */
public class activity_slideMenu_xml extends Activity implements OnClickListener {

    private ViewPager view_pager;
    private View allprocess, financialreimbursement, informationcenter, officegeneralroutine, propertyroutine;
    private View  propertymanager;
    private View porcess_f,porcess_g,porcess_i,porcess_o,porcess_p;
    private LayoutInflater inflater;
    public ArrayList<View> views;

    //界面切换时,变化的那些点所在的布局。
    private LinearLayout ll_dots;
    private View[] dots;
    private View currentDot;

    private int pageSize;
    private int halfPageSize;

    /**
     * 切换界面
     *
     * @param savedInstanceState
     */
    private static final int SWITHC_INTERFACE = 0;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SWITHC_INTERFACE:
                    // switchImage();
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

        setContentView(R.layout.activity_slidemenu);
        initView();
        initData();
    }

    public void initView() {
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);
        views = new ArrayList<View>();


    }

    @SuppressLint("NewApi")
    public void initData() {
        inflater = LayoutInflater.from(this);
        allprocess = inflater.inflate(R.layout.activity_slidemenu_allprocess, null);
        financialreimbursement = inflater.inflate(R.layout.activity_slidemenu_financialreimbursement, null);
        informationcenter = inflater.inflate(R.layout.activity_slidemenu_informationcenter, null);
        officegeneralroutine = inflater.inflate(R.layout.activity_slidemenu_officegeneralroutine, null);
        propertyroutine = inflater.inflate(R.layout.activity_slidemenu_propertyroutine, null);
        propertymanager=inflater.inflate(R.layout.activity_slidemenu_process_property_manager,null);
        porcess_f=inflater.inflate(R.layout.process_financial_manage,null);
        porcess_g=inflater.inflate(R.layout.process_general_department,null);
        porcess_i=inflater.inflate(R.layout.process_information,null);
        porcess_o=inflater.inflate(R.layout.process_over_all,null);
        porcess_p=inflater.inflate(R.layout.process_property_manager,null);

        views.add(allprocess);
        views.add(financialreimbursement);
        views.add(informationcenter);
        views.add(officegeneralroutine);
        views.add(propertyroutine);
        views.add(propertymanager);
        views.add(porcess_f);
        views.add(porcess_g);
        views.add(porcess_i);
        views.add(porcess_o);
        views.add(porcess_p);

        dots = new View[views.size()];

        //大数字，确保界面实现（仿）无限循环。
        pageSize = views.size() * 10000 * 100;
        //设置当前的显示的界面
        halfPageSize = pageSize / 2;

        for (int i = 0; i < views.size(); i++) {

            dots[i] = new View(this);
            LinearLayout.LayoutParams params = new LayoutParams(5, 5);
            if (i != 0) {
                params.leftMargin = 5;
            }
            dots[i].setLayoutParams(params);
            dots[i].setBackgroundResource(R.drawable.selector_dot);

            ll_dots.addView(dots[i]);
        }
        setDot(0);

        view_pager.setAdapter(new MyAdapter());
        view_pager.setOnPageChangeListener(mOnPageChangeListener);
        view_pager.setCurrentItem(halfPageSize);
    }

    /**
     * 切换界面
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

        //  sendSwitchImageMessage();
    }

    @Override
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
     * 发送切换界面的消息
     */
    private void sendSwitchImageMessage() {
        handler.sendEmptyMessageDelayed(SWITHC_INTERFACE, 3000);
    }


    /**
     * 移除切换图片的消息
     */
    private void removeSwitchImageMessage() {
        handler.removeMessages(SWITHC_INTERFACE);
    }

    /**
     * 设置页面的当前的点
     */
    private void setDot(int position) {
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
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % views.size();
            // 让position在 0 ~ n之间进行循环
            View view = views.get(position);
            container.addView(view);    // 往ViewPager里面添加View
            return view;
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

    OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
        // 当某一页被选择的时候
        @Override
        public void onPageSelected(int position) {
            position = position % views.size();
            setDot(position);
        }

        // 当页面滚动
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        // 当页面的滚动状态发生改变
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                // 如果是空闲状态，应该自动切换图片（放开代码，就实现自动切换）
                // sendSwitchImageMessage();
            } else {
                // 如果不是空闲状态，则不自动切换
                // removeSwitchImageMessage();
            }
        }
    };


    @Override
    public void onClick(View view) {

    }
}
