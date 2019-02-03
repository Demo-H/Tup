package com.tupperware.biz.utils;

import android.widget.Toast;

import com.tupperware.biz.base.BaseApplication;


/**
 * 
 * @author dhunter
 * 提供系统自带的toast和自定义的toast
 * 自定义toast是圆弧形状，红色背景，白色字体，在屏幕上方弹出
 *
 * 一般情况下，当前页面消失还想继续弹toast可以使用该toast工具，
 * 如果当前页面消失，不想继续弹任何该页面的toast，就使用BaseActivity和BaseFragment里的toast()方法即可
 * 没有具体要求可以混用
 */

public class ToastUtil {

	private static Toast toast;

	/**
	 * 初始化Toast(消息，时间)
	 */
	private static Toast initToast(CharSequence message, int duration) {
		if (toast == null) {
			toast = Toast.makeText(BaseApplication.getInstance(), message, duration);
		} else {
			//设置文字
			toast.setText(message);
			//设置存续期间
			toast.setDuration(duration);
		}
		return toast;
	}

	public static void showL(String msg) {
		if(msg == null || msg.isEmpty()) {
			return;
		}
		initToast(msg, Toast.LENGTH_LONG).show();
	}

	public static void showS(String msg) {
		if(msg == null || msg.isEmpty()) {
			return;
		}
		initToast(msg, Toast.LENGTH_SHORT).show();
	}
}
