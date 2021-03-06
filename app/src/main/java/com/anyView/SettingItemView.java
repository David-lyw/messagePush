package com.anyView;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import engineering.R;

public class SettingItemView extends RelativeLayout {
    private final static int BKG_FIRST = 0;
    private final static int BKG_MIDDLE = 1;
    private final static int BKG_LAST = 2;

    private TextView mTvTitle;
    private ImageView mIvToggle;

    private boolean isToggleOn;
    private boolean isToggleEnable = true;

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet set) {
        super(context, set);

        // 将布局和view绑定
        View view = View.inflate(context, R.layout.view_setting_item, this);

        mTvTitle = (TextView) findViewById(R.id.view_tv_title);
        mIvToggle = (ImageView) findViewById(R.id.view_iv_toggle);

        // 读取自定的属性
        TypedArray ta = context.obtainStyledAttributes(set,
                R.styleable.SettingItemView);

        String title = ta.getString(R.styleable.SettingItemView_mtitle);
        int bkg = ta.getInt(R.styleable.SettingItemView_itbackground, 0);
        isToggleEnable = ta.getBoolean(
                R.styleable.SettingItemView_toggleEnable, isToggleEnable);
        // 回收
        ta.recycle();

        mTvTitle.setText(title);

        // 设置背景
        switch (bkg) {
            case BKG_FIRST:
                view.setBackgroundResource(R.drawable.seting_first_selector);
                break;
            case BKG_MIDDLE:
                view.setBackgroundResource(R.drawable.seting_middle_selector);
                break;
            case BKG_LAST:
                view.setBackgroundResource(R.drawable.seting_last_selector);
                break;
            default:
                view.setBackgroundResource(R.drawable.seting_first_selector);
                break;
        }

        // 设置默认值
        setToggleOn(isToggleOn);

        //
        mIvToggle.setVisibility(isToggleEnable ? View.VISIBLE : View.GONE);
    }

    // 设置开关打开还是关闭
    public void setToggleOn(boolean on) {
        this.isToggleOn = on;
        if (on) {
            mIvToggle.setImageResource(R.mipmap.on);
        } else {
            mIvToggle.setImageResource(R.mipmap.off);
        }
    }

    // 如果打开就关闭，如果关闭就打开
    public void toggle() {
        setToggleOn(!isToggleOn);
    }

    public boolean isToggleOn() {
        return isToggleOn;
    }
}
