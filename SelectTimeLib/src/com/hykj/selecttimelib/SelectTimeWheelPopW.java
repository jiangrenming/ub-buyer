package com.hykj.selecttimelib;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
public class SelectTimeWheelPopW {
	/**
	 * 无小时分钟
	 */
	public static int NOHOURS = -1;
	/**
	 * 有小时分钟
	 */
	public static int INHOURS = 1;

	Activity activity;
	int type = -1; // 默认不包含小时
	SelectTimeWheelPopWOnClick onclick;

	// 日期选择控件
	private PopupWindow popWT;
	private static int START_YEAR = 1900, END_YEAR = 2100;
	private WheelView wheel4;
	private WheelView wheel5;
	private WheelView wheel6;
	private WheelView wheel7;
	private WheelView wheel8;
	// 添加大小月月份并将其转换为list,方便之后的判断
	String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
	String[] months_little = { "4", "6", "9", "11" };
	final List<String> list_big = Arrays.asList(months_big);
	final List<String> list_little = Arrays.asList(months_little);

	public void showPopw(Activity activity, int type, View v,
			SelectTimeWheelPopWOnClick onclick) {
		this.activity = activity;
		this.onclick = onclick;
		this.type = type;
		if (popWT == null) {
			initPopW();
		}
		popWT.showAtLocation(v, Gravity.CENTER, 0, 0);
	}

	@SuppressWarnings("deprecation")
	@SuppressLint({ "InflateParams", "InlinedApi" })
	private void initPopW() {
		// 初始化年月
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		LayoutInflater lf = LayoutInflater.from(activity);
		View v = lf.inflate(R.layout.popw_select_time, null);
		popWT = new PopupWindow(v, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		popWT.setFocusable(true);
		popWT.setBackgroundDrawable(new BitmapDrawable());
		popWT.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		wheel4 = (WheelView) v.findViewById(R.id.wheel1);
		wheel5 = (WheelView) v.findViewById(R.id.wheel2);
		wheel6 = (WheelView) v.findViewById(R.id.wheel3);
		wheel7 = (WheelView) v.findViewById(R.id.wheel4);
		wheel8 = (WheelView) v.findViewById(R.id.wheel5);
		if (type == -1) {
			wheel7.setVisibility(View.GONE);
			wheel8.setVisibility(View.GONE);
		} else {
			wheel7.setVisibility(View.VISIBLE);
			wheel8.setVisibility(View.VISIBLE);
		}

		// 初始化年份
		wheel4.setCyclic(true);// 可循环滚动
		String[] yearList = getIntList(START_YEAR, END_YEAR, "年");
		wheel4.setViewAdapter(new ArrayWheelAdapter<String>(activity, yearList));
		wheel4.setCurrentItem(year - START_YEAR);// 初始化时显示的数据
		wheel4.setVisibleItems(7);

		// 初始化月份
		wheel5.setCyclic(true);
		String[] monthList = getIntList(1, 12, "月");
		wheel5.setViewAdapter(new ArrayWheelAdapter<String>(activity, monthList));
		wheel5.setCurrentItem(month);
		wheel5.setVisibleItems(7);

		// 初始化日
		wheel6.setCyclic(true);
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(month + 1))) {
			wheel6.setViewAdapter(new ArrayWheelAdapter<String>(activity,
					getIntList(1, 31, "日")));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wheel6.setViewAdapter(new ArrayWheelAdapter<String>(activity,
					getIntList(1, 30, "日")));
		} else {
			// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wheel6.setViewAdapter(new ArrayWheelAdapter<String>(activity,
						getIntList(1, 29, "日")));
			else
				wheel6.setViewAdapter(new ArrayWheelAdapter<String>(activity,
						getIntList(1, 28, "日")));
		}
		wheel6.setCurrentItem(day - 1);
		wheel6.setVisibleItems(7);

		// 初始化小时
		wheel7.setCyclic(true);
		wheel7.setViewAdapter(new ArrayWheelAdapter<String>(activity,
				getIntList(0, 23, "时")));
		wheel7.setCurrentItem(0);
		wheel7.setVisibleItems(7);

		// 初始化分钟
		wheel8.setCyclic(true);
		wheel8.setViewAdapter(new ArrayWheelAdapter<String>(activity,
				getIntList(0, 59, "分")));
		wheel8.setCurrentItem(0);
		wheel8.setVisibleItems(7);

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
				onclick.sureOnClick(wheel4.getCurrentItem() + START_YEAR,
						wheel5.getCurrentItem() + 1,
						wheel6.getCurrentItem() + 1, wheel7.getCurrentItem(),
						wheel8.getCurrentItem());
			}
		});

		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big
						.contains(String.valueOf(wheel5.getCurrentItem() + 1))) {
					wheel6.setViewAdapter(new ArrayWheelAdapter<String>(
							activity, getIntList(1, 31, "日")));
				} else if (list_little.contains(String.valueOf(wheel5
						.getCurrentItem() + 1))) {
					wheel6.setViewAdapter(new ArrayWheelAdapter<String>(
							activity, getIntList(1, 30, "日")));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0)
						wheel6.setViewAdapter(new ArrayWheelAdapter<String>(
								activity, getIntList(1, 29, "日")));
					else
						wheel6.setViewAdapter(new ArrayWheelAdapter<String>(
								activity, getIntList(1, 28, "日")));
				}
			}
		};
		// 添加"月"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					wheel6.setViewAdapter(new ArrayWheelAdapter<String>(
							activity, getIntList(1, 31, "日")));
				} else if (list_little.contains(String.valueOf(month_num))) {
					wheel6.setViewAdapter(new ArrayWheelAdapter<String>(
							activity, getIntList(1, 30, "日")));
				} else {
					if (((wheel4.getCurrentItem() + START_YEAR) % 4 == 0 && (wheel4
							.getCurrentItem() + START_YEAR) % 100 != 0)
							|| (wheel4.getCurrentItem() + START_YEAR) % 400 == 0)
						wheel6.setViewAdapter(new ArrayWheelAdapter<String>(
								activity, getIntList(1, 29, "日")));
					else
						wheel6.setViewAdapter(new ArrayWheelAdapter<String>(
								activity, getIntList(1, 28, "日")));
				}
			}
		};
		wheel4.addChangingListener(wheelListener_year);
		wheel5.addChangingListener(wheelListener_month);
	}

	private String[] getIntList(int start, int end, String lable) {
		String[] dataList = new String[end - start + 1];
		int j = 0;
		for (int i = start; i <= end; i++) {
			dataList[j] = i + lable;
			j++;
		}
		return dataList;
	}
}
