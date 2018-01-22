package com.hykj.selecttimelib;

import com.hykj.selecttimelib.widget.OnWheelChangedListener;
import com.hykj.selecttimelib.widget.WheelView;
import com.hykj.selecttimelib.widget.adapters.ArrayWheelAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;

/**
 * 事件选择弹窗
 * 
 * @author Administrator
 *
 */
public class SelectWheelPopW {

	Activity activity;
	int type = -1; // 默认不包含小时
	SelectWheelPopWOnClick onclick;
	String[] array = null;
	int index = 0;

	// 日期选择控件
	private PopupWindow popWT;
	private WheelView wheel4;

	public void showPopw(Activity activity, View v, String[] array,
			SelectWheelPopWOnClick onclick) {
		this.activity = activity;
		this.onclick = onclick;
		this.array = array;
		if (popWT == null) {
			initPopW();
		}
		popWT.showAtLocation(v, Gravity.CENTER, 0, 0);
	}

	@SuppressWarnings("deprecation")
	@SuppressLint({ "InflateParams", "InlinedApi" })
	private void initPopW() {

		LayoutInflater lf = LayoutInflater.from(activity);
		View v = lf.inflate(R.layout.popw_select, null);
		popWT = new PopupWindow(v, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		popWT.setFocusable(true);
		popWT.setBackgroundDrawable(new BitmapDrawable());
		popWT.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		wheel4 = (WheelView) v.findViewById(R.id.wheel1);

		// 初始化年份
		wheel4.setCyclic(false);// 可循环滚动
		wheel4.setViewAdapter(new ArrayWheelAdapter<String>(activity, array));
		wheel4.setCurrentItem(0);// 初始化时显示的数据
		wheel4.setVisibleItems(7);

		Button no = (Button) v.findViewById(R.id.no);
		Button yes = (Button) v.findViewById(R.id.yes);
		no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popWT.dismiss();
				onclick.cancelOnClick();
			}
		});
		yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popWT.dismiss();
				onclick.sureOnClick(index, array[index]);
			}
		});

		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				index = newValue;
			}
		};
		wheel4.addChangingListener(wheelListener_year);
	}
}
