package engineering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import com.anyView.GifView;
import com.common.ScreenManager;
import com.utils.L;
import com.utils.T;

/**
 * Created by sd on 2015/11/18.
 */
public class activity_welcom_gif extends Activity implements OnClickListener {
    ScreenManager screenManager;
    private ViewPager view_pager;

    private int[] gifResIds = {
            R.raw.bird,
            R.raw.bird,
            R.raw.bird,
            R.raw.bird,
            R.raw.someb
    };

    GifView[] gifViews=new GifView[gifResIds.length];

    private LinearLayout ll_dots;//
    private View[] dots = new View[gifResIds.length];//
    private View currentDot;//
    private int pageSize = gifResIds.length;

    /**
     * 切换图片
     */
    private static final int SWITCH_IMAGE = 0;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SWITCH_IMAGE:
                    //switchImage();
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
        setContentView(R.layout.activity_welcom_gif);
        initView();
        initData();
        initListener();

    }

    public void initView() {
        view_pager = (ViewPager) findViewById(R.id.view_pager_gif);
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots_img);
        screenManager = ScreenManager.getScreenManager();
    }

    public void initData() {
        for (int i = 0; i < gifViews.length; i++) {
            // 初始化ImageView
            gifViews[i] = new GifView(this);

            gifViews[i].setMovieResource(gifResIds[i]);


            if (i == 4) {//为最后一张添加touch监听，并实现响应事件。
                gifViews[i].setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        screenManager.pushActivity(activity_welcom_gif.this);
                        Intent intent = new Intent(activity_welcom_gif.this, activity_index.class);
                        startActivity(intent);
                        return false;
                    }
                });
            }
            // 初始化点
            dots[i] = new View(this);
            // dotView的窗器是谁，LayoutParams就用谁的内部类
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(5, 5);
            if (i != 0) {
                // 如果不是第一个点，则需要设置leftMargin
                params.leftMargin = 10;
            }
            dots[i].setLayoutParams(params);
            dots[i].setBackgroundResource(R.drawable.selector_dot);
            ll_dots.addView(dots[i]);
        }

        setDot(0);

        view_pager.setAdapter(new MyAdapter());
        view_pager.setOnPageChangeListener(mOnPageChangeListener);    // 设置 页面改变 监听器
        view_pager.setCurrentItem(0);    // 设置ViewPager显示第一张。
        view_pager.setOnClickListener(this);
    }

    public void initListener() {

    }

    /**
     * 切换图片
     */
    protected void switchImage() {
        int currentItem = view_pager.getCurrentItem();    // 取出ViewPager当前显示的位置


//        if (currentItem == view_pager.getAdapter().getCount() - 1) {
//            // 如果是最后一个位置了，则让它变成第一个
//            currentItem = 0;
//        } else {
//            currentItem++;
//        }


        if (currentItem == view_pager.getAdapter().getCount() - 1) {

            view_pager.setCurrentItem(currentItem);
            removeSwitchImageMessage();

        } else {
            currentItem++;
            view_pager.setCurrentItem(currentItem);
            sendSwitchImageMessage();
            L.i(currentItem + "");
        }


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
     * 发送切换图片的消息
     */
    private void sendSwitchImageMessage() {
        handler.sendEmptyMessageDelayed(SWITCH_IMAGE, 3000);
    }


    /**
     * 移除切换图片的消息
     */
    private void removeSwitchImageMessage() {
        handler.removeMessages(SWITCH_IMAGE);
    }


    ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        // 当某一页被选择的时候
        @Override
        public void onPageSelected(int position) {
            position = position % gifResIds.length;
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
                // 如果是空闲状态，应该自动切换图片
                sendSwitchImageMessage();
            } else {
                // 如果不是空闲状态，则不自动切换
                removeSwitchImageMessage();
            }
        }
    };

    /**
     * 设置当前图片的当前的点
     *
     * @param position
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
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            switch (position) {
                case 4:
                    L.i("instantiateItem");
                    break;
            }
            position = position % gifViews.length;
            // 让position在 0 ~ 4之间进行循环
            GifView gifView = gifViews[position];
            container.addView(gifView);    // 往ViewPager里面添加View
            return gifView;
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

        switch (view.getId()) {
            case R.id.view_pager_gif:
                L.i("点击的view_pager");
                break;


            default:

                break;

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        screenManager.popAllActivityExceptOne(activity_welcom_gif.class);
    }
}
