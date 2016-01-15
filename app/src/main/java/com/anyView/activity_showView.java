package com.anyView;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import engineering.R;

/**
 * Created by sd on 2015/11/5.
 */
@SuppressLint("NewApi")
public class activity_showView extends Activity {
    ImageView iv_timo;
    ImageView iv_timo1;
    ImageView iv_timo2, iv_timo3;
    ImageView iv_wenya4, iv_wenya5;
    ImageView iv_wangdigua6, iv_img7;
    ImageView iv_qq8, iv_qq9;
    SettingItemView togglebtn_1, togglebtn_2;

    MyToggleButton myToggleButton;

    CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_showview);
        loadAnimation();
        initView();
        initData();
        initListener();

    }


    //加载动画的显示与消失
    public void loadAnimation() {
        TimerTask task = new TimerTask() {
            public void run() {
                customProgressDialog.dismiss();
            }
        };
        customProgressDialog = new CustomProgressDialog(this, "正在加载中", R.anim.frame, 0);
        customProgressDialog.show();
        Timer timer = new Timer(true);
        timer.schedule(task, 1000 * 2, 1000);
        timer.cancel();
    }

    public void initView() {
        iv_timo = (ImageView) findViewById(R.id.iv_timo);
        doAnimationY(iv_timo);

        iv_timo1 = (ImageView) findViewById(R.id.iv_timo1);
        doAnimationX(iv_timo1);

        togglebtn_1 = (SettingItemView) findViewById(R.id.togglebtn_1);
        togglebtn_2 = (SettingItemView) findViewById(R.id.togglebtn_2);

        //淡入淡出----代码实现
        iv_timo2 = (ImageView) findViewById(R.id.iv_timo2);
        doAlphaAnimation(iv_timo2);
        //淡入淡出----xml实现
        iv_timo3 = (ImageView) findViewById(R.id.iv_timo3);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        iv_timo3.startAnimation(animation);

        //缩放----代码实现
        iv_wenya4 = (ImageView) findViewById(R.id.iv_wenya4);
        doScaleAnimation(iv_wenya4);
        //缩放----xml实现
        iv_wenya5 = (ImageView) findViewById(R.id.iv_wenya5);
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.scale);
        iv_wenya5.startAnimation(animation1);
        //旋转----代码实现
        iv_wangdigua6 = (ImageView) findViewById(R.id.iv_wangdigua6);
        doRotateAnimation(iv_wangdigua6);
        //旋转----xml实现
        iv_img7 = (ImageView) findViewById(R.id.iv_img7);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.rotate);
        iv_img7.startAnimation(animation2);
        //移动效果----代码实现
        iv_qq8 = (ImageView) findViewById(R.id.iv_qq8);
        doTranslateAnimation(iv_qq8);
        //移动效果----xml实现
        iv_qq9 = (ImageView) findViewById(R.id.iv_qq9);
        Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.translate);
        iv_qq9.startAnimation(animation3);


        myToggleButton = (MyToggleButton) findViewById(R.id.myToggleButton);


    }

    public void initData() {
        // 设置当前更新的状态（从sp中取出存储的上次选择的状态，无值默认为true）
        boolean update = PreferenceUtils.getBoolean(this,
                Constants.AUTO_UPDATE, true);
        togglebtn_1.setToggleOn(update);

    }

    public void initListener() {
        togglebtn_1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                togglebtn_1.toggle();

                boolean toggleOn = togglebtn_1.isToggleOn();

                PreferenceUtils.putBoolean(activity_showView.this,
                        Constants.AUTO_UPDATE, toggleOn);
            }
        });


        togglebtn_2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // 弹出Dialog
                final AddressDialog dialog = new AddressDialog(
                        activity_showView.this);
                dialog.setAdapter(new AddressAdapter());// -->list
                dialog.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        dialog.dismiss();

                        PreferenceUtils.putInt(getApplicationContext(),
                                Constants.ADDRESS_STYLE, icons[position]);
                    }
                });
                dialog.show();
            }
        });

        myToggleButton.setOnStateChangedListener(new MyToggleButton.OnStateChangedListener() {
            @Override
            public void onStateChanged(boolean isOpen) {
                Toast.makeText(getApplicationContext(), isOpen ? "打开" : "关闭", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 让imageView做动画----y轴
     */
    public void doAnimationY(View imageView) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotationY",
                0, 90, 270, 360);
        animator.setDuration(2000);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.REVERSE);
        animator.start();

    }

    /**
     * 让imageView做动画----x轴
     */
    public void doAnimationX(View imageView) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotationX",
                0, 90, 270, 360);
        animator.setDuration(2000);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.REVERSE);
        animator.start();

    }


    private String[] titles = new String[]{"半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿"};
    private int[] icons = new int[]{R.drawable.toast_address_normal,
            R.drawable.toast_address_orange, R.drawable.toast_address_blue,
            R.drawable.toast_address_gray, R.drawable.toast_address_green};

    private class AddressAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                // 没有复用
                // 初始化converView
                convertView = View.inflate(getApplicationContext(),
                        R.layout.item_address, null);
                // 初始化holder
                holder = new ViewHolder();
                // 设置标记
                convertView.setTag(holder);
                // 初始化holder的view
                holder.ivIcon = (ImageView) convertView
                        .findViewById(R.id.item_addres_iv_icon);
                holder.ivSelected = (ImageView) convertView
                        .findViewById(R.id.item_addres_iv_selected);
                holder.tvTitle = (TextView) convertView
                        .findViewById(R.id.item_addres_tv_title);
            } else {
                // 有复用
                holder = (ViewHolder) convertView.getTag();
            }


            int style = PreferenceUtils.getInt(getApplicationContext(),
                    Constants.ADDRESS_STYLE, R.drawable.toast_address_normal);

            // 给view设置数据
            holder.ivIcon.setImageResource(icons[position]);
            if (style == icons[position]) {
                holder.ivSelected.setVisibility(View.VISIBLE);
            } else {
                holder.ivSelected.setVisibility(View.GONE);
            }

            holder.tvTitle.setText(titles[position]);

            return convertView;
        }
    }

    class ViewHolder {
        ImageView ivIcon;
        ImageView ivSelected;
        TextView tvTitle;

    }

    //淡入淡出效果
    public void doAlphaAnimation(View view) {
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setStartOffset(1000);//10秒后开始执行
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setFillBefore(false);
        animationSet.setFillAfter(true);
        //animationSet.setRepeatMode(Animation.RESTART);
        view.startAnimation(alphaAnimation);
    }

    //缩放效果
    public void doScaleAnimation(View view) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.5f, 1, 0.5f, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setDuration(3000);
        view.startAnimation(animationSet);
    }

    //旋转效果
    public void doRotateAnimation(View view) {
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        rotateAnimation.setDuration(3000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        animationSet.addAnimation(rotateAnimation);
        view.startAnimation(animationSet);
    }

    //移动效果
    public void doTranslateAnimation(View view) {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 6.0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        translateAnimation.setDuration(3000);
        animationSet.addAnimation(translateAnimation);
        view.startAnimation(animationSet);
    }


}
