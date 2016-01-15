package com.slidemenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class MenuUtil {

	/** 动画开启次数 */
	private static int startCount;

	/**
	 * 隐藏View
	 *
	 * @param view
	 */
	public static void hide(View view) {
		float fromDegrees = 0f; // 旋转的起始角度
		float toDegrees = -180f; // 旋转的终点角度
		rotateView(view, fromDegrees, toDegrees, 0L);
		setClickable(view, false);
	}

	/**
	 * 设置View的Clickabe属性
	 *
	 * @param view
	 * @param clickable
	 */
	private static void setClickable(View view, boolean clickable) {
		view.setClickable(clickable);

		// 遍历所有的子孩子，把clickable设置成false
		if (view instanceof ViewGroup) {
			ViewGroup viewGroup = (ViewGroup) view;
			for (int i = 0; i < viewGroup.getChildCount(); i++) {
				View child = viewGroup.getChildAt(i);
				child.setClickable(clickable); // 设置View是否可以单击
			}
		}
	}

	/**
	 * 隐藏View
	 *
	 * @param view
	 * @param startOffset
	 *            动画延迟启动的时间
	 */
	public static void hide(View view, long startOffset) {
		float fromDegrees = 0f; // 旋转的起始角度
		float toDegrees = -180f; // 旋转的终点角度
		rotateView(view, fromDegrees, toDegrees, startOffset);
		setClickable(view, false);
	}

	/**
	 * 显示View
	 *
	 * @param view
	 */
	public static void show(View view) {
		float fromDegrees = -180f; // 旋转的起始角度
		float toDegrees = 0f; // 旋转的终点角度
		rotateView(view, fromDegrees, toDegrees, 0L);
		setClickable(view, true);
	}

	/**
	 * 旋转View
	 *
	 * @param view
	 * @param fromDegrees
	 *            旋转的起始角度
	 * @param toDegrees
	 *            旋转的终点角度
	 */
	private static void rotateView(View view, float fromDegrees,
								   float toDegrees, long startOffset) {
		int pivotXType = RotateAnimation.RELATIVE_TO_SELF; // 指定旋转点x方向的参照物
		int pivotYType = RotateAnimation.RELATIVE_TO_SELF; // 指定旋转点y方向的参照物
		float pivotXValue = 0.5f; // 指定旋转点x位置
		float pivotYValue = 1.0f; // 指定旋转点y位置
		RotateAnimation ra = new RotateAnimation(fromDegrees, toDegrees,
				pivotXType, pivotXValue, pivotYType, pivotYValue);
		ra.setDuration(500); // 设置动画的执行时间
		ra.setFillAfter(true); // 设置动画保持在结束状态
		ra.setStartOffset(startOffset); // 设置动画的延迟启动时间
		ra.setAnimationListener(listener);

		view.startAnimation(ra); // 开启动画
	}

	private static AnimationListener listener = new AnimationListener() {

		// 动画开始执行
		@Override
		public void onAnimationStart(Animation animation) {
			startCount++;
		}

		// 动画重执行
		@Override
		public void onAnimationRepeat(Animation animation) {

		}

		// 动画执行结束
		@Override
		public void onAnimationEnd(Animation animation) {
			startCount--;
		}
	};

	/***
	 * 是否还有动画在执行
	 *
	 * @return
	 */
	public static boolean hasAnimExcuting() {
		return startCount > 0;
	}

}
