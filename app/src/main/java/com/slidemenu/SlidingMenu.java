package com.slidemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class SlidingMenu extends ViewGroup {

    private View menuView;
    private View mainView;
    private int menuViewWidth;
    private float downX;
    private float distanceX;
    private float destX;
    /**
     * 模拟滑动的值
     */
    private Scroller scroller;

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlidingMenu(Context context) {
        super(context);
        init();
    }

    private void init() {
        scroller = new Scroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取菜单
        menuView = getChildAt(0);
        // 主界面
        mainView = getChildAt(1);

        // 获取菜单的宽
        menuViewWidth = menuView.getLayoutParams().width;

        // 测量菜单的宽和高
        menuView.measure(menuViewWidth, heightMeasureSpec);

        // 测量主界面的宽和高
        mainView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        menuView.layout(l - menuViewWidth, t, l, b);
        mainView.layout(l, t, r, b);
    }

    private boolean isMove;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                distanceX = event.getX() - downX + destX;

                // 预防滑动时超出边界
                if (distanceX > menuViewWidth) {
                    distanceX = menuViewWidth;
                } else if (distanceX < 0) {
                    distanceX = 0;
                }
                scrollTo(distanceX);
                isMove = true;
                break;
            case MotionEvent.ACTION_UP:
                if (!isMove) {
                    // 如果没有移动过，则不走下面的代码
                    isMove = false;
                    return true;
                }

                if (getMyScrollX() > menuViewWidth / 40) {
                    // 如果菜单滑动的距离 > 菜单宽 / 4，则把菜单完全显示出来
                    destX = menuViewWidth;
                } else {
                    // 把菜单完全隐藏
                    destX = 0;
                }

                // scrollTo(oldDistanceX);

                startScroll();
                break;

            default:
                break;
        }
        return true; // 消费touch事件
    }

    /**
     * 开始滚动
     */
    private void startScroll() {
        int startX = (int) distanceX; // 从哪开始滑动
        int dx = (int) (destX - distanceX); // 滑动距离
        // 算比例的时候，最终要求什么值，则这个单位的值做为被除数
        float scale = 1000f / menuViewWidth;
        int duration = (int) (Math.abs(dx) * scale); // 移动的执行时间
        System.out.println("duration = " + duration);
        scroller.startScroll(startX, 0, dx, 0, duration);

        invalidate();
        // 调用invalidate方法系统会重新调用drawChild方法，这个方法会调用：draw(Canvas, ViewGroup,
        // long)
        // draw方法内会调computeScroll
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            int currX = scroller.getCurrX();
            scrollTo(currX);

            invalidate();
            // 调用invalidate方法系统会重新调用drawChild方法，这个方法会调用：draw(Canvas, ViewGroup,
            // long)
            // draw方法内会调computeScroll
        }
    }

    /**
     * 对原来的方法进行修改，是为了传正数的时候界面往右边滑，传负数的时候界面往左边滑
     *
     * @param x
     */
    public void scrollTo(float x) {
        super.scrollTo((int) -x, 0);
    }

    /**
     * 获取滑动的位置
     *
     * @return
     */
    public int getMyScrollX() {
        return -super.getScrollX();
    }

    /**
     * 打开或者关闭菜单
     */
    public void openMenu() {
        if (getMyScrollX() == 0) {
            // 原来是隐藏的，需要显示出来
            distanceX = 0;
            destX = menuViewWidth;
        } else {
            // 原来是显示的，需要隐藏
            distanceX = menuViewWidth;
            destX = 0;
        }

        startScroll();
    }

}
