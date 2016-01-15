package com.listview;

import com.listview.PullToRefreshListView.OnRefreshingListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import engineering.R;

public class PullToRefreshListView extends ListView {

    private int headerViewHeight;
    private View headerView;
    private float downY;
    /**
     * 下拉刷新状态
     */
    private static final int STATE_PULL_TO_REFRESH = 0;
    /**
     * 松开刷新状态
     */
    private static final int STATE_RELEASE_TO_REFRESH = 1;
    /**
     * 正在刷新状态
     */
    private static final int STATE_REFRESHING = 2;
    /**
     * 当前的状态，默认是下拉刷新状态
     */
    private int currentState = STATE_PULL_TO_REFRESH;
    private TextView tv_state;
    private ImageView iv_arrow;
    /**
     * 往上旋转的动画
     */
    private RotateAnimation upAnim;
    /**
     * 往下旋转的动画
     */
    private RotateAnimation downAnim;
    private ProgressBar progressBar;
    private OnRefreshingListener onRefreshingListener;
    private View footerView;
    private int footerViewHeight;
    /**
     * 正在加载更多
     */
    private boolean loadMoreing;


    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToRefreshListView(Context context) {
        super(context);
        init();
    }

    private void init() {
        initHeaderView();
        initFooterView();
    }

    private void initFooterView() {
        footerView = View.inflate(getContext(), R.layout.footerview, null);
        footerView.measure(0, 0);    // 让系统主动去测量view的宽和高
        footerViewHeight = footerView.getMeasuredHeight();
        hideFooterView();
        addFooterView(footerView);

        setOnScrollListener(new OnScrollListener() {

            // 滚动状态发生改变了会执行这个方法
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (loadMoreing) {
                    return;    // 如果已经在加载了，则不再加载
                }

                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && getLastVisiblePosition() == getCount() - 1) {
                    loadMoreing = true;
                    // 如果是空闲状态，并且最后一条可见的Item是最后一条数据，则把FooterView显示出来
                    showFooterView();
                    setSelection(getCount() - 1);    // 选择到最后一条数据

                    if (onRefreshingListener != null) {
                        onRefreshingListener.onLoadMore();
                    }
                }
            }

            // 正在滚动的时候会执行这个方法
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    /**
     * 隐藏FooterView
     *
     * @param
     */
    private void hideFooterView() {
        footerView.setPadding(0, -footerViewHeight, 0, 0);
    }

    /**
     * 显示FooterView
     *
     * @param
     */
    private void showFooterView() {
        footerView.setPadding(0, 0, 0, 0);
    }

    private void initHeaderView() {
        headerView = View.inflate(getContext(), R.layout.headerview, null);
        tv_state = (TextView) headerView.findViewById(R.id.tv_state);
        iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
        progressBar = (ProgressBar) headerView.findViewById(R.id.progressBar);
        headerView.measure(0, 0);    // 让系统主动去测量view的宽和高
        headerViewHeight = headerView.getMeasuredHeight();
        hideHeaderView();
        addHeaderView(headerView);
        initAnim();
    }

    /**
     * 隐藏HeaderView
     */
    private void hideHeaderView() {
        int paddingTop = -headerViewHeight;
        setHeaderViewPaddingTop(paddingTop);
    }

    private void showHeaderView() {
        int paddingTop = 0;
        setHeaderViewPaddingTop(paddingTop);
    }

    /**
     * 初始化动画类
     */
    private void initAnim() {
        upAnim = createRotateAnim(0f, -180f);
        downAnim = createRotateAnim(-180f, -360f);
    }

    /**
     * 显示箭头
     *
     * @param isShowArrow 是否显示箭头
     */
    public void showArrow(boolean isShowArrow) {
        iv_arrow.setVisibility(isShowArrow ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(isShowArrow ? View.GONE : View.VISIBLE);
    }

    /**
     * @param fromDegrees 旋转的起始角度
     * @param toDegrees   旋转的结束角度
     */
    private RotateAnimation createRotateAnim(float fromDegrees, float toDegrees) {
        int pivotXType = RotateAnimation.RELATIVE_TO_SELF;        // 旋转点的参照物
        int pivotYType = RotateAnimation.RELATIVE_TO_SELF;        // 旋转点的参照物
        float pivotXValue = 0.5f;    // 旋转点的x轴的位置
        float pivotYValue = 0.5f;    // 旋转点的y轴的位置
        RotateAnimation ra = new RotateAnimation(fromDegrees, toDegrees,
                pivotXType, pivotXValue, pivotYType, pivotYValue);
        ra.setDuration(300);    // 动画执行时间
        ra.setFillAfter(true);    // 保持动画的结束状态
        return ra;
    }

    /**
     * @param paddingTop
     */
    private void setHeaderViewPaddingTop(int paddingTop) {
        headerView.setPadding(0, paddingTop, 0, 0);
    }

    // 如果往上滑，不需要显示HeaderView
    // 往下滑的时候，如果第 一条可见的item不是索引为0的数据，也不需要显示HeaderView

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentState == STATE_REFRESHING) {
                    return super.onTouchEvent(ev);    // 如果已经是正在刷新状态了，则不让其下拉操作了
                }

                float distatnceY = ev.getY() - downY;
                if (distatnceY > 0 && getFirstVisiblePosition() == 0) {    // 如果大于0则是往下滑
                    // 往下滑的时候，并且第一条可以的Item是索引为0的数据的时候
                    int paddingTop = (int) (distatnceY - headerViewHeight);
                    setHeaderViewPaddingTop(paddingTop);

                    // 如果paddingTop < 0，则HeaderView没有完全显示出来，这是下拉刷新状态
                    if (paddingTop < 0 && currentState != STATE_PULL_TO_REFRESH) {
                        // 下拉刷新
                        currentState = STATE_PULL_TO_REFRESH;
                        tv_state.setText("下拉刷新");
                        iv_arrow.startAnimation(downAnim);
                        showArrow(true);
                    } else if (paddingTop >= 0 && currentState != STATE_RELEASE_TO_REFRESH) {
                        // 松开刷新
                        currentState = STATE_RELEASE_TO_REFRESH;
                        tv_state.setText("松开刷新");
                        iv_arrow.startAnimation(upAnim);
                        showArrow(true);
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                // 手指抬起的时候，并且当前状态是松开刷新状态的时候
                if (currentState == STATE_RELEASE_TO_REFRESH) {
                    // 正在刷新
                    currentState = STATE_REFRESHING;
                    tv_state.setText("正在刷新");
                    iv_arrow.clearAnimation();    // 清除动画效果
                    showArrow(false);

                    showHeaderView();

                    if (onRefreshingListener != null) {
                        onRefreshingListener.onRefreshing();
                    }
                } else if (currentState == STATE_PULL_TO_REFRESH) {
                    hideHeaderView();
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 正在刷新接口
     *
     * @author Administrator
     */
    public interface OnRefreshingListener {
        /**
         * 正在刷新的时候会回调这个方法
         */
        void onRefreshing();

        /**
         * 正在加载更多 的时候会回调这个方法
         */
        void onLoadMore();
    }

    public void setOnRefreshingListener(OnRefreshingListener onRefreshing) {
        this.onRefreshingListener = onRefreshing;
    }

    /**
     * 刷新完成了
     */
    public void onRefreshComplete() {
        // 隐藏HeaderView
        hideHeaderView();

        // 恢复默认状态（下拉刷新）
        currentState = STATE_PULL_TO_REFRESH;
        showArrow(true);
    }

    /**
     * 加载完成
     */
    public void onLoadMoreComplete() {
        hideFooterView();
        loadMoreing = false;
    }

}
